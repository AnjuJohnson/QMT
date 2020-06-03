package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
@Entity(tableName = "ChapterList")

public class ChapterList {
    @PrimaryKey
    @NonNull
    private String Chapter_id;

    private String chapter_name;
    private String chapter_name_meaning;
    private String arabic_title;
    private String surah_no;
    private List<AyatList> ayat;

    public Boolean getOptionsOn() {
        return isOptionsOn;
    }

    public void setOptionsOn(Boolean optionsOn) {
        isOptionsOn = optionsOn;
    }

    private Boolean isOptionsOn=false;

    public String getChapter_id() {
        return Chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        Chapter_id = chapter_id;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_name_meaning() {
        return chapter_name_meaning;
    }

    public void setChapter_name_meaning(String chapter_name_meaning) {
        this.chapter_name_meaning = chapter_name_meaning;
    }

    public String getArabic_title() {
        return arabic_title;
    }

    public void setArabic_title(String arabic_title) {
        this.arabic_title = arabic_title;
    }

    public String getSurah_no() {
        return surah_no;
    }

    public void setSurah_no(String surah_no) {
       this. surah_no = surah_no;
    }

    public List<AyatList> getAyat() {
        return ayat;
    }

    public void setAyat(List<AyatList> ayat) {
        this.ayat = ayat;
    }


}
