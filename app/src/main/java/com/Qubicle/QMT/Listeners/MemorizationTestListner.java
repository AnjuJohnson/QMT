package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Practice;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface MemorizationTestListner {

    void fetchingfailure();
    void progress();
    void datapassing(List<ChapterList> chapterLists);
    void versedetail(List<Verse> verseList);
    void Practiceversedetail(List<Practice> verseList);
    void dataReciterAudiopassing(List<AudioCategoryReciterList> audio);

}
