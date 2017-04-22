package com.twevent.xtrememobileweatherapp.favourite;

import android.os.AsyncTask;

import java.util.List;

public class GetFavouriteListTask extends AsyncTask<Void, Void, List<FavouriteModel>>{

	private final FavouritesRepository favouritesRepository;
	private final GetFavouriteListCallback getFavouriteListCallback;

	public GetFavouriteListTask(FavouritesRepository favouritesRepository, GetFavouriteListCallback getFavouriteListCallback) {
		this.favouritesRepository = favouritesRepository;
		this.getFavouriteListCallback = getFavouriteListCallback;
	}

	@Override
	protected List<FavouriteModel> doInBackground(Void... voids) {
		return favouritesRepository.getFavourites();
	}

	@Override
	protected void onPostExecute(List<FavouriteModel> favouriteModels) {
		super.onPostExecute(favouriteModels);
		getFavouriteListCallback.showFavouriteList(favouriteModels);
	}
}
