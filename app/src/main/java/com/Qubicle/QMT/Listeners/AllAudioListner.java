package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.Audio;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface AllAudioListner {

    void dataaudipassing(List<Audio> audio,String page,String audiocategory);
    void juzdataaudipassing(List<Audio> audio,String page,String audiocategory);
    void stoppressed(Boolean stop);
}
