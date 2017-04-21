package com.twevent.xtrememobileweatherapp.forecast;

public interface WeatherForecastResponseListener {
    void weatherForecastReceived(String data);
    void weatherForecastFailed();
}
