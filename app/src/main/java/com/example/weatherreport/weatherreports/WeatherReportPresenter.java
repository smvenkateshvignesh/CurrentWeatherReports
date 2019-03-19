package com.example.weatherreport.weatherreports;

import com.example.weatherreport.services.WeatherAsyncTask;

class WeatherReportPresenter {
    private WeatherReportView weatherReportView;

    WeatherReportPresenter(WeatherReportView weatherReportView) {
        this.weatherReportView = weatherReportView;
    }

    /*
    This method is used to call the weather api in async task
     */
    void callWeatherApi(String cityName) {
        new WeatherAsyncTask(weatherReportView).execute(cityName);
    }
}
