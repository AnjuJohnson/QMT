package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.IndexVerseDetailAdapter;
import com.Qubicle.QMT.Adapter.MainTopicAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.IndexVersefragmentController;
import com.Qubicle.QMT.Controllers.MaintopicsfragmentController;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class IndexVerseDetailsfragment extends BaseFragment implements VerseDetailListener {
    WebView mWebview;
    TextView mSuraTextView, mJuzTextView;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    IndexVersefragmentController mMaintopiccontroller;
    Dialog mDialog;
    List<MaintopicIndex> maintopicIndexList;
    RecyclerView mMaintopicsRecycler;
    RecyclerView.LayoutManager layoutManager;
    MainTopicAdapter mainTopicAdapter;
    String malayalamFont, arabicfont;
    EditText searchEdittext;
    String chapterno,keyid;
    public static String sVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, sTranslationNumber;
    private String page;
CardView searchcard;

    public IndexVerseDetailsfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_maintopics, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {

        sVerseRadioButton = PreferenceManager.getManager().getVerse();
        sTranslationRadioButton = PreferenceManager.getManager().gettranslationOn();
        sWordMeaningRadioButton = PreferenceManager.getManager().getWordMeaning();
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        arabicfont = PreferenceManager.getManager().getArabicFont();
        sTranslationNumber = PreferenceManager.getManager().getTranslation();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mMaintopiccontroller = new IndexVersefragmentController(IndexVerseDetailsfragment.this, getActivity());
        mMaintopicsRecycler = mFragmentView.findViewById(R.id.mMaintopicsRecycler);
        searchcard = mFragmentView.findViewById(R.id.searchcard);
        searchcard.setVisibility(View.GONE);
        mMaintopicsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mMaintopicsRecycler.setLayoutManager(layoutManager);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            keyid = bundle.getString("KEYORD_ID");
            page = bundle.getString("PAGE");
        }





        if (isNetworkAvailable()) {
            mMaintopiccontroller.startfetching(keyid,page);
        }
        else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("VERSE");
        OnMenuIconChange.iconchange(IndexVerseDetailsfragment.this);
        HomeActivity.fragment = IndexVerseDetailsfragment.this;
    }


    @Override
    public void fetchingsuccess() {

    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {

    }

    @Override
    public void datapassingVerseDetail(List<VerseDetail> verseDetailsList) {
        IndexVerseDetailAdapter IndexVerseDetailAdapter=new IndexVerseDetailAdapter(IndexVerseDetailsfragment.this,  verseDetailsList, getActivity(),
                sVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, malayalamFont, arabicfont, sTranslationNumber);
        mMaintopicsRecycler.setAdapter(IndexVerseDetailAdapter);
    }

    @Override
    public void verse_explanation(String chapterid, String ayatno) {

    }

    @Override
    public void addtofavourites(String chaptername, String chapterno, String ayatno, String verse, String translation) {

    }

    @Override
    public void verse_vyakyanakurip(String vyakyanakurip, String chapterid, String ayatno) {

    }

    @Override
    public void datapassingChapterExplanation(VerseExplanation verseExplanation) {

    }

    @Override
    public void wordmeaningOn(String ayatno) {

    }
}