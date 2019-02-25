package com.omerilhanli.tmdbmove.UI.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.omerilhanli.tmdbmove.R;
import com.omerilhanli.tmdbmove.asistant.common.Constants;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initAll();
    }

    private void initAll() {

        setContentView(R.layout.activity_splash);

        initDelayed();
    }

    private void initDelayed() {

        Handler handle = new Handler();

        handle.postDelayed(new Runnable() {

            @Override
            public void run() {

                startApp();

            }

        }, Constants.SPLASH_DURATION);
    }

    private void startApp() {

        MainActivity.startActivityFrom(this);
    }
}
