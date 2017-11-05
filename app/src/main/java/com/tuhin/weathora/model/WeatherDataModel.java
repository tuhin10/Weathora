package com.tuhin.weathora.model;

public class WeatherDataModel {
    private String cityName;
    private float todayTemperature;
    private String todayDescription;
    private double todayWind;
    private float todayPressure;
    private String todayHumidity;
    private String todaySunrise;
    private String todaySunset;
    private String weatherIcon;

    public WeatherDataModel(){
        
    }
    
    public WeatherDataModel(String cityName, float todayTemperature, 
                            String todayDescription, double todayWind, 
                            float todayPressure, String todayHumidity, 
                            String todaySunrise, String todaySunset, String weatherIcon) {
        this.cityName = cityName;
        this.todayTemperature = todayTemperature;
        this.todayDescription = todayDescription;
        this.todayWind = todayWind;
        this.todayPressure = todayPressure;
        this.todayHumidity = todayHumidity;
        this.todaySunrise = todaySunrise;
        this.todaySunset = todaySunset;
        this.weatherIcon = weatherIcon;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getTodayTemperature() {
        return todayTemperature;
    }

    public void setTodayTemperature(float todayTemperature) {
        this.todayTemperature = todayTemperature;
    }

    public String getTodayDescription() {
        return todayDescription;
    }

    public void setTodayDescription(String todayDescription) {
        this.todayDescription = todayDescription;
    }

    public double getTodayWind() {
        return todayWind;
    }

    public void setTodayWind(double todayWind) {
        this.todayWind = todayWind;
    }

    public float getTodayPressure() {
        return todayPressure;
    }

    public void setTodayPressure(float todayPressure) {
        this.todayPressure = todayPressure;
    }

    public String getTodayHumidity() {
        return todayHumidity;
    }

    public void setTodayHumidity(String todayHumidity) {
        this.todayHumidity = todayHumidity;
    }

    public String getTodaySunrise() {
        return todaySunrise;
    }

    public void setTodaySunrise(String todaySunrise) {
        this.todaySunrise = todaySunrise;
    }

    public String getTodaySunset() {
        return todaySunset;
    }

    public void setTodaySunset(String todaySunset) {
        this.todaySunset = todaySunset;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
