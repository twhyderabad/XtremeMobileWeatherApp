package com.twevent.xtrememobileweatherapp.forecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.twevent.xtrememobileweatherapp.R;

public class WeatherForecastActivity extends AppCompatActivity {

    private String weatherData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        weatherData = getIntent().getStringExtra("weatherData");
        String city = getIntent().getStringExtra("city");
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        toolbar.setTitle(city);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        showDetailWeatherPage();
    }

    private void showDetailWeatherPage() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putString("details",weatherData);
        fragment.setArguments(bundle);
        transaction.add(R.id.container, fragment, "details");
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
