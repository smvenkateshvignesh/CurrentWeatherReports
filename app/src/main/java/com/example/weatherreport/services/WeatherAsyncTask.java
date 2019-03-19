package com.example.weatherreport.services;

import android.os.AsyncTask;
import android.util.Log;


import com.example.weatherreport.model.WeatherDataModel;
import com.example.weatherreport.weatherreports.WeatherReportView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class WeatherAsyncTask extends AsyncTask<String, Void, JSONObject> {
    /*open weather api
    */
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "8000df826430227ec833c3f6e838cb6a";
    private WeatherReportView weatherReportView;

    public WeatherAsyncTask(WeatherReportView weatherReportView) {
        this.weatherReportView = weatherReportView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        weatherReportView.showProgress();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        weatherReportView.hideProgress();
        try {
            if (jsonObject != null) {
                int responseCode = jsonObject.getInt("cod");
                if (responseCode == 200) {
                    WeatherDataModel weatherDataModel = new WeatherDataModel();
                    weatherDataModel.setCityName(jsonObject.getString("name"));
                    JSONObject main = jsonObject.getJSONObject("main");
                    weatherDataModel.setCityTemperature(kelvinToCelsius(main.getDouble("temp")));
                    weatherDataModel.setCityMinTemperature(kelvinToCelsius(main.getDouble("temp_min")));
                    weatherDataModel.setCityMaxTemperature(kelvinToCelsius(main.getDouble("temp_max")));
                    weatherDataModel.setHumidity(main.getString("humidity"));
                    JSONArray weather = jsonObject.getJSONArray("weather");
                    if (weather.length() > 0) {
                        JSONObject type = (JSONObject) weather.get(0);
                        weatherDataModel.setWeatherType(type.getString("main"));
                        weatherDataModel.setWeatherTypeDesc(type.getString("description"));
                    }
                    JSONObject clouds = jsonObject.getJSONObject("clouds");
                    weatherDataModel.setCloudy(clouds.getString("all"));
                    weatherReportView.showWeatherData(weatherDataModel);
                    Log.e("response:", jsonObject.toString());
                } else {
                    weatherReportView.showErrorMessage(jsonObject.getString("message"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
      This method is used to convert kelvin to celsius
       */
    private String kelvinToCelsius(double kelvinValue) {
        DecimalFormat df = new DecimalFormat(".#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(kelvinValue - 273.0);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String cityName = strings[0];
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(API_URL + "?q=" + cityName + "&APPID=" + API_KEY);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader;
            int status = urlConnection.getResponseCode();
            if (status >= 400) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            }
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            Log.e("Response:", stringBuilder.toString());
            return new JSONObject(stringBuilder.toString());

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }
}
