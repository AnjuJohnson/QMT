package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Notes;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Notes data);

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<ChapterList> data);*/

    @Update
    void update(Notes data);

    @Delete
    void delete(Notes data);



    @Query("SELECT * FROM Notes WHERE surah_no = :surah_no AND ayat_no = :ayat_no AND active =1" )
    public List<Notes> getallnotes(String surah_no, String ayat_no);


    @Query("SELECT * FROM Notes " )
    public List<Notes> getFullNotes();

    @Query("SELECT * FROM Notes WHERE note_id = :note_id  AND active =1" )
    public Notes getSinglenotes(String note_id);

   /* @Query("SELECT * FROM Notes WHERE chapter_name = :chapter_name AND ayat_no = :ayat_no AND page = :page AND active =1")
    public Bookmark getBookmark(String chapter_name, String ayat_no, String page);*/

}
