package com.cont96roller.weatherdiary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

    @Dao
public interface DiaryDao {
    @Query("SELECT * FROM diary")
    List<Diary> getAll();

    @Query("SELECT * FROM diary WHERE id=:id")
    Diary getDiary(int id);

    @Insert
    void insertAll(Diary... diaries);

    @Update
    void updateDiary(Diary diary);

    @Delete
    void delete(Diary diary);

    @Query("DELETE FROM diary WHERE id=:diaryId")
        void deleteByDiaryId(int diaryId);
}
