package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anju on 27-08-2019.
 */
@Entity(tableName = "VerseExplanation")
public class VerseExplanation {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int explanation_id;


    private String chapter_no;
    private String verse_no;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    @NonNull
    public int getExplanation_id() {
        return explanation_id;
    }

    public void setExplanation_id(@NonNull int explanation_id) {
        this.explanation_id = explanation_id;
    }

    public String getChapter_no() {
        return chapter_no;
    }

    public void setChapter_no(String chapter_no) {
        this.chapter_no = chapter_no;
    }

    public String getVerse_no() {
        return verse_no;
    }

    public void setVerse_no(String verse_no) {
        this.verse_no = verse_no;
    }

    private String chapter_name;

    private String chapter_name_meaning;

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_name_meaning() {
        return chapter_name_meaning;
    }

    public void setChapter_name_meaning(String chapter_name_meaning) {
        this.chapter_name_meaning = chapter_name_meaning;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    private String explanation;



}
