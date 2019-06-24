package com.hinext.maxis7567.karjoo.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.main.fragments.home.HomeFragment;
import com.hinext.maxis7567.karjoo.main.fragments.offer.JobFragment;
import com.hinext.maxis7567.karjoo.main.fragments.profile.ProfileFragment;
import com.hinext.maxis7567.karjoo.main.fragments.request.RequestFragment;
import com.maxis7567.msdialog.MSdialog;
import com.onurkaganaldemir.ktoastlib.KToast;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment home, job, request, profile;
    private ImageView aboutUsBTN, sendNewsBTN, homeIc, jobIc, profileIc, requestIc;
    private TextView homeTx, jobTx, profileTx, requestTx;
    private LinearLayout homeBTN, jobBTN, profileBTN, requestBTN;
    private int fragContainerId ;
    private boolean flag=false;
    private View LOADING;
    private View DIALOG;
    private ViewGroup viewGroup;
    private boolean doubleBackToExitPressedOnce=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragContainerId= R.id.Main_FragCountainer;
        homeBTN = findViewById(R.id.Mian_Home_BTN);
        homeIc = findViewById(R.id.Main_Home_Ic);
        homeTx = findViewById(R.id.Main_Home_Tx);
        jobBTN = findViewById(R.id.Mian_Job_BTN);
        jobIc = findViewById(R.id.Main_Job_Ic);
        jobTx = findViewById(R.id.Main_Job_Tx);
        profileBTN = findViewById(R.id.Main_Profile_BTN);
        profileIc = findViewById(R.id.Main_Profile_Ic);
        profileTx = findViewById(R.id.Main_Profile_Tx);
        requestBTN =findViewById(R.id.Main_Request_BTN);
        requestIc =findViewById(R.id.Main_Request_Ic);
        requestTx =findViewById(R.id.Main_Request_Tx);
        viewGroup=findViewById(R.id.Main_viewgroup);
        LOADING=new MSdialog(this,viewGroup).Loading(getWindow().getDecorView());
        setUpBTN();
        fragmentManager = getSupportFragmentManager();
        home = new HomeFragment();
        job = new JobFragment();
        request = new RequestFragment();
        profile =new ProfileFragment();
        transactionManager(1);

    }

    private void setUpBTN() {
        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionManager(1);
            }
        });
        jobBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionManager(2);
            }
        });
        profileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionManager(3);
            }
        });
        requestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionManager(4);
            }
        });

    }

    private void transactionManager(int i) {
        Fragment f;
        switch (i) {
            case 1:
                f = fragmentManager.findFragmentByTag("Home");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(fragContainerId, home, "Home").commit();
                    homeIc.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
                    homeTx.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    jobIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    jobTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    profileIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    profileTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    requestIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    requestTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));

                }
                break;
            case 2:
                f = fragmentManager.findFragmentByTag("Job");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(fragContainerId, job, "Job").commit();
                    homeIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    homeTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    jobIc.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
                    jobTx.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    profileIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    profileTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));

                    requestIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    requestTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));

                }
                break;
            case 3:
                f = fragmentManager.findFragmentByTag("Profile");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(fragContainerId, profile, "Profile").commit();
                    homeIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    homeTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    jobIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    jobTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    profileIc.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
                    profileTx.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    requestIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    requestTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));

                }
                break;
            case 4:
                f = fragmentManager.findFragmentByTag("Request");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(fragContainerId, request, "Request").commit();
                    homeIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    homeTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    jobIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    jobTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    requestIc.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
                    requestTx.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    profileIc.setColorFilter(ContextCompat.getColor(this, R.color.backgroundGrey2));
                    profileTx.setTextColor(ContextCompat.getColor(this, R.color.backgroundGrey2));

                }
                break;
        }
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
            KToast.warningToast(MainActivity.this, "برای خروج دکمه بازگشت را دوباره بزنید", Gravity.BOTTOM, KToast.LENGTH_AUTO);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
    }

}
