package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

@Dao
public interface ReciterListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reciter data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Reciter> data);

    @Update
    void update(Reciter data);

    @Delete
    void delete(Reciter data);



    @Query("SELECT * FROM Reciter")
    public List<Reciter> getReciterlist();

}
