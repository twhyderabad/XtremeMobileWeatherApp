package com.twevent.xtrememobileweatherapp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.twevent.xtrememobileweatherapp.model.Weather;
import com.twevent.xtrememobileweatherapp.presenter.WeatherPresenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Weather currentWeather = null;
    private Map<String, Integer> weatherStatusImageMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        setWeatherStatusImageMap();
        loadWeatherFromFile();
    }

    private void loadWeatherFromFile() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("cityData.json");
            Gson gson = new Gson();
            Reader inputReader = new InputStreamReader(inputStream);
            currentWeather = gson.fromJson(inputReader, Weather.class);
            renderWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setWeatherStatusImageMap() {
        weatherStatusImageMap.put("light rain",R.drawable.rainy_main);
        weatherStatusImageMap.put("moderate rain",R.drawable.sun);
        weatherStatusImageMap.put("clear sky",R.drawable.sunny_main);
    }

    private void renderWeather() {
        WeatherPresenter presenter = new WeatherPresenter(currentWeather);
        TextView cityName = (TextView) findViewById(R.id.cityName);
        cityName.setText(currentWeather.getName());

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(presenter.getDayOfTheWeek());

        TextView temperature = (TextView) findViewById(R.id.temperature);
        temperature.setText(presenter.getTemperatureInCelsius());

        TextView weatherDescription = (TextView) findViewById(R.id.weatherDescription);
        weatherDescription.setText(currentWeather.getWeather().get(0).getDescription());

        ImageView weatherImage = (ImageView) findViewById(R.id.weatherImage);
        weatherImage.setImageResource(weatherStatusImageMap.get(currentWeather.getWeather().get(0).getDescription()));
    }
}
