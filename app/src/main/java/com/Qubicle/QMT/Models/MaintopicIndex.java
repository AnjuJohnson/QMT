package com.Qubicle.QMT.Models;

import java.util.List;

/**
 * Created by Anju on 17-09-2019.
 */
public class MaintopicIndex {
    String alphabet;
    List<Keyword> keywords;

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public List<Keyword> getKeyword() {
        return keywords;
    }

    public void setKeyword(List<Keyword> keyword) {
        this.keywords = keyword;
    }
}
