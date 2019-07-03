package com.example.weatherreport.services.retrofit;


import com.example.weatherreport.weatherreports.model.CurrentWeatherReportResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Venkatesh Vignesh on 7/3/2019.
 */

public interface GetDataService {

    @GET("weather")
    Call<CurrentWeatherReportResponse> getCurrentWeatherReport(@Query("q")String cityName, @Query("APPID")String appId);
}