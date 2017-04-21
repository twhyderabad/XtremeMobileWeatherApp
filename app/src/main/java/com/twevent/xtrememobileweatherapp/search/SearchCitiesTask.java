package com.twevent.xtrememobileweatherapp.search;

import android.os.AsyncTask;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SearchCitiesTask extends AsyncTask<String, Void, SearchResult> {

    private final SearchResultsListener listener;

    public SearchCitiesTask(SearchResultsListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected SearchResult doInBackground(String... args) {
        OkHttpClient client = new OkHttpClient();
        String url = formSearchUrl(args[0]);
        Request request = new Request.Builder().url(url).build();
        Call newCall = client.newCall(request);
        try {
            Response response = newCall.execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                return gson.fromJson(response.body().string(), SearchResult.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SearchResult o) {
        super.onPostExecute(o);
        if (o != null) {
            this.listener.onSearchResultsFetched(o);
        } else {
            this.listener.onSearchResultsFetchFailed();
        }
    }

    private String formSearchUrl(String queryText) {
        return "http://api.openweathermap.org/data/2.5/find?q=" + queryText + "&APPID=ebbc66b823072502c81339f5b0b9b042&type=like";
    }

    public interface SearchResultsListener {

        void onSearchResultsFetched(SearchResult result);

        void onSearchResultsFetchFailed();
    }
}
