package com.twevent.xtrememobileweatherapp.weather;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherPresenter {
    private List<String> daysInAWeek;
    private Weather weather;

    public WeatherPresenter(Weather weather) {
        super();
        this.weather = weather;
        daysInAWeek = Arrays.asList("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
    }

    public String getTemperatureInCelsius() {
        return String.valueOf(weather.getMain().getTemp()) + "\u00b0" + "C";
    }

    public String getDayOfTheWeek() {

        long unixSeconds = Long.parseLong(weather.getDatetime());
        Date date = new Date(unixSeconds * 1000L);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return c.get(Calendar.DAY_OF_MONTH) + "-" +
        c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR) + ", " +
        daysInAWeek.get(dayOfWeek - 1);
    }

}
