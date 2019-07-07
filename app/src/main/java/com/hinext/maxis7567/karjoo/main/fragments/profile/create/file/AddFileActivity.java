package com.hinext.maxis7567.karjoo.main.fragments.profile.create.file;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.login.RegisterActivity;
import com.hinext.maxis7567.karjoo.services.DataBaseTokenID;
import com.maxis7567.msdialog.MSdialog;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddFileActivity extends AppCompatActivity {
    private View DIALOG  ;
    private ImageView saveBTN,chooseFile;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<MyFile> list=new ArrayList<>();
    private int i=0;
    private ProgressDialog pd;
    private ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
        viewGroup=findViewById(R.id.AddFileView);
        saveBTN=findViewById(R.id.AddfileBTN);
        recyclerView=findViewById(R.id.AddFileRex);
        chooseFile=findViewById(R.id.AddFileAttache);
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(AddFileActivity.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 9);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
            }
        });
        adapter=new Adapter(this,list,viewGroup);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size()==0){
                    finish();
                }else {

                    uploadFile();
                }
            }
        });
        pd = new ProgressDialog(this);
        pd.setMax(100);
    }

    private void uploadFile() {
        if (i!=adapter.list.size()) {
            pd.setTitle(String.valueOf(i+1)+"/"+String.valueOf(adapter.list.size()));

            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            AndroidNetworking.upload("http://192.168.1.104:8080/v1/upload")
                    .addHeaders("token", DataBaseTokenID.GetTokenID(this))
                    .addHeaders("id",getIntent().getStringExtra("id"))
                    .addHeaders("desc",adapter.list.get(i).getDesc())
                    .addMultipartFile("file", new File(adapter.list.get(i).getPath()))
                    .setTag("upload")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            if (bytesUploaded != 0) {
                                pd.setTitle("بارگزاری فایل ...");
                                pd.setCancelable(false);
                                pd.setProgress((int) (((int) bytesUploaded * 95) / totalBytes));

                            }
                            pd.show();

                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            i++;
                            uploadFile();
                        }

                        @Override
                        public void onError(ANError error) {
                            pd.cancel();
                            viewGroup.removeView(DIALOG);
                            DIALOG = new MSdialog(AddFileActivity.this, viewGroup).DefaultDialog(getWindow().getDecorView(), "خطا", "مشکل در ارتبات با سرور", "تلاش دوباره",
                                    new MSdialog.MSdialogInterfaceDefault() {
                                        @Override
                                        public void OnConfirmed() {
                                            viewGroup.removeView(DIALOG);
                                            uploadFile();
                                        }

                                        @Override
                                        public void OnCancel() {
                                            viewGroup.removeView(DIALOG);
                                        }
                                    });
                            viewGroup.addView(DIALOG);
                        }
                    });
        }else finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            List<MyFile> myFileList=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                MyFile myFile=new MyFile();
                myFile.setMimeType( list.get(i).getMimeType());
                myFile.setPath(list.get(i).getPath());
                myFile.setName(list.get(i).getName());
                myFileList.add(myFile);
            }
            adapter.addFile(myFileList);

        }
    }
}
