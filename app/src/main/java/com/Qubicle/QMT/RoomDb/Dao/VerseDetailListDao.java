package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

@Dao
public interface VerseDetailListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(VerseDetail data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<VerseDetail> data);

    @Update
    void update(VerseDetail data);

    @Delete
    void delete(VerseDetail data);



    @Query("SELECT * FROM VerseDetail  WHERE surah_no = :surah_no")
    public List<VerseDetail> getVerseDetailList(String surah_no);




}
