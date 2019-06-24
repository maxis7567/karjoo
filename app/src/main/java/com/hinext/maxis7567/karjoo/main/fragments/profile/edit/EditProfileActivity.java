package com.hinext.maxis7567.karjoo.main.fragments.profile.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.login.RegisterActivity;
import com.hinext.maxis7567.karjoo.main.fragments.profile.ProfileFragment;
import com.hinext.maxis7567.karjoo.models.Province;
import com.hinext.maxis7567.karjoo.models.User;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.karjoo.services.DataBaseTokenID;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.hinext.maxis7567.mstools.RawStringReader;
import com.maxis7567.msdialog.MSdialog;
import com.maxis7567.msdialog.SpinnerProvince;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

public class EditProfileActivity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private View DIALOG;
    private TextView provinceBTN, countyBTN, cityBTN;
    private EditText firstName, lastName,address;
    private TextView send;
    private CircleImageView image;
    private ImageView imageBTN;
    private RadioButton femaleBTN,maleBTN;
    private File file=null;


    private List<SpinnerProvince> spinnerProvinces = new ArrayList<>();
    private List<SpinnerProvince> spinnerCounties = new ArrayList<>();
    private List<SpinnerProvince> spinnerCity = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();

    private long provinceId, countiesId, cityId;
    private DisplayMetricsUtils displayMetricsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Gson gson = new Gson();
            List<Province> province;
            province = gson.fromJson(RawStringReader.readRawFile(this, R.raw.address_database), new TypeToken<ArrayList<Province>>() {
            }.getType());
            this.provinces = province;
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < provinces.size(); i++) {
            SpinnerProvince spinnerProvince = new SpinnerProvince();
            spinnerProvince.setId(provinces.get(i).getId());
            spinnerProvince.setName(provinces.get(i).getProvinceName());
            spinnerProvinces.add(spinnerProvince);
        }
        setContentView(R.layout.activity_edit_profile);
        final User user= (User) getIntent().getExtras().get("user");
        viewGroup = findViewById(R.id.RegisterView);
        provinceBTN = findViewById(R.id.FragProfileProvince);
        countyBTN = findViewById(R.id.FragProfileCounty);
        cityBTN = findViewById(R.id.FragProfileCity);
        firstName = findViewById(R.id.FragProfileListBTN);
        lastName = findViewById(R.id.FragProfileLName);
        send = findViewById(R.id.RegSendBtn);
        address=findViewById(R.id.FragProfileAddress);
        image=findViewById(R.id.RegImage);
        imageBTN=findViewById(R.id.RegImageBTN);
        femaleBTN=findViewById(R.id.RegFemaleBTN);
        maleBTN=findViewById(R.id.RegMaleBTN);
        getFile();
        displayMetricsUtils=new DisplayMetricsUtils(this);
        Picasso.get()
                .load(Api.SERVER_ADDRESS_IMAGE + user.getImage())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .centerInside()
                .resize(displayMetricsUtils.convertDIPToPixels(100), displayMetricsUtils.convertDIPToPixels(100))
                .placeholder(R.drawable.ic_man)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Picasso", "fetch image success in first time.");
                        getFile();

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(Api.SERVER_ADDRESS_IMAGE + user.getImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .centerInside()
                                .resize(displayMetricsUtils.convertDIPToPixels(100), displayMetricsUtils.convertDIPToPixels(100))
                                .placeholder(R.drawable.ic_man)
                                .into(image, new Callback() {

                                    @Override
                                    public void onSuccess() {
                                        Log.e("Picasso", "fetch image success in try again.");
                                        getFile();
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e("Picasso", e.getMessage());
                                    }

                                });

                    }
                });


        firstName.setText(user.getName());
        address.setText(user.getAddress());
        imageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditProfileActivity.this, ImagePickActivity.class);
                intent1.putExtra(IS_NEED_CAMERA, true);
                intent1.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
            }
        });

        provinceBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIALOG = new MSdialog(EditProfileActivity.this, viewGroup).Spinner(getWindow().getDecorView(), spinnerProvinces, new MSdialog.MSdialogInterfaceSpinner() {
                    @Override
                    public void OnSelect(SpinnerProvince s) {
                        provinceBTN.setText(s.getName());
                        viewGroup.removeView(DIALOG);
                        countyBTN.setClickable(true);
                        cityBTN.setClickable(false);
                        provinceId = s.getId();
                        countyBTN.setText("(انتخاب)");
                        cityBTN.setText("(انتخاب)");
                        spinnerCounties=new ArrayList<>();
                        for (int i = 0; i < spinnerProvinces.size(); i++) {
                            if (spinnerProvinces.get(i).getId() == s.getId()) {
                                for (int j = 0; j < provinces.get(i).getCounties().size(); j++) {
                                    SpinnerProvince spinnerProvince = new SpinnerProvince();
                                    spinnerProvince.setId(provinces.get(i).getCounties().get(j).getId());
                                    spinnerProvince.setName(provinces.get(i).getCounties().get(j).getCountyName());
                                    spinnerCounties.add(spinnerProvince);
                                }
                                return;
                            }
                        }

                    }

                    @Override
                    public void OnCancel() {
                        viewGroup.removeView(DIALOG);
                    }
                });
                viewGroup.addView(DIALOG);
            }
        });
        countyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIALOG = new MSdialog(EditProfileActivity.this, viewGroup).Spinner(getWindow().getDecorView(), spinnerCounties, new MSdialog.MSdialogInterfaceSpinner() {
                    @Override
                    public void OnSelect(SpinnerProvince s) {
                        countyBTN.setText(s.getName());
                        viewGroup.removeView(DIALOG);
                        cityBTN.setClickable(true);
                        countiesId=s.getId();
                        cityBTN.setText("(انتخاب)");
                        spinnerCity=new ArrayList<>();
                        for (int i = 0; i < provinces.size(); i++) {
                            if (provinces.get(i).getId() == provinceId) {
                                for (int j = 0; j < provinces.get(i).getCounties().size(); j++) {
                                    if (provinces.get(i).getCounties().get(j).getId() == s.getId()) {
                                        for (int k = 0; k < provinces.get(i).getCounties().get(j).getCities().size(); k++) {
                                            SpinnerProvince spinnerProvince = new SpinnerProvince();
                                            spinnerProvince.setId(provinces.get(i).getCounties().get(j).getCities().get(k).getId());
                                            spinnerProvince.setName(provinces.get(i).getCounties().get(j).getCities().get(k).getCityName());
                                            spinnerCity.add(spinnerProvince);
                                        }
                                        return;
                                    }

                                }
                                return;
                            }
                        }

                    }

                    @Override
                    public void OnCancel() {
                        viewGroup.removeView(DIALOG);
                    }
                });
                viewGroup.addView(DIALOG);
            }
        });

        cityBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIALOG = new MSdialog(EditProfileActivity.this, viewGroup).Spinner(getWindow().getDecorView(), spinnerCity, new MSdialog.MSdialogInterfaceSpinner() {
                    @Override
                    public void OnSelect(SpinnerProvince s) {
                        viewGroup.removeView(DIALOG);
                        cityBTN.setText(s.getName());
                        cityId=s.getId();
                    }

                    @Override
                    public void OnCancel() {
                        viewGroup.removeView(DIALOG);
                    }
                });
                viewGroup.addView(DIALOG);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
//        birthdayBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                picker = new PersianDatePickerDialog(RegisterActivity.this)
//                        .setPositiveButtonString("تایید")
//                        .setNegativeButton("انصراف")
//                        .setTodayButtonVisible(false)
//                        .setInitDate(initDate)
//                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
//                        .setMinYear(1300)
//                        .setActionTextColor(Color.GRAY)
//                        .setListener(new Listener() {
//                            @Override
//                            public void onDateSelected(PersianCalendar persianCalendar) {
//
//                                birthdayBTN.setText( validBirthday(persianCalendar.getPersianYear(),persianCalendar.getPersianMonth(),persianCalendar.getPersianDay()));
//                            }
//
//                            @Override
//                            public void onDismissed() {
//
//                            }
//                        });
//
//                picker.show();
//            }
//        });
        countyBTN.setClickable(false);
        cityBTN.setClickable(false);
    }

    private void getFile() {
        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                file = new File(EditProfileActivity.this.getCacheDir(), "image.jpg");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//Convert bitmap to byte array
                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.write(bitmapdata);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

            }
        }).start();
    }

    private void checkValidation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        if (firstName.getText().toString().length() > 0) {
            if (lastName.getText().toString().length() > 0) {
                if (provinceBTN.getText().equals("(انتخاب)")) {
                    provinceBTN.startAnimation(shake);
                } else {
                    if (countyBTN.getText().equals("(انتخاب)")) {
                        countyBTN.startAnimation(shake);
                    } else {
                        if (cityBTN.getText().equals("(انتخاب)")) {
                            cityBTN.startAnimation(shake);
                        } else if (address.getText().toString().length()<0){
                            address.startAnimation(shake);
                        }else if (file==null){
                            image.startAnimation(shake);
                        }else {
                            editProfile();
                        }
                    }
                }
            } else {
                lastName.startAnimation(shake);
            }
        } else {
            firstName.startAnimation(shake);
        }
    }

    private void editProfile() {
        DIALOG=new MSdialog(this,viewGroup).Loading(getWindow().getDecorView());
        viewGroup.addView(DIALOG);
        int a=maleBTN.isSelected()?1:2;
        final ProgressDialog pd;
        pd = new ProgressDialog(EditProfileActivity.this);
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        AndroidNetworking.upload("http://192.168.1.47:8080/v1/user/edit")
                .addHeaders("Accept", "application/json")
                .addHeaders("token", DataBaseTokenID.GetTokenID(this))
                .addMultipartFile("image",file)
                .addMultipartParameter("name", firstName.getText().toString())
                .addMultipartParameter("lName", lastName.getText().toString())
                .addMultipartParameter("address", address.getText().toString())
                .addMultipartParameter("cityId", String.valueOf(cityId))
                .addMultipartParameter("gender", String.valueOf(a))
                .setTag("EditUser")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        if (bytesUploaded!=0){
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
                        ProfileFragment.needRefresh=true;
                        finish();
                    }

                    @Override
                    public void onError(ANError error) {
                        viewGroup.removeView(DIALOG);
                        pd.cancel();
                        DIALOG = new MSdialog(EditProfileActivity.this, viewGroup).DefaultDialog(getWindow().getDecorView(), "خطا", "مشکل در ارتبات با سرور", "تلاش دوباره",
                                new MSdialog.MSdialogInterfaceDefault() {
                                    @Override
                                    public void OnConfirmed() {
                                        viewGroup.removeView(DIALOG);
                                        editProfile();
                                    }

                                    @Override
                                    public void OnCancel() {
                                        viewGroup.removeView(DIALOG);
                                    }
                                });
                        viewGroup.addView(DIALOG);
                    }
                });
    }

}
