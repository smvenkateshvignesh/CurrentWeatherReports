package com.example.weatherreport.weatherreports;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherreport.R;
import com.example.weatherreport.model.WeatherDataModel;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

public class WeatherReportActivity extends AppCompatActivity implements WeatherReportView, View.OnClickListener {
    private WeatherReportPresenter weatherReportPresenter;
    private Button btnGetWeather;
    private AutoCompleteTextView atvCityName;
    private ProgressDialog dialog = null;
    private TextView tvCityName, tvCityTemperature, tvCityMinAndMAxTemperature, tvCityWeatherType, tvCityWeatherTypeDesc, tvCityHumidityPercentage, tvCityCloudsPercentage;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressedOldTime;
    private GyroscopeObserver gyroscopeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);
        init();
        addClickListener();
    }

    /*
    This method is used to add the all listeners
     */
    private void addClickListener() {
        btnGetWeather.setOnClickListener(this);
        atvCityName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    callWeatherApi();
                }
                return true;
            }
        });
    }

    /*
    This method is used to intilize the views
     */
    private void init() {
        weatherReportPresenter = new WeatherReportPresenter(this);
        gyroscopeObserver = new GyroscopeObserver();

        PanoramaImageView panoramaImageView =findViewById(R.id.panorama_image_view);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        atvCityName = findViewById(R.id.atvCityName);
        tvCityName = findViewById(R.id.tvCityName);
        tvCityTemperature = findViewById(R.id.tvCityTemperature);
        tvCityMinAndMAxTemperature = findViewById(R.id.tvCityMinAndMAxTemperature);
        tvCityWeatherType = findViewById(R.id.tvCityWeatherType);
        tvCityWeatherTypeDesc = findViewById(R.id.tvCityWeatherTypeDesc);
        tvCityHumidityPercentage = findViewById(R.id.tvCityHumidityPercentage);
        tvCityCloudsPercentage = findViewById(R.id.tvCityCloudsPercentage);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetWeather:
                callWeatherApi();
                break;
        }

    }

    /*
    This method is used to check the internet connection and city name, If both are true call the weather api
     */
    private void callWeatherApi() {
        hideKeyboardFrom(this, atvCityName);
        String cityName = atvCityName.getText().toString().trim();
        if (cityName.length() > 0) {
            if (isNetworkConnected())
                weatherReportPresenter.callWeatherApi(cityName);
            else
                Snackbar.make(btnGetWeather, R.string.internet_connection_error, Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(btnGetWeather, R.string.error_enter_city_name, Snackbar.LENGTH_SHORT).show();
        }
    }

    /*
    This method is used to the check the network connection
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /*
    This method is used to hide the keyboard
     */
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showWeatherData(WeatherDataModel weatherDataModel) {
        tvCityName.setText(weatherDataModel.getCityName().trim());
        tvCityTemperature.setText(weatherDataModel.getCityTemperature() + " \u2103");
        tvCityWeatherType.setText(weatherDataModel.getWeatherType());
        tvCityWeatherTypeDesc.setText(weatherDataModel.getWeatherTypeDesc());
        tvCityHumidityPercentage.setText(weatherDataModel.getHumidity() + "%");
        tvCityCloudsPercentage.setText(weatherDataModel.getCloudy() + "%");
        tvCityMinAndMAxTemperature.setText("Min. " + weatherDataModel.getCityMinTemperature() + " \u2103    Max. " + weatherDataModel.getCityMaxTemperature() + " \u2103");
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(btnGetWeather, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setMessage(getString(R.string.progress_dialoug_message));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void hideProgress() {
        if (dialog != null && dialog.isShowing())
            dialog.hide();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedOldTime + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressedOldTime = System.currentTimeMillis();
    }
    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }
}
