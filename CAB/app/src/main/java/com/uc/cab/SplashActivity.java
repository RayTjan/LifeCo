package com.uc.cab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread splashthread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(500);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent welcomeIntent = new Intent(SplashActivity.this,WelcomeActivity.class);
                    startActivity(welcomeIntent);
                }
            }
        };
        splashthread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
