package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface SearchListner {

    void fetchingfailure();
    void progress();
    void dataSearchpassing(List<VerseDetail> verseDetails);
    void openversedetail(String chapterno,String verseno);
}
