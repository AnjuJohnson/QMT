package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzList;

import java.util.List;

@Dao
public interface JuzListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(JuzList data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<JuzList> data);

    @Update
    void update(JuzList data);

    @Delete
    void delete(JuzList data);



    @Query("SELECT * FROM JuzList")
    public List<JuzList> getJuzList();

}
