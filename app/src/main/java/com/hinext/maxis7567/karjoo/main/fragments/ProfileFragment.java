package com.hinext.maxis7567.karjoo.main.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.login.LoginActivity;
import com.hinext.maxis7567.karjoo.services.DataBaseTokenID;
import com.maxis7567.msdialog.MSdialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context context;
    private Activity activity;
    private View view;
    private ViewGroup viewGroup;
    private View DIALOG;


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
        }
        return view;
    }


}
