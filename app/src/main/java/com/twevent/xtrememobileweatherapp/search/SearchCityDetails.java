package com.twevent.xtrememobileweatherapp.search;

public class SearchCityDetails {
    private Integer id;
    private String name;
    private Integer dt;
    private Sys sys;
    private SearchResult.LatLng coord;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryName() {
        return sys.getCountry();
    }

    public SearchResult.LatLng getCoord() {
        return coord;
    }

    private class Sys {
        private String country;

        String getCountry() {
            return country;
        }
    }
}
