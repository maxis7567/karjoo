package com.hinext.maxis7567.karjoo.main.fragments.profile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.login.LoginActivity;
import com.hinext.maxis7567.karjoo.models.User;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.karjoo.services.DataBaseTokenID;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.maxis7567.msdialog.MSdialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context context;
    private Activity activity;
    private View view;
    private ViewGroup viewGroup;
    private View DIALOG;

    private ImageView exitBTN,settingBTN;
    private CircleImageView image;
    private TextView name,listBTN,province,couny,city,address;
    private Button addBTN;
    private User user;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        activity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        viewGroup=view.findViewById(R.id.FragProfileView);
        exitBTN=view.findViewById(R.id.FragProfileExist);
        settingBTN=view.findViewById(R.id.FragProfileSetting);
        image=view.findViewById(R.id.FragProfileImage);
        name=view.findViewById(R.id.FragProfileName);
        listBTN=view.findViewById(R.id.FragProfileListBTN);
        province=view.findViewById(R.id.FragProfileProvince);
        couny=view.findViewById(R.id.FragProfileCounty);
        city=view.findViewById(R.id.FragProfileCity);
        address=view.findViewById(R.id.FragProfileAddress);
        addBTN=view.findViewById(R.id.FragProfileAddBTN);
        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseTokenID.ResetTokenID(context);
                DIALOG= new MSdialog(context,viewGroup).ConfirmDialog(activity.getWindow().getDecorView()
                        , "ورود / ثبت نام", "برای استفاده از این قسمت باید وارد حساب خود شوید",
                        "ورود / ثبت نام", new MSdialog.MSdialogInterfaceConfirm() {
                            @Override
                            public void OnConfirmed() {
                                activity.startActivity(new Intent(context, LoginActivity.class));
                            }
                        });
                viewGroup.addView(DIALOG);
            }
        });
    settingBTN.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent
        }
    });
    image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //upload image
        }
    });
    listBTN.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listBTN.getText().toString().equals("لیست رزومه ها")){

            }else {
                //intent
            }
        }
    });
    addBTN.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //intent
        }
    });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!DataBaseTokenID.isLogin(context)){
            DIALOG= new MSdialog(context,viewGroup).ConfirmDialog(activity.getWindow().getDecorView()
                    , "ورود / ثبت نام", "برای استفاده از این قسمت باید وارد حساب خود شوید",
                    "ورود / ثبت نام", new MSdialog.MSdialogInterfaceConfirm() {
                        @Override
                        public void OnConfirmed() {
                            activity.startActivity(new Intent(context, LoginActivity.class));
                        }
                    });
            viewGroup.addView(DIALOG);
        }else if (user==null){
            DIALOG=new MSdialog(context,viewGroup).Loading(activity.getWindow().getDecorView());
            viewGroup.addView(DIALOG);
            Api.getUserData(context, new Response.Listener<User>() {
                @Override
                public void onResponse(User response) {
                    user=response;
                    setUpView();
                    viewGroup.removeView(DIALOG);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    viewGroup.removeView(DIALOG);
                    DIALOG = new MSdialog(context, viewGroup).DefaultDialog(activity.getWindow().getDecorView(), "خطا", "مشکل در ارتبات با سرور", "تلاش دوباره",
                            new MSdialog.MSdialogInterfaceDefault() {
                                @Override
                                public void OnConfirmed() {
                                    viewGroup.removeView(DIALOG);
                                    onResume();
                                }

                                @Override
                                public void OnCancel() {
                                    viewGroup.removeView(DIALOG);
                                }
                            });
                    viewGroup.addView(DIALOG);
                }
            });
        }else {
            setUpView();
        }
    }

    private void setUpView() {
        final DisplayMetricsUtils displayMetricsUtils=new DisplayMetricsUtils(context);
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
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e("Picasso", e.getMessage());
                                    }

                                });

                    }
                });
        name.setText(user.getName());
        if (user.getType()!=1){
            listBTN.setText("لیست کار ها");
        }
        province.setText(user.getProvince());
        couny.setText(user.getCounty());
        city.setText(user.getCity());
        address.setText(user.getAddress());
    }
}
