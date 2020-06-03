package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Models.TajweedClassAudio;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface TajweedListner {

    void fetchingfailure();
    void progress();
    void fetchingsuccess(List<Tajweed> TajweedTitles);
    void passaudioparameter(String title,String minSortOrder,String maxSortOrder);
    void dataTajweedClassesAudio(List<TajweedClassAudio> tajweedclasslist);
}
