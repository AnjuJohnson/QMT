package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public interface SuraListener {
    void fetchingsuccess();
    void fetchingfailure();
    void progress();
void datapassing(List<ChapterList> chapterLists);
void chapter_explanation_listener(String chapterid);

void verse_detail(String chapterid,String ayatid,String ayatno);
void datapassingChapterExplanation(ChapterExplanation chapterExplanation);
void amaniaudioParameter(String chapterno,String chaptername, String chaptermeaning, String verseno);
void chapterqa(String chapterno);
void optionOn(String chapterno,String grouppostion);
void verseopen(String chapterno,String grouppostion);
void indexopen(String chaptername,String chapterno);

}
