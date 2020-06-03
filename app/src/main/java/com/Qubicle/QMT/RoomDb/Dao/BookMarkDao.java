package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.ChapterExplanation;

import java.util.List;

@Dao
public interface BookMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Bookmark data);

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<ChapterList> data);*/

    @Update
    void update(Bookmark data);

    @Delete
    void delete(Bookmark data);



    @Query("SELECT * FROM Bookmark WHERE active =1" )
    public List<Bookmark> getAllBookmark();


    @Query("SELECT * FROM Bookmark WHERE chapter_name = :chapter_name AND ayat_no = :ayat_no AND page = :page AND active =1")
    public Bookmark getBookmark(String chapter_name,String ayat_no,String page);

}
