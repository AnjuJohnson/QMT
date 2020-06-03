package com.Qubicle.QMT.Models;

import java.util.List;

/**
 * Created by Anju on 17-09-2019.
 */

public class TajweedUlQuranAudio {

    String reciters_id;
    String reciter_name;
    String tajweed_quran_id;

    public String getTajweed_quran_id() {
        return tajweed_quran_id;
    }

    public void setTajweed_quran_id(String tajweed_quran_id) {
        this.tajweed_quran_id = tajweed_quran_id;
    }

    public String getChapter_no() {
        return chapter_no;
    }

    public void setChapter_no(String chapter_no) {
        this.chapter_no = chapter_no;
    }

    String chapter_no;


    public List<ULQuranAudio> getAudio() {
        return audio;
    }

    public void setAudio(List<ULQuranAudio> audio) {
        this.audio = audio;
    }

    List<ULQuranAudio> audio;

    public String getReciters_id() {
        return reciters_id;
    }

    public void setReciters_id(String reciters_id) {
        this.reciters_id = reciters_id;
    }

    public String getReciter_name() {
        return reciter_name;
    }

    public void setReciter_name(String reciter_name) {
        this.reciter_name = reciter_name;
    }


}
