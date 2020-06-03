package com.Qubicle.QMT.utils.Preference;

import android.content.SharedPreferences;

import com.Qubicle.QMT.Base.BaseApplication;
import com.Qubicle.QMT.utils.Constants;


public class PreferenceManager {
    private static PreferenceManager manager;
    private SharedPreferences sharedPreferences;

    private PreferenceManager() {
        sharedPreferences = BaseApplication.getApplication().getSharedPreferences(Constants.SHARED_KEY, 0);
    }

    public static PreferenceManager getManager() {
        if (manager == null) {
            manager = new PreferenceManager();
        }
        return manager;
    }

    public void clearprefernce() {
        sharedPreferences.edit().clear().apply();
    }


    public String getRepeatSingleValue() {
        return sharedPreferences.getString(PREF_KEY.KEY_REPEATSINGLE_VALUE, "0");
    }

    public void setRepeatSingleValue(String repeatsinglevalue) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_REPEATSINGLE_VALUE, repeatsinglevalue).apply();
    }

    public String getRepeatMultipleValue() {
        return sharedPreferences.getString(PREF_KEY.KEY_REPEATMULTIPLE_VALUE, "0");
    }

    public void setRepeatMultipleValue(String repeatmultiplevalue) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_REPEATMULTIPLE_VALUE, repeatmultiplevalue).apply();
    }


    public String getTranslation() {
        return sharedPreferences.getString(PREF_KEY.KEY_TRANSLATION, "Cheriya Mundam/Parappoor");
    }

    public void setTranslation(String translation) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_TRANSLATION, translation).apply();
    }
    public String getAutoScroll() {
        return sharedPreferences.getString(PREF_KEY.KEY_AUTOSCROLL, "autoscrollOn");
    }

    public void setAutoScroll(String autoScroll) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_AUTOSCROLL, autoScroll).apply();
    }

    public String getWordMeaning() {
        return sharedPreferences.getString(PREF_KEY.KEY_WORDMEANING, "wordmeaningOn");
    }

    public void setWordMeaning(String wordMeaning) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_WORDMEANING, wordMeaning).apply();
    }

    public String getVerse() {
        return sharedPreferences.getString(PREF_KEY.KEY_VERSE, "verseOn");
    }

    public void setVerse(String verse) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_VERSE, verse).apply();
    }

    public String gettranslationOn() {
        return sharedPreferences.getString(PREF_KEY.KEY_TRANSLATION_ON, "translationOn");
    }

    public void settranslationOn(String translation) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_TRANSLATION_ON, translation).apply();
    }

    public String getReciter() {
        return sharedPreferences.getString(PREF_KEY.KEY_RECITER, "DEFAULT");
    }

    public void setReciter(String reciter) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_RECITER, reciter).apply();
    }
    public String getMalayalamFont() {
        return sharedPreferences.getString(PREF_KEY.KEY_MALAYALAMFONT, "DEFAULT");
    }

    public void setMalayalamFont(String malayalamFont) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_MALAYALAMFONT, malayalamFont).apply();
    }
    public String getArabicFont() {
        return sharedPreferences.getString(PREF_KEY.KEY_ARABICFONT, "DEFAULT");
    }

    public void setArabicFont(String arabicFont) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_ARABICFONT, arabicFont).apply();
    }


    public String getReadingPage() {
        return sharedPreferences.getString(PREF_KEY.KEY_READINGPAGE, "surapage");
    }

    public void setReadingPage(String page) {
        sharedPreferences.edit().putString(PREF_KEY.KEY_READINGPAGE, page).apply();
    }

}