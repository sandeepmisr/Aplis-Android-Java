package com.edu.aplis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
   // SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // hideTitleBar();
        setContentView(R.layout.activity_splash);
        //sessionManager=new SessionManager(this);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
