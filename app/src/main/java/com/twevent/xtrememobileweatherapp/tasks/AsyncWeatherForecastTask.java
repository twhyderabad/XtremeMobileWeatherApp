package com.twevent.xtrememobileweatherapp.tasks;

import android.os.AsyncTask;
import com.twevent.xtrememobileweatherapp.WeatherResponseListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class AsyncWeatherForecastTask extends AsyncTask<String, Void, String> {

	private static final String FAILED_EVENT = "FailedEvent";
	private WeatherResponseListener listener;

	public AsyncWeatherForecastTask(WeatherResponseListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(FAILED_EVENT.equals(result)) {
			listener.weatherForecastFailed();
		} else {
			listener.weatherForecastReceived(result);
		}
	}

	@Override
	protected String doInBackground(String... urls) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(urls[0]).build();
		Call newCall = client.newCall(request);
		try {
			Response response = newCall.execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILED_EVENT;
	}
}
