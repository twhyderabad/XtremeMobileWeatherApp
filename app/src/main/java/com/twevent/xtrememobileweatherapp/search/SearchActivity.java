package com.twevent.xtrememobileweatherapp.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.twevent.xtrememobileweatherapp.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchCitiesTask.SearchResultsListener {

    private EditText searchCityEditText;
    private SearchResultsListAdapter searchResultsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initialiseViews();
    }

    private void initialiseViews() {
        searchCityEditText = (EditText) findViewById(R.id.searchCityEditText);
        ImageView searchImageView = (ImageView) findViewById(R.id.searchImageView);
        ListView searchResultsListView = (ListView) findViewById(R.id.searchResultsListView);

        searchResultsListAdapter = new SearchResultsListAdapter(this, new ArrayList<SearchCityDetails>());
        searchResultsListView.setAdapter(searchResultsListAdapter);

        searchCityEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SearchCityDetails searchCityDetails = (SearchCityDetails) adapterView.getItemAtPosition(position);
                loadForecastForSearch(searchCityDetails.getCoord());
            }
        });
    }

    private void performSearch() {
        String queryText = searchCityEditText.getText().toString();
        if (queryText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter search text", Toast.LENGTH_LONG).show();
            return;
        }
        searchCitiesWithText(queryText);
    }

    private void loadForecastForSearch(SearchResult.LatLng latLng) {
        Intent intent = new Intent();
        intent.putExtra("latitude", latLng.getLat());
        intent.putExtra("longitude", latLng.getLon());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void searchCitiesWithText(String queryText) {
        SearchCitiesTask searchCitiesTask = new SearchCitiesTask(this);
        searchCitiesTask.execute(queryText);
    }


    @Override
    public void onSearchResultsFetched(SearchResult searchResult) {
        searchResultsListAdapter.updateSearchResults(searchResult);
    }

    @Override
    public void onSearchResultsFetchFailed() {
        Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_LONG).show();
    }
}
