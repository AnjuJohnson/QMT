package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public interface SettingsListener {

    void fetchingfailure();
    void progress();

void successVerseDetail();
void successChapterExplanation();
void successVerseExplanation();
void successJuzDetail();

}
