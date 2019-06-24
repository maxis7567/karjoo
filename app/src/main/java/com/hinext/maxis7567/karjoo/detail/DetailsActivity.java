package com.hinext.maxis7567.karjoo.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.Detail;
import com.hinext.maxis7567.karjoo.models.File;
import com.hinext.maxis7567.karjoo.models.HomeData;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.hinext.maxis7567.mstools.RtlGridLayoutManager;
import com.maxis7567.msdialog.MSdialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;


import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {
    private View LOADING;
    private ViewGroup viewGroup;
    private HomeData homeData;
    private DisplayMetricsUtils displayMetricsUtils;
    private CircleImageView image;
    private TextView name,address,describe,skillsText,fileText;
    private RecyclerView skillsRec,filesRec;
    private Button call;
    private FileAdapter fileAdapter;
    private SkillsAdapter skillsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        displayMetricsUtils=new DisplayMetricsUtils(this);
        image=findViewById(R.id.DetailProfileImage);
        name=findViewById(R.id.DetailProfileName);
        address=findViewById(R.id.DetailAddress);
        describe=findViewById(R.id.DetailDesc);
        skillsText=findViewById(R.id.DetailSkillTText);
        fileText=findViewById(R.id.DetailFileTExt);
        call=findViewById(R.id.DetailCallBTN);
        skillsRec=findViewById(R.id.DetailSkillsRec);
        filesRec=findViewById(R.id.DetailFileRec);
        viewGroup=findViewById(R.id.DetailView);
        LOADING=new MSdialog(this,viewGroup).Loading(getWindow().getDecorView());
        viewGroup.addView(LOADING);
        homeData= (HomeData) getIntent().getExtras().get("data");
        getDetail();

    }

    private void getDetail() {
        Api.getDetail(this, new Response.Listener<Detail>() {
            @Override
            public void onResponse(Detail response) {
                setUpView(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },homeData.getId(),homeData.getType());
    }

    private void setUpView(final Detail response) {
        Picasso.get()
                .load(Api.SERVER_ADDRESS_IMAGE + homeData.getImage())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.ic_man)
                .centerInside()
                .resize( displayMetricsUtils.convertDIPToPixels(100),displayMetricsUtils.convertDIPToPixels(100))
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Picasso", "fetch image success in first time.");

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(Api.SERVER_ADDRESS_IMAGE + homeData.getImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .placeholder(R.drawable.ic_man)
                                .centerInside()
                                .resize( displayMetricsUtils.convertDIPToPixels(100),displayMetricsUtils.convertDIPToPixels(100))
                                .into(image, new Callback() {

                                    @Override
                                    public void onSuccess() {
                                        Log.e("Picasso", "fetch image success in try again.");
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e("Picasso", e.getMessage());
                                    }

                                });

                    }
                });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", response.getNumber(), null));
                startActivity(intent);            }
        });
        name.setText(homeData.getName());
        address.setText(homeData.getProvince()+"<"+homeData.getCity());
        describe.setText(homeData.getDescribe());
        if (homeData.getType()==1){
            skillsText.setText("مهارت ها");
        }else {
            skillsText.setText("عناوین شغل");
        }
        if (response.getFile().size()>0){
            fileAdapter=new FileAdapter(this,response.getFile());
            filesRec.setLayoutManager(new RtlGridLayoutManager(this,displayMetricsUtils.getScreenDimensionsInDIP().x/100,RecyclerView.VERTICAL,false));
        }else {
            fileText.setVisibility(View.GONE);
            filesRec.setVisibility(View.GONE);
        }

        filesRec.setAdapter(fileAdapter);
        if (homeData.getType()==1){
            skillsAdapter=new SkillsAdapter(this,response.getSkills(),true);
        }else {
            skillsAdapter=new SkillsAdapter(this,response.getSkills(),false);
        }

        skillsRec.setAdapter(skillsAdapter);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        flowLayoutManager.setAlignment(Alignment.RIGHT);

        skillsRec.setLayoutManager(flowLayoutManager);

        viewGroup.removeView(LOADING);
    }
}
