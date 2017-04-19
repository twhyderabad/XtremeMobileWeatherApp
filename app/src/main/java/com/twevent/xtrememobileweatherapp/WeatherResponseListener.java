package com.twevent.xtrememobileweatherapp;

public interface WeatherResponseListener {
    void weatherForecastReceived(String data);
    void weatherForecastFailed();
}
