package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public interface JuzListener {
    void fetchingsuccess();
    void fetchingfailure();
    void progress();
void datapassing(List<JuzList> juzLists);
void fetchjuzdetails(String juzno);
    void datapassingJuzDetail(List<JuzDetailNew> juzDetailsList);
    void verse_explanation(String chapterid,String ayatno);
    void addtofavourites(String chaptername,String chapterno,String ayatno,String verse,String translation);
}
