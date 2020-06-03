package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anju on 27-08-2019.
 */
@Entity(tableName = "ChapterExplanation")
public class ChapterExplanation {
    @PrimaryKey
    @NonNull
    private String id;

    private String chapter_name;
    private String chapter_name_meaning;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
