package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.VerseExplanation;

import java.util.List;

@Dao
public interface VerseExplanationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(VerseExplanation data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<VerseExplanation> data);

    @Update
    void update(VerseExplanation data);

    @Delete
    void delete(VerseExplanation data);



    @Query("SELECT * FROM VerseExplanation WHERE chapter_no = :chapter_no AND verse_no = :verse_no" )
    public VerseExplanation getverseexplanation(String chapter_no,String verse_no);


    @Query("SELECT * FROM VerseExplanation")
    public List<VerseExplanation> getAllverseexplanation();

    @Query("DELETE FROM VerseExplanation")
    int deleteall();

}
