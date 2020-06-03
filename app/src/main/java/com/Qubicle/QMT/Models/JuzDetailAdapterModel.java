package com.Qubicle.QMT.Models;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public class JuzDetailAdapterModel {
    private String surah_no;
    private String ayat_no;
    private String meaning_arabic;
    private Boolean isSelected=false;
    private String juzno;

    public String getChapternameAll() {
        return chapternameAll;
    }

    public void setChapternameAll(String chapternameAll) {
        this.chapternameAll = chapternameAll;
    }

    private String chapternameAll;

    public String getJuzno() {
        return juzno;
    }

    public void setJuzno(String juzno) {
        this.juzno = juzno;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
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
    private Boolean isFavourites=false;

    public Boolean getFavourites() {
        return isFavourites;
    }

    public void setFavourites(Boolean favourites) {
        isFavourites = favourites;
    }

    private String meaning_malayalam;

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
    private String meaning_malayalam_new;

    public String getMeaning_malayalam_new() {
        return meaning_malayalam_new;
    }

    public void setMeaning_malayalam_new(String meaning_malayalam_new) {
        this.meaning_malayalam_new = meaning_malayalam_new;
    }
}
