package com.a_liutarovich.nyblesofttest.DB;

import android.content.Context;

public class ElementDBSet {

    private QueriesDB mQueriesDB = new QueriesDB();

    public void setWeatherElement (Context context, String adress, Long date, String description, Double latitude, Double longitude, String temp, String pressure, String humidity) {
        WeatherDB weatherDB = new WeatherDB();

        weatherDB.setAdress(adress);
        weatherDB.setDate(date);
        weatherDB.setDescription(description);
        weatherDB.setLatitude(latitude);
        weatherDB.setLongitude(longitude);
        weatherDB.setTemp(temp);
        weatherDB.setPressure(pressure);
        weatherDB.setHumidity(humidity);
        mQueriesDB.saveWeatherDB(weatherDB, context);
    }
}
