package com.twevent.xtrememobileweatherapp.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.twevent.xtrememobileweatherapp.R;

import java.util.List;

class SearchResultsListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<SearchCityDetails> searchCityDetailsList;

    SearchResultsListAdapter(Context context, List<SearchCityDetails> searchCityDetailsList) {
        inflater = LayoutInflater.from(context);
        this.searchCityDetailsList = searchCityDetailsList;
    }

    void updateSearchResults(SearchResult searchResult) {
        this.searchCityDetailsList = searchResult.getList();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchCityDetailsList.size();
    }

    @Override
    public SearchCityDetails getItem(int position) {
        return searchCityDetailsList.get(position);
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
        SearchResultViewHolder searchResultViewHolder = (SearchResultViewHolder) view.getTag();
        SearchCityDetails searchCityDetails = searchCityDetailsList.get(position);
        searchResultViewHolder.cityName.setText(searchCityDetails.getName() + ", " + searchCityDetails.getCountryName());
    }

    private View createView() {
        View view = inflater.inflate(R.layout.list_search_details_item, null);
        SearchResultViewHolder searchResultViewHolder = new SearchResultViewHolder();
        searchResultViewHolder.cityName = (TextView) view.findViewById(R.id.cityNameTextView);
        view.setTag(searchResultViewHolder);
        return view;
    }

    private class SearchResultViewHolder {
        TextView cityName;
    }
}
