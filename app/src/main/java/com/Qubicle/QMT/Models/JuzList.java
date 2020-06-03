package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anju on 27-08-2019.
 */
@Entity(tableName = "JuzList")
public class JuzList {
    @PrimaryKey
    @NonNull
    private String name;

    private String from;
    private String to;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
     this.   from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
      this.  to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
     this.   name = name;
    }



}
