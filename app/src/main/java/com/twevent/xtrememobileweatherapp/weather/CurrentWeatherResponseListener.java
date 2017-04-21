package com.twevent.xtrememobileweatherapp.weather;

public interface CurrentWeatherResponseListener {

    void onCurrentWeatherFetched(Weather weather);

    void onCurrentWeatherFetchFailure();
}
