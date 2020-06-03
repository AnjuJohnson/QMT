package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.VerseExplanationController;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;

/**
 * Created by Anju on 27-08-2019.
 */
public class VyakyanakuripFragment extends BaseFragment  {
    Context context;
    Dialog mDialog;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
  public  static   String mChapterno,mVerseno,mVyakyanakuripu;
    WebView mChapterExplationWebView;
    String malayalamFont;
    TextView mChapterNameTextView, mChapterMeaningTextView,mChapVerseNoTextView;
VerseExplanationController verseExplanationController;
    public VyakyanakuripFragment(String vyakyanakuripu,Context context, String chapterid, String verseno, String malayalamFont) {
        this.context = context;
        this.mChapterno = chapterid;
        this.mVerseno = verseno;
        this.malayalamFont = malayalamFont;
        this.mVyakyanakuripu = vyakyanakuripu;
      //  setRetainInstance(true);
    }

    public VyakyanakuripFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_chapterexplation, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        configviews(mFragmentView);
        datapassingChapterExplanation();

    }
private void configviews(View FragmentView){
    OnMenuIconChange = (Utility.menuIconChange) getActivity();
    mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
    mChapterMeaningTextView = FragmentView.findViewById(R.id.chapterMeaningTextView);
    mChapterNameTextView = FragmentView.findViewById(R.id.chapternameTextView);
    mChapterExplationWebView = FragmentView.findViewById(R.id.Explanationwebview);

}
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("VYAKYANAKURIPPU");
        OnMenuIconChange.iconchange(VyakyanakuripFragment.this);
        HomeActivity.fragment = VyakyanakuripFragment.this;
    }




    public void datapassingChapterExplanation() {
        getActivity(). runOnUiThread(new Runnable() {
            public void run() {
                setfontsize(malayalamFont);
                mChapterNameTextView.setText(mChapterno);
                mChapterMeaningTextView.setText(mVerseno);
                mChapterExplationWebView.setBackgroundColor(Color.parseColor("#EDFCF9"));
                mChapterExplationWebView.loadData(mVyakyanakuripu,"text/html; charset=UTF-8;", null);
            }
        });


    }


    private void setfontsize(String malayalamFont){
        final WebSettings webSettings = mChapterExplationWebView.getSettings();
        Resources res = getResources();

        if(malayalamFont.equalsIgnoreCase("0")){
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);
            webSettings.setDefaultFontSize(9);
        }
        else  if(malayalamFont.equalsIgnoreCase("1")){
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            webSettings.setDefaultFontSize(10);
        }
        else  if(malayalamFont.equalsIgnoreCase("2")){
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            webSettings.setDefaultFontSize(12);
        }
        else  if(malayalamFont.equalsIgnoreCase("3")){
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            webSettings.setDefaultFontSize(14);
        }
        else  if(malayalamFont.equalsIgnoreCase("4")){
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            webSettings.setDefaultFontSize(16);
        }
        else  if(malayalamFont.equalsIgnoreCase("5")){
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            webSettings.setDefaultFontSize(18);

        }
        else  if(malayalamFont.equalsIgnoreCase("6")){
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            webSettings.setDefaultFontSize(20);
        }
    }
}
