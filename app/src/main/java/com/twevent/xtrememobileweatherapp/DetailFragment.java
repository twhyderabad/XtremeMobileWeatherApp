package com.twevent.xtrememobileweatherapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.gson.Gson;
import com.twevent.xtrememobileweatherapp.adapter.WeatherForecastAdapter;
import com.twevent.xtrememobileweatherapp.model.WeatherForecastResponse;


public class DetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        String details = getArguments().getString("details");
        Gson json = new Gson();
        WeatherForecastResponse weatherList = json.fromJson(details,WeatherForecastResponse.class);

        WeatherForecastAdapter weatherForecastAdapter = new WeatherForecastAdapter(getActivity(), weatherList.getList());
        ListView listView = (ListView) view.findViewById(R.id.weather_forecast);
        listView.setAdapter(weatherForecastAdapter);

        return view;
    }
}
