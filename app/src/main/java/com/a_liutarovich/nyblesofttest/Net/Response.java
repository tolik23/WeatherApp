package com.a_liutarovich.nyblesofttest.Net;

import android.content.Context;
import android.widget.Toast;

import com.a_liutarovich.nyblesofttest.Models.WeatherDay;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Response {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public WeatherDay callWeather(Context context, Double lat, Double lon, String units, String appid) {
        WeatherDay mWeatherDay = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherAPI service = retrofit.create(WeatherAPI.class);
        try {
            Call<WeatherDay> call = service.getToday(lat, lon, units, appid);
            retrofit2.Response<WeatherDay> response = null;
            response = call.execute();
            mWeatherDay = response.body();
            return mWeatherDay;
        } catch (IOException e) {
            Toast.makeText(context,"Ошибка сервера.",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return mWeatherDay;
        }
    }
}
