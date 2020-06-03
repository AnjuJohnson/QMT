package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */

public class OfflineJuzDetailNew {


    public String getJuzno() {
        return juz_no;
    }

    public void setJuzno(String juz_no) {
        this.juz_no = juz_no;
    }



    private String juz_no;

List<JuzDetailNew> juz_details;

    public List<JuzDetailNew> getJuz_details() {
        return juz_details;
    }

    public void setJuz_details(List<JuzDetailNew> juz_details) {
        this.juz_details = juz_details;
    }
}
