package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
@Entity(tableName = "JuzDetailNew")
public class JuzDetailNew {


    @NonNull
    public int getJuzid() {
        return juzid;
    }

    public void setJuzid(@NonNull int juzid) {
        this.juzid = juzid;
    }

    public String getJuzno() {
        return juzno;
    }

    public void setJuzno(String juzno) {
        this.juzno = juzno;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int juzid;





    private String juzno;


    private String surah_no;
List<Verse> verse;

    public List<Verse> getVerse() {
        return verse;
    }

    public void setVerse(List<Verse> verse) {
        this.verse = verse;
    }

    public String getChapter_name_meaning() {
        return chapter_name_meaning;
    }

    public void setChapter_name_meaning(String chapter_name_meaning) {
        this.chapter_name_meaning = chapter_name_meaning;
    }

    private String chapter_name_meaning;



    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    private String chapter_name;


    public String getSurah_no() {
        return surah_no;
    }

    public void setSurah_no(String surah_no) {
        this.surah_no = surah_no;
    }

}
