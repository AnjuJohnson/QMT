package com.Qubicle.QMT.Models;

/**
 * Created by Anju on 23-08-2019.
 */
public class WordMeaningList {


    private String meaning_mala;

   /* public WordMeaningList(String arabic, String malayalam) {
        this.meaning_arabic=arabic;
        this.meaning_mala=malayalam;
    }
*/
    public String getMeaning_mala() {
        return meaning_mala;
    }

    public void setMeaning_mala(String meaning_mala) {
        this.meaning_mala = meaning_mala;
    }

    public String getMeaning_arabic() {
        return meaning_arabic;
    }

    public void setMeaning_arabic(String meaning_arabic) {
        this.meaning_arabic = meaning_arabic;
    }

    private String meaning_arabic;

}
