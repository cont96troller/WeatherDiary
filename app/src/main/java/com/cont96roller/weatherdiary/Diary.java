package com.cont96roller.weatherdiary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diary {
    @PrimaryKey(autoGenerate = true)
    public int id;

//    @ColumnInfo(name="status")
//    public String status;
//
//    @ColumnInfo(name="temp_max")
//    public int temp_max;
//
//    @ColumnInfo(name="temp_min")
//    public int temp_min;
//
//    @ColumnInfo(name="icon")
//    public String icon;

    @ColumnInfo(name="contents")
    public String contents;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
