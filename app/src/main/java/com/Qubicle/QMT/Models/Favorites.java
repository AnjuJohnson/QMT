package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anju on 17-09-2019.
 */
@Entity(tableName = "Favorites")
public class Favorites {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int fav_id;


    private String ayat_no;
    private String meaning_arabic;

    public int getFav_id() {
        return fav_id;
    }

    public void setFav_id(int fav_id) {
        this.fav_id = fav_id;
    }

    public String getAyat_no() {
        return ayat_no;
    }

    public void setAyat_no(String ayat_no) {
        this.ayat_no = ayat_no;
    }

    public String getMeaning_arabic() {
        return meaning_arabic;
    }

    public void setMeaning_arabic(String meaning_arabic) {
        this.meaning_arabic = meaning_arabic;
    }

    public String getMeaning_malayalam() {
        return meaning_malayalam;
    }

    public void setMeaning_malayalam(String meaning_malayalam) {
        this.meaning_malayalam = meaning_malayalam;
    }

    public String getSurah_no() {
        return surah_no;
    }

    public void setSurah_no(String surah_no) {
        this.surah_no = surah_no;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    private String meaning_malayalam;
    private String surah_no;
    private String chapter_name;





}
