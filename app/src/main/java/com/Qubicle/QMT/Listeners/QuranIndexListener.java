package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.Models.VerseDetail;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public interface QuranIndexListener {
    void fetchingsuccess(List<ChapterList> chapterlist);
    void parsemainindex(List<MaintopicIndex> maintopicIndexList);
    void parsealltopicindex(List<Keyword> maintopicIndexList);
    void fetchingfailure();
    void progress();
void passdatatiIndex(String chaptername,String chapterno);
void passkeywordreference(String referenceid,String keyword);
    void parsemainindexverse(List<IndexVerseList> maintopicIndexList);

}
