package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anju on 17-09-2019.
 */

public class ReciterNew {
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

    String reciters_id;
    String reciter_name;


}
