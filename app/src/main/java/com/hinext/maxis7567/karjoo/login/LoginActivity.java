package com.hinext.maxis7567.karjoo.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hinext.maxis7567.karjoo.R;
import com.onurkaganaldemir.ktoastlib.KToast;

public class LoginActivity extends AppCompatActivity {
    private EditText number;
    private TextView loginBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        number = findViewById(R.id.Login_Number);
        loginBTN = findViewById(R.id.Login_LoginBTN);
        if (getIntent().getStringExtra("number")!=null){
            number.setText(getIntent().getStringExtra("number"));
        }
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNumber(number.getText().toString())) {
                    Intent intent = new Intent(LoginActivity.this, ActiveActivity.class);
                    intent.putExtra("number", number.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    number.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake));
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v1.vibrate(400);
                    KToast.errorToast(LoginActivity.this, "شماره وارد شده درست نیست", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                }
            }
        });
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 2003);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    boolean CheckNumber(String number) {
        if (number.length() == 11) {
            String tmp = String.valueOf(number.charAt(0));
            tmp = tmp.concat(String.valueOf(number.charAt(1)));
            return tmp.equals("09");
        } else return false;

    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
