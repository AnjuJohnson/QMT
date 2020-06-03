package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.QA;

import java.util.List;

/**
 * Created by Anju on 23-08-2019.
 */
public interface QAListener {
    void fetchingsuccess(List<QA> qa);
    void fetchingfailure();
    void progress();
    void reference(String chapterno,String verseno);

}
