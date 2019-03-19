package com.example.weatherreport.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.weatherreport.weatherreports.WeatherReportActivity;
import com.example.weatherreport.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        navigateToWeatherReportScreen();
    }

    /*
    This method is used to navigate the screen to WeatherReportActivity after the specified time
     */
    private void navigateToWeatherReportScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, WeatherReportActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
