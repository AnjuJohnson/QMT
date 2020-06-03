package com.Qubicle.QMT.Models;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public class JuzDetail {
    private String surah_no;
    private String ayat_no;
    private String meaning_arabic;
    private String meaning_malayalam;

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



    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    private String audio;


    public String getAyat_no() {
        return ayat_no;
    }

    public void setAyat_no(String ayat_no) {
        this.ayat_no = ayat_no;
    }
}
