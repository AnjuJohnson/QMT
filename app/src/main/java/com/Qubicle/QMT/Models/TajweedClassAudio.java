package com.Qubicle.QMT.Models;

import java.util.List;

/**
 * Created by Anju on 17-09-2019.
 */

public class TajweedClassAudio {

    String reciters_id;
    String reciter_name;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    String topic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public List<TajweedAudio> getAudio() {
        return audio;
    }

    public void setAudio(List<TajweedAudio> audio) {
        this.audio = audio;
    }

    List<TajweedAudio> audio;

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
