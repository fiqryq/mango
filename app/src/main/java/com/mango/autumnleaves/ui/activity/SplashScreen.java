package com.mango.autumnleaves.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.mango.autumnleaves.ui.activity.login.LoginActivity;

public class SplashScreen extends BaseActivity {

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
