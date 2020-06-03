package com.Qubicle.QMT.utils;


import androidx.recyclerview.widget.LinearSmoothScroller;

import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.ReciterNew;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String BASE_URL = "http://qubicle.co.in/qmt/";

    public static final String SHARED_KEY = "qmt";
    public static  String INDEX_PAGE = "all";
    public static  String CHAPTERNAME = "chaptername";
    public static  String ALLCHAPTERPAGE = "allchapterpage";
    public static  String VERSEDETAILPAGE_BACKPRESS = "not_reading_page";
    public static  String READING_BACKPRESS = "initialloading";
    public static  Boolean SEARCH_ON = false;
    public static  Boolean ISREPEATSINGLE = false;
    public static  Boolean ISREPEATMULTIPLE = false;
    public static  List<Reciter> RECITER_LIST=new ArrayList<>() ;
    public static  List<ReciterNew> RECITER_AUDIO_CATEGORY_LIST=new ArrayList<>() ;
    public static  String[] CHPATER_RECITER_LIST ;
    public static SimpleExoPlayer player;
    public static String sChapterno,sVerseFrom,sAudioCategory,sVerseto,sReciternames,sChapterFrom,sChapterTo;

}

