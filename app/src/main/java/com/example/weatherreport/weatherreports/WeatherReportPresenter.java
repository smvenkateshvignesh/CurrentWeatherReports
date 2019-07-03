package com.example.weatherreport.weatherreports;

import com.example.weatherreport.services.WeatherAsyncTask;
import com.example.weatherreport.services.retrofit.RetrofitClientInstance;
import com.example.weatherreport.weatherreports.model.CurrentWeatherReportResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class WeatherReportPresenter {
    private WeatherReportView weatherReportView;

    WeatherReportPresenter(WeatherReportView weatherReportView) {
        this.weatherReportView = weatherReportView;
    }

    /*
    This method is used to call the weather api in async task
     */
    void callWeatherApi(String cityName) {
        Call<CurrentWeatherReportResponse> call = RetrofitClientInstance.getApi().getCurrentWeatherReport(cityName,"8000df826430227ec833c3f6e838cb6a");
        call.enqueue(new Callback<CurrentWeatherReportResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherReportResponse> call, Response<CurrentWeatherReportResponse> response) {
                weatherReportView.hideProgress();
//                weatherReportView.showWeatherData();
            }

            @Override
            public void onFailure(Call<CurrentWeatherReportResponse> call, Throwable t) {

            }
        });
        new WeatherAsyncTask(weatherReportView).execute(cityName);
    }
}
