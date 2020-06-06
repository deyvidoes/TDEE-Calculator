package com.deyvitineo.tdee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "SplashScreen";
    private SharedPreferences sp;
    private boolean alreadyStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("doNotShow", MODE_PRIVATE);
        alreadyStarted = sp.getBoolean("doNotShowAgain", false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (alreadyStarted == true) {
                    Intent i = new Intent(SplashScreen.this, TdeeCalculator.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreen.this, GetStarted.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 750);
    }
}

