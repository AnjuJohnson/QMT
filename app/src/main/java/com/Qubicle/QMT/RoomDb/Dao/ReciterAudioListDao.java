package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Reciter;

import java.util.List;

@Dao
public interface ReciterAudioListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AudioCategoryReciterList data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<AudioCategoryReciterList> data);

    @Update
    void update(AudioCategoryReciterList data);

    @Delete
    void delete(AudioCategoryReciterList data);



    @Query("SELECT * FROM AudioCategoryReciterList")
    public List<AudioCategoryReciterList> getReciterAudiolist();

}
