package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Models.TajweedUlQuranAudio;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface TajweedUlListner {

    void fetchingfailure();
    void progress();
    void datapassing(List<ChapterList> chapterLists);
void passaudioparameter(String chapterno);
void dataTajweedClassesAudio(List<TajweedUlQuranAudio> chapterLists);
}
