package com.Qubicle.QMT.RoomDb.Dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.VerseDetail;


import java.util.List;

@Dao
public interface ChapterListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ChapterList data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<ChapterList> data);

    @Update
    void update(ChapterList data);

    @Delete
    void delete(ChapterList data);



    @Query("SELECT * FROM ChapterList")
    public List<ChapterList> getChapterList();


    @Query("SELECT * FROM ChapterList WHERE surah_no != 0")
    public List<ChapterList> getChapterExceptZeroList();

    @Query("SELECT * FROM ChapterList  WHERE surah_no = :surah_no")
    public List<ChapterList> getChapterdetail(String surah_no);
}
