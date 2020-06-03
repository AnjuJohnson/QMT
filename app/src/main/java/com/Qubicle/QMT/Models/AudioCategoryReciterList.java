package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by Anju on 17-09-2019.
 */
@Entity(tableName = "AudioCategoryReciterList")
public class AudioCategoryReciterList {
    @PrimaryKey
    @NonNull
    String audio_category_id;

    String audio_category;

List<ReciterNew> reciter_list;

    @NonNull
    public String getAudio_category_id() {
        return audio_category_id;
    }

    public void setAudio_category_id(@NonNull String audio_category_id) {
        this.audio_category_id = audio_category_id;
    }

    public String getAudio_category() {
        return audio_category;
    }

    public void setAudio_category(String audio_category) {
        this.audio_category = audio_category;
    }

    public List<ReciterNew> getReciter_list() {
        return reciter_list;
    }

    public void setReciter_list(List<ReciterNew> reciter_list) {
        this.reciter_list = reciter_list;
    }
}
