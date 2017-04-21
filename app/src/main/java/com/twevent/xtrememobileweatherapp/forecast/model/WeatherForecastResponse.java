package com.twevent.xtrememobileweatherapp.forecast.model;

import java.util.List;

public class WeatherForecastResponse {

    private City city;
    private List<WeatherForecast> list;

    public String getCityName() {
        return city.name;
    }
    public List<WeatherForecast> getList() {
        return list;
    }

    public class City {
        private long id;
        private String name;
        private String country;

        public String getCountry() {
            return country;
        }
    }

}
