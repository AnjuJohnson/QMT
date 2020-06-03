package com.Qubicle.QMT.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
@Entity(tableName = "VerseDetail")
public class VerseDetail {
    @NonNull
    public String getAyat_id() {
        return ayat_id;
    }

    public void setAyat_id(@NonNull String ayat_id) {
        this.ayat_id = ayat_id;
    }

    @PrimaryKey
    @NonNull
    private String ayat_id;



    private String surah_no;
    private String ayat_no;
    private String meaning_arabic;

    private String meaning_malayalam;
    private String chapter_name;

    public String getDetailed_definition() {
        return detailed_definition;
    }

    public void setDetailed_definition(String detailed_definition) {
        this.detailed_definition = detailed_definition;
    }

    private String detailed_definition;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    private Boolean isSelected=false;

    public Boolean getFavourites() {
        return isFavourites;
    }

    public void setFavourites(Boolean favourites) {
        isFavourites = favourites;
    }

    private Boolean isFavourites=false;

    public Boolean getWordmeaning() {
        return isWordmeaning;
    }

    public void setWordmeaning(Boolean wordmeaning) {
        isWordmeaning = wordmeaning;
    }

    private Boolean isWordmeaning=true;

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

    private String chapter_name_meaning;

    public String getMeaning_malayalam_new() {
        return meaning_malayalam_new;
    }

    public void setMeaning_malayalam_new(String meaning_malayalam_new) {
        this.meaning_malayalam_new = meaning_malayalam_new;
    }

    private String meaning_malayalam_new;
    private String audio_title;
List<WordMeaningList> wordMeaningLists;

    public List<WordMeaningListNew> getWord_meaning() {
        return word_meaning;
    }

    public void setWord_meaning(List<WordMeaningListNew> word_meaning) {
        this.word_meaning = word_meaning;
    }

    List<WordMeaningListNew> word_meaning;

    public List<WordMeaningList> getWordMeaningLists() {
        return wordMeaningLists;
    }

    public void setWordMeaningLists(List<WordMeaningList> wordMeaningLists) {
        this.wordMeaningLists = wordMeaningLists;
    }

    public String getSurah_no() {
        return surah_no;
    }

    public void setSurah_no(String surah_no) {
        this.surah_no = surah_no;
    }

    public String getMeaning_arabic() {
        return meaning_arabic;
    }

    public void setMeaning_arabic(String meaning_arabic) {
        this.meaning_arabic = meaning_arabic;
    }



    public String getMeaning_malayalam() {
        return meaning_malayalam;
    }

    public void setMeaning_malayalam(String meaning_malayalam) {
        this.meaning_malayalam = meaning_malayalam;
    }

    public String getAudio_title() {
        return audio_title;
    }

    public void setAudio_title(String audio_title) {
        this.audio_title = audio_title;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    private String audio;

    public String getChapter_no() {
        return chapter_no;
    }

    public void setChapter_no(String chapter_no) {
        this.chapter_no = chapter_no;
    }

    private String chapter_no;

    public String getVerse_no() {
        return verse_no;
    }

    public void setVerse_no(String verse_no) {
        this.verse_no = verse_no;
    }

    private String verse_no;


    public String getAyat_no() {
        return ayat_no;
    }

    public void setAyat_no(String ayat_no) {
        this.ayat_no = ayat_no;
    }


}
