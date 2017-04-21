package com.twevent.xtrememobileweatherapp.search;

import java.util.List;

public class SearchResult {
    private String message;
    private String cod;
    private Integer count;
    private List<SearchCityDetails> list;

    public List<SearchCityDetails> getList() {
        return list;
    }

    public class LatLng {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }
    }
}