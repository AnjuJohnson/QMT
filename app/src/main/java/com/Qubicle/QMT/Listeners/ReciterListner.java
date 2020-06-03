package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Reciter;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface ReciterListner {

    void fetchingfailure();
    void progress();
    void dataReciterpassing(List<Reciter> audio);
    void dataReciterAudiopassing(List<AudioCategoryReciterList> audio);
}
