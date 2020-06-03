package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public interface VerseDetailListener {
    void fetchingsuccess();
    void fetchingfailure();
    void progress();
void datapassingVerseDetail(List<VerseDetail> verseDetailsList);
    void verse_explanation(String chapterid,String ayatno);
    void addtofavourites(String chaptername,String chapterno,String ayatno,String verse,String translation);
    void verse_vyakyanakurip(String vyakyanakurip,String chapterid,String ayatno);
    void datapassingChapterExplanation(VerseExplanation verseExplanation);

    void wordmeaningOn(String ayatno);
}
