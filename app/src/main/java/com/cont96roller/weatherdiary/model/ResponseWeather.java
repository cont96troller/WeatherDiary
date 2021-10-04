package com.cont96roller.weatherdiary.model;

import java.util.ArrayList;

public class ResponseWeather {
    private Main main;
    private ArrayList<WeatherInfoModel> weather;

    public Main getMain() {
        return main;
    }

    public ArrayList<WeatherInfoModel> getWeather() {
        return weather;
    }
}
