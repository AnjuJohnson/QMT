package com.Qubicle.QMT.Models;

/**
 * Created by Anju on 13-09-2019.
 */
public class Audio {
    private String surah_no;
    private String ayat_no;

    private String audio_title;

    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String audio;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;


    public String getAudio_title() {
        return audio_title;
    }

    public void setAudio_title(String audio_title) {
        this.audio_title = audio_title;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
