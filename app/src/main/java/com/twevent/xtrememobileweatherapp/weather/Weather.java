package com.twevent.xtrememobileweatherapp.weather;

import java.util.List;

public class Weather {

    private String name;
    private int visibility;
    private List<WeatherDetails> weather;
    private Temperature main;
    private Wind wind;
    private Sys sys;

    private String dt;

    public String getDatetime() {
        return dt;
    }

    public String getName() {
        return name;
    }

    public List<WeatherDetails> getWeather() {
        return weather;
    }

    public int getVisibility() {
        return visibility;
    }

    public Temperature getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }


    public class WeatherDetails {
        private String description;
        private String icon;
        private int id;
        private String main;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }
    }

    public class Temperature {
        private float humidity;
        private float pressure;
        private float temp;
        private float temp_max;
        private float temp_min;

        public float getHumidity() {
            return humidity;
        }

        public float getPressure() {
            return pressure;
        }

        public float getTemp() {
            return temp;
        }

        public float getTemp_max() {
            return temp_max;
        }

        public float getTemp_min() {
            return temp_min;
        }
    }

    public class Wind {
        private float deg;
        private float speed;

        public float getDeg() {
            return deg;
        }

        public float getSpeed() {
            return speed;
        }
    }

    public class Sys {
        private long sunrise;
        private long sunset;

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }
    }
}
