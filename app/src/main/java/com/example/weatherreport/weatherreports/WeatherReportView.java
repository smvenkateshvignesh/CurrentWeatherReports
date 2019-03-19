package com.example.weatherreport.weatherreports;


import com.example.weatherreport.model.WeatherDataModel;

public interface WeatherReportView {
    /*
    This method is used to show the weather data for the given city
     */
    void showWeatherData(WeatherDataModel weatherDataModel);

    /*
       This method is used to show the error message
     */
    void showErrorMessage(String message);

    /*
       This method is used to show the progress bar
     */
    void showProgress();

    /*
        This method is used to hide the progress bar
     */
    void hideProgress();
}
