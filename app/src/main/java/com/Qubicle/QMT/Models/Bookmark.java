package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anju on 17-09-2019.
 */
@Entity(tableName = "Bookmark")
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int bookmark_id;


private String chapter_name;
private String surah_no;
private String ayat_no;
private String page;

    public String getJuz_no() {
        return juz_no;
    }

    public void setJuz_no(String juz_no) {
        this.juz_no = juz_no;
    }

    private String juz_no;

    public int getBookmark_id() {
        return bookmark_id;
    }

    public void setBookmark_id(int bookmark_id) {
        this.bookmark_id = bookmark_id;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getSurah_no() {
        return surah_no;
    }

    public void setSurah_no(String surah_no) {
        this.surah_no = surah_no;
    }

    public String getAyat_no() {
        return ayat_no;
    }

    public void setAyat_no(String ayat_no) {
        this.ayat_no = ayat_no;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    private String active;



}
