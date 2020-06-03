package com.Qubicle.QMT.Models;

/**
 * Created by Anju on 17-09-2019.
 */
public class IndexVerseList {
 String chapter_no;
 String verse_no;

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

   public String getMeaning_arabic() {
      return meaning_arabic;
   }

   public void setMeaning_arabic(String meaning_arabic) {
      this.meaning_arabic = meaning_arabic;
   }

   String meaning_arabic;
   String keyword_id;
   String title;
   String meaning_malayalam_new;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeaning_malayalam_new() {
        return meaning_malayalam_new;
    }

    public void setMeaning_malayalam_new(String meaning_malayalam_new) {
        this.meaning_malayalam_new = meaning_malayalam_new;
    }

    public String getMeaning_malayalam() {
        return meaning_malayalam;
    }

    public void setMeaning_malayalam(String meaning_malayalam) {
        this.meaning_malayalam = meaning_malayalam;
    }

    String meaning_malayalam;

    public String getKeyword_id() {
        return keyword_id;
    }

    public void setKeyword_id(String keyword_id) {
        this.keyword_id = keyword_id;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    String ref_id;
}
