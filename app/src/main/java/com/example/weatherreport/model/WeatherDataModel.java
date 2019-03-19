package com.example.weatherreport.model;


public class WeatherDataModel {
    private String cityName = "Tirupati";
    private String cityTemperature = "0";
    private String cityMinTemperature = "0";
    private String cityMaxTemperature = "0";
    private String weatherType = "No data available";
    private String weatherTypeDesc = "No data available";
    private String humidity = "0";
    private String cloudy = "0";

    public  String getCityName() {
        return cityName;
    }

    public  String getCityTemperature() {
        return cityTemperature;
    }

    public  String getCityMinTemperature() {
        return cityMinTemperature;
    }

    public  String getCityMaxTemperature() {
        return cityMaxTemperature;
    }

    public  String getWeatherType() {
        return weatherType;
    }

    public String getWeatherTypeDesc() {
        return weatherTypeDesc;
    }

    public  String getHumidity() {
        return humidity;
    }

    public  String getCloudy() {
        return cloudy;
    }

     public void setCityName(String cityName) {
        this.cityName = cityName;
    }

     public void setCityTemperature(String cityTemperature) {
        this.cityTemperature = cityTemperature;
    }

    public void setCityMinTemperature(String cityMinTemperature) {
        this.cityMinTemperature = cityMinTemperature;
    }

    public  void setCityMaxTemperature(String cityMaxTemperature) {
        this.cityMaxTemperature = cityMaxTemperature;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public void setWeatherTypeDesc(String weatherTypeDesc) {
        this.weatherTypeDesc = weatherTypeDesc;
    }

    public  void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public  void setCloudy(String cloudy) {
        this.cloudy = cloudy;
    }

 }
