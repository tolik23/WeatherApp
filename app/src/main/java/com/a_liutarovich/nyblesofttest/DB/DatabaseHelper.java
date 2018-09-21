package com.a_liutarovich.nyblesofttest.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "weather.db";

    private static final int DATABASE_VERSION = 1;

    private Dao<WeatherDB, Integer> recirculatorWeatherIntegerDao = null;

    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    private static DatabaseHelper sDatabaseHelper;

    private DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getSingletonInstance(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(context);
        }
        usageCounter.incrementAndGet();
        return sDatabaseHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(),"onCreate");

            TableUtils.createTable(connectionSource, WeatherDB.class);
        }catch (SQLException e){
            Log.i(DatabaseHelper.class.getName(),"Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(),"onUpgrade");

        }catch (Exception e){
            Log.e(DatabaseHelper.class.getName(),"Can't drop databases",e);
            throw new RuntimeException(e);
        }

    }

    public Dao<WeatherDB, Integer> getWeatherDB() throws SQLException {
        if (recirculatorWeatherIntegerDao == null){
            recirculatorWeatherIntegerDao = getDao(WeatherDB.class);
        }
        return recirculatorWeatherIntegerDao;
    }

    @Override
    public void close() {
        if (usageCounter.decrementAndGet()==0){
            super.close();
            recirculatorWeatherIntegerDao = null;
            sDatabaseHelper = null;
        }

    }
}

