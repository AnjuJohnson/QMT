package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

@Dao
public interface JuzDetailListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(JuzDetailNew data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<JuzDetailNew> data);

    @Update
    void update(JuzDetailNew data);

    @Delete
    void delete(JuzDetailNew data);



    @Query("SELECT * FROM JuzDetailNew  WHERE juzno = :juzno")
    public List<JuzDetailNew> getJuzDetailList(String juzno);


    @Query("SELECT * FROM JuzDetailNew")
    public List<JuzDetailNew> getJuzDetailListAll();

    @Query("DELETE FROM JuzDetailNew")
    int deleteall();

}
