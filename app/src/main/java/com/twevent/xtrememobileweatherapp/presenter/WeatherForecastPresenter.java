package com.twevent.xtrememobileweatherapp.presenter;


import com.twevent.xtrememobileweatherapp.model.WeatherForecast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WeatherForecastPresenter {
    private WeatherForecast weather;
    private List<String> daysInAWeek;

    public WeatherForecastPresenter(WeatherForecast weather) {
        super();
        this.weather = weather;
        daysInAWeek = Arrays.asList("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
    }

    public String getTemperatureMinInCelsius() {
        return String.valueOf(weather.getTemperature().getMinimum()) + "\u00b0" + "C";
    }

    public String getTemperatureMaxInCelsius() {
        return String.valueOf(weather.getTemperature().getMaximum()) + "\u00b0" + "C";
    }

    public String getDayOfTheWeek() {

        long unixSeconds = weather.getTimestamp();
        Date date = new Date(unixSeconds*1000L);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return daysInAWeek.get(dayOfWeek - 1);
    }
}
