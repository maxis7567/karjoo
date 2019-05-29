package com.hinext.maxis7567.karjoo.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.main.MainActivity;
import com.hinext.maxis7567.karjoo.models.Active;
import com.hinext.maxis7567.karjoo.models.ActiveResualt;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.karjoo.services.DataBaseTokenID;
import com.maxis7567.msdialog.MSdialog;
import com.onurkaganaldemir.ktoastlib.KToast;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class ActiveActivity extends AppCompatActivity {
    private EditText code1, code2, code3, code4;
    private View DIALOG;
    private ViewGroup viewGroup;
    private TextView remainingS, number, leftBTN, rightBTN;
    private LinearLayout codesView;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        code1 = findViewById(R.id.Code_Code1);
        code2 = findViewById(R.id.Code_Code2);
        code3 = findViewById(R.id.Code_Code3);
        code4 = findViewById(R.id.Code_Code4);
        viewGroup = findViewById(R.id.Active_View);
        remainingS = findViewById(R.id.Active_RemaininS);
        number = findViewById(R.id.Active_number);
        leftBTN = findViewById(R.id.Active_LeftBtn);
        rightBTN = findViewById(R.id.Active_rightBTN);
        codesView = findViewById(R.id.Code_linearLayout_Code);
        number.setText("کد فعال سازی به شماره " + (getIntent().getStringExtra("number")) + " ارسال شد");
        leftBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftBTN.getText().toString().equals("تغییر شماره")) {
                    finish();
                } else {
                    sendNumber();
                    leftBTN.setText("تغییر شماره");
                }
            }
        });

        rightBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });
        sendNumber();
        setUpCodes();


    }

    private void Timer() {
        new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {
                remainingS.setText(String.format("%d : %d " + " تا ارسال مجدد",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                ));
            }

            public void onFinish() {
                remainingS.setText("0:0");
                leftBTN.setText("ارسال مجدد");
            }

        }.start();


    }

    private void sendNumber() {

        DIALOG = new MSdialog(this, viewGroup).Loading(getWindow().getDecorView());
        viewGroup.addView(DIALOG);
        Api.sendPhoneNumber(this, new Response.Listener<Active>() {
            @Override
            public void onResponse(final Active response) {
                code=String.valueOf(response.getCode());
                viewGroup.removeView(DIALOG);
                KToast.successToast(ActiveActivity.this, "منتظر دریافت پیامک باشید", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        code1.setText(String.valueOf(code.charAt(0)));
                        code2.setText(String.valueOf(code.charAt(1)));
                        code3.setText(String.valueOf(code.charAt(2)));
                        code4.setText(String.valueOf(code.charAt(3)));

                    }
                }, 3000);
                Timer();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DIALOG = new MSdialog(ActiveActivity.this, viewGroup).ConfirmDialog(getWindow().getDecorView(), "خطا", "مشکل در ارتباط با سرور", "تلاش دوباره", new MSdialog.MSdialogInterfaceConfirm() {
                    @Override
                    public void OnConfirmed() {
                        viewGroup.removeView(DIALOG);
                        sendNumber();
                    }
                });
                viewGroup.addView(DIALOG);
            }
        }, getIntent().getStringExtra("number"));

    }

    void setUpCodes() {
        code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    String readCode() {
        String tmp = "";
        tmp = tmp.concat(code1.getText().toString());
        tmp = tmp.concat(code2.getText().toString());
        tmp = tmp.concat(code3.getText().toString());
        tmp = tmp.concat(code4.getText().toString());
        return tmp;
    }

    private void sendCode() {
        if (readCode().length() == 4) {
            DIALOG = new MSdialog(this, viewGroup).Loading(getWindow().getDecorView());
            viewGroup.addView(DIALOG);
            Api.sendCode(this, new Response.Listener<ActiveResualt>() {
                @Override
                public void onResponse(ActiveResualt response) {
                    if (response.getStatus().equals("OK")) {
                        if ("2".equals(response.getMessage())) {
                            Intent intent = new Intent(ActiveActivity.this, CreateProfileActivity.class);
                            intent.putExtra("token", response.getTokenId());
                            startActivity(intent);
                        } else {
                            DataBaseTokenID.WriteTokenID(ActiveActivity.this, response.getTokenId());
                            startActivity(new Intent(ActiveActivity.this, MainActivity.class));
                        }
                    } else {
                        viewGroup.removeView(DIALOG);
                        DIALOG = new MSdialog(ActiveActivity.this, viewGroup).ConfirmDialog(getWindow().getDecorView(), "خطا", response.getMessage(), "تایید", new MSdialog.MSdialogInterfaceConfirm() {
                            @Override
                            public void OnConfirmed() {
                                viewGroup.removeView(DIALOG);
                            }
                        });
                        viewGroup.addView(DIALOG);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    viewGroup.removeView(DIALOG);
                    DIALOG = new MSdialog(ActiveActivity.this, viewGroup).ConfirmDialog(getWindow().getDecorView(), "خطا", "مشکل در ارتباط با سرور", "تلاش دوباره", new MSdialog.MSdialogInterfaceConfirm() {
                        @Override
                        public void OnConfirmed() {
                            viewGroup.removeView(DIALOG);
                            sendCode();
                        }
                    });
                    viewGroup.addView(DIALOG);
                }
            }, getIntent().getStringExtra("number"), readCode());
        } else {
            Animation shake = AnimationUtils.loadAnimation(ActiveActivity.this, R.anim.shake);
            Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v1.vibrate(400);
            codesView.startAnimation(shake);
        }
    }


}
