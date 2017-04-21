package com.twevent.xtrememobileweatherapp.weather;

import android.os.AsyncTask;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AsyncCurrentWeatherTask extends AsyncTask<Double, Void, Weather> {

    private final CurrentWeatherResponseListener listener;

    public AsyncCurrentWeatherTask(CurrentWeatherResponseListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected Weather doInBackground(Double... urls) {
        OkHttpClient client = new OkHttpClient();
        String url = formUrl(urls[0], urls[1]);
        Request request = new Request.Builder().url(url).build();
        Call newCall = client.newCall(request);
        try {
            Response response = newCall.execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                return gson.fromJson(response.body().string(), Weather.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String formUrl(Double latitude, Double longitude) {
        return "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&APPID=ebbc66b823072502c81339f5b0b9b042";
    }


    @Override
    protected void onPostExecute(Weather o) {
        super.onPostExecute(o);
        if (o == null) {
            listener.onCurrentWeatherFetchFailure();
        } else {
            listener.onCurrentWeatherFetched(o);
        }
    }

}
