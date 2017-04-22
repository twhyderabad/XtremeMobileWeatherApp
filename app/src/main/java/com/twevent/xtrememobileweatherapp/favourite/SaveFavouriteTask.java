package com.twevent.xtrememobileweatherapp.favourite;

import android.os.AsyncTask;
import com.twevent.xtrememobileweatherapp.weather.Weather;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SaveFavouriteTask extends AsyncTask<String, Void, Boolean> {
	private final FavouriteSavedCallback favouriteSavedCallback;
	private final Weather currentWeather;
	private final FavouritesRepository favouritesRepository;

	public SaveFavouriteTask(FavouritesRepository favouritesRepository,
	                         FavouriteSavedCallback favouriteSavedCallback,
	                         Weather currentWeather) {
		this.favouritesRepository = favouritesRepository;
		this.favouriteSavedCallback = favouriteSavedCallback;
		this.currentWeather = currentWeather;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String cityName = currentWeather.getName();
		double latitude = currentWeather.getCoord().getLat();
		double longitude = currentWeather.getCoord().getLon();
		return favouritesRepository.saveFavourite(cityName, latitude, longitude);
	}

	@Override
	protected void onPostExecute(Boolean favouriteSaved) {
		super.onPostExecute(favouriteSaved);
		if (favouriteSaved) {
			favouriteSavedCallback.displayToast("Favourite saved successfully");
			favouriteSavedCallback.showSaveAsFavouriteButton(GONE);
		} else {
			favouriteSavedCallback.displayToast("An error occurred while saving city as favourite");
			favouriteSavedCallback.showSaveAsFavouriteButton(VISIBLE);
		}

	}
}
