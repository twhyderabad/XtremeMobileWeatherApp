package com.twevent.xtrememobileweatherapp.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import com.twevent.xtrememobileweatherapp.MainActivity;
import com.twevent.xtrememobileweatherapp.weather.AsyncCurrentWeatherTask;

public class LocationTracker implements LocationListener {

    private MainActivity activity;
    private LocationManager locationManager;

    public LocationTracker(MainActivity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    public void refreshLocation() {
        boolean enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (enabled) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
                return;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(lastKnownLocation != null) {
                fetchWeatherForLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            }
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    private void fetchWeatherForLocation(double latitude, double longitude) {
        AsyncCurrentWeatherTask task = new AsyncCurrentWeatherTask(activity);
        task.execute(latitude, longitude);
    }
}
