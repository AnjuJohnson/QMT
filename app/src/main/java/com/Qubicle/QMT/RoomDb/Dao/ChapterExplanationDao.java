package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.Qubicle.QMT.Models.ChapterExplanation;

import java.util.List;

@Dao
public interface ChapterExplanationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ChapterExplanation data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<ChapterExplanation> data);

    @Update
    void update(ChapterExplanation data);

    @Delete
    void delete(ChapterExplanation data);



    @Query("SELECT * FROM ChapterExplanation WHERE id = :chapter_id")
    public ChapterExplanation getchapterexplanation(String chapter_id);

}
