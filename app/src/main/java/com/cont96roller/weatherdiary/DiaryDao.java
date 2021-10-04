package com.cont96roller.weatherdiary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

    @Dao
public interface DiaryDao {
    @Query("SELECT * FROM diary")
    List<Diary> getAll();

    @Insert
    void insertAll(Diary... diaries);

    @Delete
    void delete(Diary diary);
}
