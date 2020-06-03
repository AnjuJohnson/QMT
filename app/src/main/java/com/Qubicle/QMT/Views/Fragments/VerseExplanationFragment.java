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
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;

/**
 * Created by Anju on 27-08-2019.
 */
public class VerseExplanationFragment extends BaseFragment implements VerseDetailListener {
    Context context;
    Dialog mDialog;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
  public  static   String mChapterno,mVerseno;
    WebView mChapterExplationWebView;
    String malayalamFont;
    TextView mChapterNameTextView, mChapterMeaningTextView,mChapVerseNoTextView;
VerseExplanationController verseExplanationController;
    public VerseExplanationFragment(Context context, String chapterid,String verseno,String malayalamFont) {
        this.context = context;
        this.mChapterno = chapterid;
        this.mVerseno = verseno;
        this.malayalamFont = malayalamFont;
      //  setRetainInstance(true);
    }

    public VerseExplanationFragment() {

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
        verseExplanationController=new VerseExplanationController(VerseExplanationFragment.this,getActivity());
      //check data present offline else fetch it

        if (isNetworkAvailable()) {
            progress();
            verseExplanationController.startfetching(mChapterno,mVerseno);
        } else {
            VerseExplanation verseExplanation = verseExplanationController.checkExplanationdatapresent(mChapterno,mVerseno);
            if (verseExplanation == null) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            }
            else {
                datapassingChapterExplanation(verseExplanation);
            }
        }




        /*    if (isNetworkAvailable()) {
                progress();
                verseExplanationController.startfetching(mChapterno,mVerseno);
            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }



*/
    }
private void configviews(View FragmentView){
    OnMenuIconChange = (Utility.menuIconChange) getActivity();
    mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
    mChapterMeaningTextView = FragmentView.findViewById(R.id.chapterMeaningTextView);
    mChapterNameTextView = FragmentView.findViewById(R.id.chapternameTextView);

         mChapterExplationWebView = FragmentView.findViewById(R.id.Explanationwebview);
    mChapterExplationWebView.getSettings().setJavaScriptEnabled(true);
    mChapterExplationWebView.setLayerType(WebView.LAYER_TYPE_NONE, null);
    /*final WebSettings webSettings = mChapterExplationWebView.getSettings();
    Resources res = getResources();
    float  fontSize = res.getDimension(R.dimen.webviewfont);
    webSettings.setDefaultFontSize((int)fontSize);*/
}
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("VERSE EXPLANATION");
        OnMenuIconChange.iconchange(VerseExplanationFragment.this);
        HomeActivity.fragment = VerseExplanationFragment.this;
    }

    @Override
    public void fetchingsuccess() {
        if(mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {
        mDialog = Utility.showProgressBar(getActivity());
        mDialog.show();
    }

    @Override
    public void datapassingVerseDetail(List<VerseDetail> verseDetailsList) {

    }

    @Override
    public void verse_explanation(String chapterid, String ayatno) {

    }

    @Override
    public void addtofavourites(String chaptername, String chapterno, String ayatno, String verse, String translation) {

    }

    @Override
    public void verse_vyakyanakurip(String vyakyanakurip,String chapterid, String ayatno) {

    }

    @Override
    public void datapassingChapterExplanation(final VerseExplanation verseExplanation) {

        if(mDialog!=null){
            mDialog.dismiss();
        }



        getActivity(). runOnUiThread(new Runnable() {
            public void run() {
                setfontsize(malayalamFont);
                mChapterNameTextView.setText(verseExplanation.getChapter_name().trim());
                mChapterMeaningTextView.setText(verseExplanation.getChapter_name_meaning().trim());
                mChapterExplationWebView.setBackgroundColor(Color.parseColor("#EDFCF9"));

                String newBody = "<html><body>" + verseExplanation.getExplanation() + "</body></html>";
                mChapterExplationWebView.loadDataWithBaseURL(null, newBody, "text/html", "utf-8", null);

               // mChapterExplationWebView.loadData(verseExplanation.getExplanation(),"text/html; charset=UTF-8;", null);
            }
        });


    }

    @Override
    public void wordmeaningOn(String ayatno) {

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
