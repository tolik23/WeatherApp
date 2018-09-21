package com.a_liutarovich.nyblesofttest.Models;

import java.util.List;

public class Result {
    private List <WeatherDay> weather_days;

    public List<WeatherDay> getWeather_days() {
        return weather_days;
    }

    public void setWeather_days(List<WeatherDay> weather_days) {
        this.weather_days = weather_days;
    }

    @Override
    public String toString() {
        return "Result{" +
                "weather_days=" + weather_days +
                '}';
    }
}
