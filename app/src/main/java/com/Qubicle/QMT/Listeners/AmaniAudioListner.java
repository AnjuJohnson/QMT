package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.Reciter;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface AmaniAudioListner {

    void fetchingfailure();
    void progress();
    void dataAmaniAudio(List<AmaniAudio> audio);
}
