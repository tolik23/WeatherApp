package com.a_liutarovich.nyblesofttest.Net;

import com.a_liutarovich.nyblesofttest.Models.Result;
import com.a_liutarovich.nyblesofttest.Models.WeatherDay;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    public static String KEY = "6532bbf7718f07a43cb1435a7638c4f5";

        @GET("weather")
        Call<WeatherDay> getToday(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("units") String units,
                @Query("appid") String appid
        );
}
