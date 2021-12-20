package com.cont96roller.weatherdiary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//Room 초기화 작업
@Database(entities = {Diary.class}, version = 1)
public abstract class DiaryDB extends RoomDatabase {

    private static DiaryDB INSTANCE = null;

    public abstract DiaryDao diaryDao();

    public static DiaryDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DiaryDB.class, "diary.db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;

    }
}
