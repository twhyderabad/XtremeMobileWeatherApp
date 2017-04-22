package com.twevent.xtrememobileweatherapp.favourite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.COLUMN_NAME_CITY;
import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.COLUMN_NAME_ID;
import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.COLUMN_NAME_LATITUDE;
import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.COLUMN_NAME_LONGITUDE;
import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.TABLE_NAME_FAVOURITE;

public class WeatherAppDatabaseHandler extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "WeatherApp.db";
	private static final int DATABASE_VERSION = 1;

	private static final String SQL_CREATE_FAVOURITES_TABLE =
		"CREATE TABLE " + TABLE_NAME_FAVOURITE + " (" +
			COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
			COLUMN_NAME_CITY + " TEXT NOT NULL," +
			COLUMN_NAME_LATITUDE + " REAL NOT NULL," +
			COLUMN_NAME_LONGITUDE + " REAL NOT NULL"
			+ ")";

	private static WeatherAppDatabaseHandler instance;

	public static synchronized WeatherAppDatabaseHandler getInstance(Context context) {
		if(instance == null) {
			instance = new WeatherAppDatabaseHandler(context);
		}
		return  instance;
	}


	private WeatherAppDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}
}
