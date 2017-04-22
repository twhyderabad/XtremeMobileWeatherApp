package com.twevent.xtrememobileweatherapp.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.twevent.xtrememobileweatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavouritesListActivity extends AppCompatActivity implements GetFavouriteListCallback{

	private FavouritesListAdapter favouritesListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites_list);
		initialiseViews();
		initialiseToolBar();
	}

	private void initialiseViews() {
		GetFavouriteListTask getFavouriteListTask = new GetFavouriteListTask(new FavouritesRepository(this), this);
		getFavouriteListTask.execute();
		// show spinner
		ListView favouritesListView = (ListView) findViewById(R.id.favouritesListView);

		favouritesListAdapter = new FavouritesListAdapter(this, new ArrayList<FavouriteModel>());
		favouritesListView.setAdapter(favouritesListAdapter);

		favouritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				FavouriteModel favouriteModel = (FavouriteModel) adapterView.getItemAtPosition(position);
				loadForecastForFavourite(favouriteModel);
			}
		});
	}


	private void initialiseToolBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
		}
		setTitle("My Favourites");
	}

	private void loadForecastForFavourite(FavouriteModel favouriteModel) {
		Intent intent = new Intent();
		intent.putExtra("latitude", favouriteModel.getLatitude());
		intent.putExtra("longitude", favouriteModel.getLongitude());
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void showFavouriteList(List<FavouriteModel> favouriteModels) {
		// stop spinner
		favouritesListAdapter.updateFavouriteList(favouriteModels);
	}
}
