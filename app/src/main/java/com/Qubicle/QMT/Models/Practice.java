package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by Anju on 17-09-2019.
 */

public class Practice {

    String reciters_id;
    String reciter_name;
List<Verse> verse_details;

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

    public List<Verse> getVerse_details() {
        return verse_details;
    }

    public void setVerse_details(List<Verse> verse_details) {
        this.verse_details = verse_details;
    }
}
