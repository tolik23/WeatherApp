package com.a_liutarovich.nyblesofttest.DB;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class QueriesDB {

    // Получить все таблицу
    public List<WeatherDB> getWeatherDB(Context context) throws SQLException {
        return DatabaseHelper.getSingletonInstance(
                context)
                .getWeatherDB().queryBuilder().query();
    }

    // Сохранить элемент
    public int saveWeatherDB(WeatherDB weatherDB, Context context) {
        int Count = 0;
        try {
            Dao<WeatherDB, Integer> servicesDataDao = DatabaseHelper.getSingletonInstance(
                    context)
                    .getWeatherDB();

            servicesDataDao.createOrUpdate(weatherDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Count;
    }

    // Получить элемент по id
    public List<WeatherDB> getWeatherDBById(Context context, int id) throws SQLException {
        return DatabaseHelper.getSingletonInstance(
                context)
                .getWeatherDB().queryBuilder().where().eq("id", id).query();
    }
}
