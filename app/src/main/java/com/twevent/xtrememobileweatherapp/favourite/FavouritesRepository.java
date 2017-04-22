package com.twevent.xtrememobileweatherapp.favourite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.COLUMN_NAME_CITY;
import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.COLUMN_NAME_LATITUDE;
import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.COLUMN_NAME_LONGITUDE;
import static com.twevent.xtrememobileweatherapp.favourite.DatabaseTableColumnNames.TABLE_NAME_FAVOURITE;
import static java.lang.String.valueOf;

public class FavouritesRepository {

	private static final String TAG = FavouritesRepository.class.getSimpleName();

	private final WeatherAppDatabaseHandler weatherAppDatabaseHandler;

	public FavouritesRepository(Context context) {
		weatherAppDatabaseHandler = WeatherAppDatabaseHandler.getInstance(context);
	}

	boolean saveFavourite(String name, double latitude, double longitude) {
		if(favouriteAlreadyExists(latitude, longitude)) {
			return true;
		}
		try (SQLiteDatabase sqLiteDatabase = weatherAppDatabaseHandler.getWritableDatabase()) {
			ContentValues contentValue = new ContentValues();
			contentValue.put(COLUMN_NAME_CITY, name);
			contentValue.put(COLUMN_NAME_LATITUDE, latitude);
			contentValue.put(COLUMN_NAME_LONGITUDE, longitude);

			long insert = sqLiteDatabase.insert(TABLE_NAME_FAVOURITE, null, contentValue);
			return insert > 0;
		} catch (SQLiteException exception) {
			Log.e(TAG, exception.getLocalizedMessage());
		}
		return  false;
	}

	List<FavouriteModel> getFavourites() {
		List<FavouriteModel> favouritesList = new ArrayList<>();
		try (SQLiteDatabase sqLiteDatabase = weatherAppDatabaseHandler.getReadableDatabase()) {
			try (Cursor cursor = sqLiteDatabase.query(TABLE_NAME_FAVOURITE, null, null, null, null, null, null)) {
				if (cursor == null) {
					return favouritesList;
				}
				while (cursor.moveToNext()) {
					String cityName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CITY));
					double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_LATITUDE));
					double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_LONGITUDE));

					FavouriteModel favouriteModel = new FavouriteModel();
					favouriteModel.setCityName(cityName);
					favouriteModel.setLatitude(latitude);
					favouriteModel.setLongitude(longitude);
					favouritesList.add(favouriteModel);
				}
				cursor.close();
			}
		} catch (SQLiteException exception) {
			Log.e(TAG, exception.getLocalizedMessage());
		}
		return favouritesList;
	}

	private boolean favouriteAlreadyExists(double latitude, double longitude) {
		try (SQLiteDatabase sqLiteDatabase = weatherAppDatabaseHandler.getReadableDatabase()) {
			String whereClause = COLUMN_NAME_LATITUDE + " = ? AND " + COLUMN_NAME_LONGITUDE + " = ?";
			String[] whereArgs = new String[] {
				valueOf(latitude), valueOf(longitude)
			};
			try (Cursor query = sqLiteDatabase.query(TABLE_NAME_FAVOURITE, new String[]{COLUMN_NAME_CITY}, whereClause, whereArgs, null, null, null)) {
				return query.getCount() > 1;
			}
		} catch (SQLiteException exception) {
			Log.e(TAG, exception.getLocalizedMessage());
		}
		return  false;
	}
}
