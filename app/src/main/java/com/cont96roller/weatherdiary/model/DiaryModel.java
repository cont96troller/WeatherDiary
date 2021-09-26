package com.cont96roller.weatherdiary.model;

import java.io.Serializable;

public class DiaryModel implements Serializable {

    private String weatherStatus;
    private String title;
    private String date;

    public DiaryModel(String weatherStatus, String title, String date) {
        this.weatherStatus = weatherStatus;
        this.title = title;
        this.date = date;

    }

    public DiaryModel(String weatherStatus, String title) {
        this.weatherStatus = weatherStatus;
        this.title = title;
    }

    public DiaryModel(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;


    }
}
