package com.twevent.xtrememobileweatherapp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.twevent.xtrememobileweatherapp.model.Weather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainActivity extends AppCompatActivity {

    private Weather currentWeather = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadWeatherFromFile();
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
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

    private void renderWeather() {
        // RENDER WEATHER
    }
}
