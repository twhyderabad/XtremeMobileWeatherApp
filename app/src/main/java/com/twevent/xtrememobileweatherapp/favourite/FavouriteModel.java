package com.twevent.xtrememobileweatherapp.favourite;

public class FavouriteModel {
	private String cityName;
	private double latitude;
	private double longitude;

	public String getCityName() {
		return cityName;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "FavouriteModel{" +
			"cityName='" + cityName + '\'' +
			", latitude=" + latitude +
			", longitude=" + longitude +
			'}';
	}
}
