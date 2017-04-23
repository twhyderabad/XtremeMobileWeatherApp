package com.twevent.xtrememobileweatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.twevent.xtrememobileweatherapp.favourite.FavouriteSavedCallback;
import com.twevent.xtrememobileweatherapp.favourite.FavouritesListActivity;
import com.twevent.xtrememobileweatherapp.favourite.FavouritesRepository;
import com.twevent.xtrememobileweatherapp.favourite.SaveFavouriteTask;
import com.twevent.xtrememobileweatherapp.forecast.WeatherForecastActivity;
import com.twevent.xtrememobileweatherapp.forecast.WeatherForecastResponseListener;
import com.twevent.xtrememobileweatherapp.forecast.tasks.AsyncWeatherForecastTask;
import com.twevent.xtrememobileweatherapp.location.LocationTracker;
import com.twevent.xtrememobileweatherapp.search.SearchActivity;
import com.twevent.xtrememobileweatherapp.weather.AsyncCurrentWeatherTask;
import com.twevent.xtrememobileweatherapp.weather.CurrentWeatherResponseListener;
import com.twevent.xtrememobileweatherapp.weather.Weather;
import com.twevent.xtrememobileweatherapp.weather.WeatherPresenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements WeatherForecastResponseListener, CurrentWeatherResponseListener, FavouriteSavedCallback {

    private static final int SEARCH_CODE = 9999;
    private static final int FAVOURITES_CODE = 9998;
    private Weather currentWeather = null;
    private static final Map<String, Integer> weatherStatusImageMap;
    private LocationTracker locationTracker;
    private static final double defaultLat = 17.3850f;
    private static final double defaultLong = 78.4867f;
    private static double lat = defaultLat;
    private static double lon = defaultLong;

    static {
        weatherStatusImageMap = new HashMap<>();
        weatherStatusImageMap.put("light rain", R.drawable.rainy_main);
        weatherStatusImageMap.put("moderate rain", R.drawable.sun);
        weatherStatusImageMap.put("clear sky", R.drawable.sunny_main);
        weatherStatusImageMap.put("sky is clear", R.drawable.sunny_main);
        weatherStatusImageMap.put("sky is clear", R.drawable.sunny_main);
        weatherStatusImageMap.put("broken clouds", R.drawable.cloud);
        weatherStatusImageMap.put("haze", R.drawable.cloud);
    }

	private SharedPreferences sharedPreferences;
    private ImageView favouriteListButton;
    private TextView saveAsFavouriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        loadWeatherFromFile();

		ImageView searchImageView = (ImageView) findViewById(R.id.searchImageView);
		searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearchActivity();
            }
        });

		sharedPreferences = getSharedPreferences("com.twevent.xtrememobileweatherapp.weatherApp", 0);

        favouriteListButton = (ImageView) findViewById(R.id.view_favourite_list);
        showFavouriteListButton();
        favouriteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavouritesListActivity.class);
                startActivityForResult(intent, FAVOURITES_CODE);
            }
        });

        saveAsFavouriteButton = (TextView) findViewById(R.id.add_as_favourite);
        saveAsFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFavourite();
                showFavouriteListButton();
            }
        });
        locationTracker = new LocationTracker(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(lat == defaultLat && lon == defaultLong) {
            locationTracker.refreshLocation();
        }else {
            showWeatherOfCurrentCity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            locationTracker.refreshLocation();
    }

    private void showFavouriteListButton() {
        int favListButtonVisibility = sharedPreferences.getBoolean("favouriteSaved", false) ? VISIBLE : INVISIBLE;
        favouriteListButton.setVisibility(favListButtonVisibility);
    }

    private void loadWeatherFromFile() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("cityData.json");
            Gson gson = new Gson();
            Reader inputReader = new InputStreamReader(inputStream);
            currentWeather = gson.fromJson(inputReader, Weather.class);
            renderWeatherList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showForecastDetails(View view) {
        fetchForecastData();
    }

    @Override
    public void weatherForecastReceived(String data) {
        Intent intent = new Intent(this, WeatherForecastActivity.class);
        intent.putExtra("weatherData", data);
        intent.putExtra("city", currentWeather.getName());
        startActivity(intent);
    }

    @Override
    public void weatherForecastFailed() {
        Toast.makeText(this, "An error occurred. Please try again", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FAVOURITES_CODE) {
            showSaveAsFavouriteButton(GONE);
        } else {
            showSaveAsFavouriteButton(VISIBLE);
        }
        if ((requestCode == SEARCH_CODE || requestCode == FAVOURITES_CODE) && resultCode == RESULT_OK) {
            lat = data.getDoubleExtra("latitude", lat);
            lon = data.getDoubleExtra("longitude", lon);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCurrentWeatherFetched(Weather weather) {
        currentWeather = weather;
        renderWeatherList();
    }

    @Override
    public void onCurrentWeatherFetchFailure() {
        Toast.makeText(MainActivity.this, "An error occurred. Please try again", Toast.LENGTH_LONG).show();
    }

    private void saveFavourite() {
        sharedPreferences.edit().putBoolean("favouriteSaved", true).apply();
        saveTheCityAsFavourite();
    }

    private void saveTheCityAsFavourite() {
        FavouritesRepository favouritesRepository = new FavouritesRepository(this);
        new SaveFavouriteTask(favouritesRepository, this, currentWeather).execute();
    }

    private void fetchWeatherForLocation(double latitude, double longitude) {
        AsyncCurrentWeatherTask task = new AsyncCurrentWeatherTask(this);
        task.execute(latitude, longitude);
    }

    private void launchSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, SEARCH_CODE);
    }

    private void fetchForecastData(){
        String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + currentWeather.getName() + "&units=metric" + "&" + "APPID=" + "ebbc66b823072502c81339f5b0b9b042";
        AsyncWeatherForecastTask task = new AsyncWeatherForecastTask(this);
        task.execute(weatherUrl);
    }

    private void showWeatherOfCurrentCity() {
        fetchWeatherForLocation(lat, lon);
    }

    public void renderWeatherList(Weather weather) {
        this.currentWeather = weather;
        renderWeatherList();
    }

    private void renderWeatherList() {
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
        int imageResource = R.drawable.sunny_main;
        if (weatherStatusImageMap.containsKey(currentWeather.getWeather().get(0).getDescription())) {
            imageResource = weatherStatusImageMap.get(currentWeather.getWeather().get(0).getDescription());
        }
        weatherImage.setImageResource(imageResource);    }

    @Override
    public void displayToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSaveAsFavouriteButton(int visibility) {
        saveAsFavouriteButton.setVisibility(visibility);
    }
}
