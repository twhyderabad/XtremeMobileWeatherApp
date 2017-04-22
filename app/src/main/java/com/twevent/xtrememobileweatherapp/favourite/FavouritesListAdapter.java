package com.twevent.xtrememobileweatherapp.favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.twevent.xtrememobileweatherapp.R;

import java.util.List;

public class FavouritesListAdapter extends BaseAdapter {

	private final LayoutInflater inflater;
	private List<FavouriteModel> favouritesList;

	FavouritesListAdapter(Context context, List<FavouriteModel> favouritesList) {
		inflater = LayoutInflater.from(context);
		this.favouritesList = favouritesList;
	}

	public void updateFavouriteList(List<FavouriteModel> favouritesList) {
		this.favouritesList = favouritesList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return favouritesList.size();
	}

	@Override
	public FavouriteModel getItem(int position) {
		return favouritesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = createView();
		}
		updateView(convertView, position);
		return convertView;
	}


	private void updateView(View view, int position) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		FavouriteModel favourite = favouritesList.get(position);
		viewHolder.cityName.setText(favourite.getCityName());
	}

	private View createView() {
		View view = inflater.inflate(R.layout.list_search_details_item, null);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.cityName = (TextView) view.findViewById(R.id.cityNameTextView);
		view.setTag(viewHolder);
		return view;
	}

	private class ViewHolder {
		TextView cityName;
	}
}
