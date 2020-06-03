package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by Anju on 17-09-2019.
 */

public class AmaniAudio {

    String reciters_id;
    String reciter_name;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    String topic;
    List<Audio> audio_list;

    public List<Audio> getAudio() {
        return audio;
    }

    public void setAudio(List<Audio> audio) {
        this.audio = audio;
    }

    List<Audio> audio;

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

    public List<Audio> getAudio_list() {
        return audio_list;
    }

    public void setAudio_list(List<Audio> audio_list) {
        this.audio_list = audio_list;
    }
}
