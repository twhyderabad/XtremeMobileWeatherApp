package com.twevent.xtrememobileweatherapp.forecast.model;

import java.util.List;

public class WeatherForecast {

    private List<Weather> weather;
    private Temperature temp;
    private Float pressure;
    private Float humidity;
    private long dt;

    public Temperature getTemperature() {
        return temp;
    }
    public Float getPressure() {
        return pressure;
    }
    public Float getHumidity() {
        return humidity;
    }

    public Weather getWeather() {
        return weather.get(0);
    }

    public long getTimestamp() {
        return dt;
    }


    public class Temperature {
        private float min;
        private float max;

        public float getMinimum() {
            return min;
        }

        public float getMaximum() {
            return max;
        }
    }

    public  class Weather {
        private int id;
        private String description;
        private String main;

        public String getDescription() {
            return description;
        }

        public String getMain(){
            return main;
        }
    }


}
