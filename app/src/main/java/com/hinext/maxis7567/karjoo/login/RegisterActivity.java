package com.hinext.maxis7567.karjoo.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
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
import com.hinext.maxis7567.karjoo.main.fragments.profile.ProfileFragment;
import com.hinext.maxis7567.karjoo.models.Province;

import com.hinext.maxis7567.karjoo.models.User;
import com.hinext.maxis7567.karjoo.services.DataBaseTokenID;
import com.hinext.maxis7567.mstools.RawStringReader;
import com.maxis7567.msdialog.MSdialog;
import com.maxis7567.msdialog.SpinnerProvince;
import com.onurkaganaldemir.ktoastlib.KToast;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

public class RegisterActivity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private View DIALOG;
    private TextView provinceBTN, countyBTN, cityBTN;
    private EditText firstName, lastName,address;
    private TextView send;
    private CircleImageView image;
    private ImageView imageBTN;
    private RadioButton femaleBTN,maleBTN;
    private Button karjooBTN,karfarmaBTN;
    private File file=null;
    private int type=1;

    private List<SpinnerProvince> spinnerProvinces = new ArrayList<>();
    private List<SpinnerProvince> spinnerCounties = new ArrayList<>();
    private List<SpinnerProvince> spinnerCity = new ArrayList<>();
    private List<Province> provinces = new ArrayList<>();

    private long provinceId, countiesId, cityId;
    private boolean doubleBackToExitPressedOnce=false;


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

        setContentView(R.layout.activity_register);


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
        karfarmaBTN=findViewById(R.id.RegTypeKarfarma);
        karjooBTN=findViewById(R.id.RegTypeKarjo);
        karjooBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                karjooBTN.setBackgroundResource(R.drawable.shape_leftcorner30_bgprimary);
                karjooBTN.setTextColor(getResources().getColor(R.color.textPrimary));
                karfarmaBTN.setBackgroundResource(R.drawable.shape_rightcorner30_strokeprimary);
                karfarmaBTN.setTextColor(getResources().getColor(R.color.DefaultTextColor));
                type=1;
            }
        });
        karfarmaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                karjooBTN.setBackgroundResource(R.drawable.shape_leftcorner30_strokeprimary);
                karjooBTN.setTextColor(getResources().getColor(R.color.DefaultTextColor));
                karfarmaBTN.setBackgroundResource(R.drawable.shape_rightcorner30_bgprimary);
                karfarmaBTN.setTextColor(getResources().getColor(R.color.textPrimary));
                type=2;
            }
        });
        imageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RegisterActivity.this, ImagePickActivity.class);
                intent1.putExtra(IS_NEED_CAMERA, true);
                intent1.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
            }
        });
        provinceBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIALOG = new MSdialog(RegisterActivity.this, viewGroup).Spinner(getWindow().getDecorView(), spinnerProvinces, new MSdialog.MSdialogInterfaceSpinner() {
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
                DIALOG = new MSdialog(RegisterActivity.this, viewGroup).Spinner(getWindow().getDecorView(), spinnerCounties, new MSdialog.MSdialogInterfaceSpinner() {
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
                DIALOG = new MSdialog(RegisterActivity.this, viewGroup).Spinner(getWindow().getDecorView(), spinnerCity, new MSdialog.MSdialogInterfaceSpinner() {
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

    private String validBirthday(int persianYear, int persianMonth, int persianDay) {
        String year=String.valueOf(persianYear),month=String.valueOf(persianMonth),day=String.valueOf(persianDay);
        if (month.length()!=2){
            month="0";
            month=month.concat(String.valueOf(persianMonth));
        }
        if (day.length()!=2){
            day="0";
            day=day.concat(String.valueOf(persianMonth));
        }
        return year+"/"+month+"/"+day;
    }

    private void checkValidation() {
        Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
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
                                sendData();
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

    private void sendData() {
        DIALOG=new MSdialog(this,viewGroup).Loading(getWindow().getDecorView());
        viewGroup.addView(DIALOG);
        int a=maleBTN.isSelected()?1:2;
        final ProgressDialog pd;
        pd = new ProgressDialog(RegisterActivity.this);
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        AndroidNetworking.upload("http://192.168.1.104:8080/v1/user/active")
                .addHeaders("Accept", "application/json")
                .addHeaders("token", getIntent().getStringExtra("tokenId"))
                .addMultipartFile("image",file)
                .addMultipartParameter("name", firstName.getText().toString())
                .addMultipartParameter("lName", lastName.getText().toString())
                .addMultipartParameter("address", address.getText().toString())
                .addMultipartParameter("cityId", String.valueOf(cityId))
                .addMultipartParameter("gender", String.valueOf(a))
                .addMultipartParameter("type",String.valueOf(type))
                .setTag("activeUser")
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
                        DataBaseTokenID.WriteTokenID(RegisterActivity.this,getIntent().getStringExtra("tokenId"));
                        ProfileFragment.needRefresh=true;
                        finish();
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.cancel();
                        viewGroup.removeView(DIALOG);
                        DIALOG = new MSdialog(RegisterActivity.this, viewGroup).DefaultDialog(getWindow().getDecorView(), "خطا", "مشکل در ارتبات با سرور", "تلاش دوباره",
                                new MSdialog.MSdialogInterfaceDefault() {
                                    @Override
                                    public void OnConfirmed() {
                                        viewGroup.removeView(DIALOG);
                                        sendData();
                                    }

                                    @Override
                                    public void OnCancel() {
                                        viewGroup.removeView(DIALOG);
                                    }
                                });
                        viewGroup.addView(DIALOG);
                    }
                });
//        Api.sendRegisterData(this, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                startActivity(new Intent(RegisterActivity.this,FinishActivity.class));
//                finish();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                viewGroup.removeView(DIALOG);
//                DIALOG = new MSdialog(RegisterActivity.this, viewGroup).DefaultDialog(getWindow().getDecorView(), "خطا", "مشکل در ارتباط با سرور", "تلاش دوباره", new MSdialog.MSdialogInterfaceDefault() {
//                    @Override
//                    public void OnConfirmed() {
//                        viewGroup.removeView(DIALOG);
//                        sendData();
//                    }
//
//                    @Override
//                    public void OnCancel() {
//                        viewGroup.removeView(DIALOG);
//                    }
//                });
//                viewGroup.addView(DIALOG);
//            }
//        },firstName.getText().toString(),getIntent().getStringExtra("number"),lastName.getText().toString(),birthdayBTN.getText().toString(),provinceId,countiesId,cityId);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        }
        doubleBackToExitPressedOnce = true;
        KToast.warningToast(RegisterActivity.this, "برای خروج دکمه بازگشت را دوباره بزنید", Gravity.BOTTOM, KToast.LENGTH_AUTO);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constant.REQUEST_CODE_PICK_IMAGE){
            if (resultCode == RESULT_OK) {
            ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            if (!list.isEmpty()) {
                file=new File(list.get(0).getPath());
                image.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
            }
        }
    }



                }
}
