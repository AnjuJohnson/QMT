package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.SpinnerchapterlistModel;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface AudioListner {
    void fetchaudio(String audiocategory,String chaptername,String chaptername_meaning,String chapterNo,String verseMinLimit,String verseMaxLimit);
    void fetchjuzaudio(String audiocategory,List<SpinnerchapterlistModel> spinnerchapterlistModels,  String verseMinLimit, String verseMaxLimit);
    void fetchingaudiofailure();
    void progress();
    void dataaudipassing(List<Audio> audio);
}
