package com.cont96roller.weatherdiary.model;

import java.util.ArrayList;
//오픈왜더맵 리스판스 제이슨 파싱을 위해 만든 모델클래스
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
