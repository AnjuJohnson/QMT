package com.Qubicle.QMT.Models;

import java.sql.Ref;
import java.util.List;

/**
 * Created by Anju on 17-09-2019.
 */
public class QA {
    String id;
    String question;
    String surah_no;
    String answers;
    String reference_surah_no;

    public String getReference_surah_no() {
        return reference_surah_no;
    }

    public void setReference_surah_no(String reference_surah_no) {
        this.reference_surah_no = reference_surah_no;
    }

    public String getReference_verse_no() {
        return reference_verse_no;
    }

    public void setReference_verse_no(String reference_verse_no) {
        this.reference_verse_no = reference_verse_no;
    }

    String reference_verse_no;

    List<Reference> reference;

    public String getSurah_no() {
        return surah_no;
    }

    public void setSurah_no(String surah_no) {
        this.surah_no = surah_no;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public List<Reference> getReference() {
        return reference;
    }

    public void setReference(List<Reference> reference) {
        this.reference = reference;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
