package com.Qubicle.QMT.RoomDb.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.Notes;

import java.util.List;

@Dao
public interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavourites(Favorites data);

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<ChapterList> data);*/

    @Update
    void update(Favorites data);

    @Delete
    void delete(Favorites data);



   @Query("SELECT * FROM Favorites WHERE surah_no = :surah_no AND ayat_no = :ayat_no" )
    public Favorites getIsFavourite(String surah_no, String ayat_no);


    @Query("SELECT * FROM Favorites")
    public List<Favorites> getallFavourites();



}
