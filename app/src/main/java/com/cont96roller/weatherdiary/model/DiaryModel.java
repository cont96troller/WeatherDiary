package com.cont96roller.weatherdiary.model;

import java.io.Serializable;

public class DiaryModel implements Serializable {

    private String weatherStatus;
    private String title;
    private long date;

    public DiaryModel(String weatherStatus, String title, long date) {
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;


    }
}
