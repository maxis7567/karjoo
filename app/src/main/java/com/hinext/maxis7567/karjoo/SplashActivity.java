package com.hinext.maxis7567.karjoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hinext.maxis7567.karjoo.login.ActiveActivity;
import com.hinext.maxis7567.karjoo.main.MainActivity;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.mstools.NetworkCheck;
import com.maxis7567.msdialog.MSdialog;

public class SplashActivity extends AppCompatActivity {
    private LottieAnimationView logo;
    private View DIALOG;
    private ViewGroup viewGroup;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (flag) {
                viewGroup.removeView(DIALOG);
                networkCheck();
            }

        }
    };
    private boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.SlpashLottie);
        viewGroup = findViewById(R.id.SplashView);
        logo.setSpeed(1.4F);
        logo.playAnimation();
        networkCheck();
    }
    private void networkCheck() {
        if (!NetworkCheck.isNetworkAvailable(this)) {
            flag=true;
            DIALOG = new MSdialog(this, viewGroup).ConfirmDialog(getWindow().getDecorView(), "خطا اینترنت", "اتصال اینترنت خود را چک کنید.", "تلاش دوباره", new MSdialog.MSdialogInterfaceConfirm() {
                @Override
                public void OnConfirmed() {
                    viewGroup.removeView(DIALOG);
                    networkCheck();
                }
            });
            viewGroup.addView(DIALOG);
        } else {
          checkVersion();
        }
    }

    private void checkVersion() {
        Api.checkVersion(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (response.charAt(0))
                {
                    case '1':
                        Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                        SplashActivity.this.startActivity(intent);
                        finish();
                        break;
                    case '2':
                        DIALOG = new MSdialog(SplashActivity.this, viewGroup).DefaultDialog(getWindow().getDecorView(), "بروزرسانی", "نرم افزار جدید لمپا وجود دارد لطفا بروزرسانی کنید.", "بروزرسانی", new MSdialog.MSdialogInterfaceDefault() {
                            @Override
                            public void OnConfirmed() {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("bazaar://details?id=" + SplashActivity.this.getPackageName())));
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + SplashActivity.this.getPackageName())));//DO= new Address
                                    finish();
                                }
                            }

                            @Override
                            public void OnCancel() {
                                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                                SplashActivity.this.startActivity(intent);
                                finish();
                            }
                        });
                        viewGroup.addView(DIALOG);
                        break;
                    case '3':
                        DIALOG = new MSdialog(SplashActivity.this, viewGroup).ConfirmDialog(getWindow().getDecorView(), "بروزرسانی", "نرم افزار جدید لمپا وجود دارد لطفا بروزرسانی کنید.", "بروزرسانی", new MSdialog.MSdialogInterfaceConfirm() {
                            @Override
                            public void OnConfirmed() {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + SplashActivity.this.getPackageName())));
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + SplashActivity.this.getPackageName())));//DO= new Address
                                    finish();
                                }
                            }
                        });
                        viewGroup.addView(DIALOG);
                        break;
                    default:
                        finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DIALOG = new MSdialog(SplashActivity.this, viewGroup).ConfirmDialog(getWindow().getDecorView(), "خطا", "مشکل در ارتباط با سرور", "تلاش دوباره", new MSdialog.MSdialogInterfaceConfirm() {
                    @Override
                    public void OnConfirmed() {
                        viewGroup.removeView(DIALOG);
                        checkVersion();
                    }
                });
                viewGroup.addView(DIALOG);
            }
        },"100");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
