package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.ChapterExplanationController;
import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;

/**
 * Created by Anju on 27-08-2019.
 */
public class ChapterExplanationFragment extends BaseFragment implements SuraListener {
    Context context;
    Dialog mDialog;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    String mChapterId, malayalamFont;
    WebView mChapterExplationWebView;
    Float fontSize;
    TextView mChapterNameTextView, mChapterMeaningTextView;
    ChapterExplanationController chapterExplanationController;

    public ChapterExplanationFragment(Context context, String chapterid, String malayalamFont) {
        this.context = context;
        this.mChapterId = chapterid;
        this.malayalamFont = malayalamFont;
        // setRetainInstance(true);
    }

    public ChapterExplanationFragment() {

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
        chapterExplanationController = new ChapterExplanationController(ChapterExplanationFragment.this, getActivity());
        //check data present offline else fetch it

        if (isNetworkAvailable()) {
            progress();
            chapterExplanationController.startfetching(mChapterId);
        } else {
            ChapterExplanation chapterExplanation = chapterExplanationController.checkExplanationdatapresent(mChapterId);
            if (chapterExplanation == null) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            }
            else {
                datapassingChapterExplanation(chapterExplanation);
            }
        }


     /*   ChapterExplanation chapterExplanation = chapterExplanationController.checkExplanationdatapresent(mChapterId);
        if (chapterExplanation == null ) {
            if (isNetworkAvailable()) {
                progress();
                chapterExplanationController.startfetching(mChapterId);
            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        } else {
            datapassingChapterExplanation(chapterExplanation);
        }
*/

    }

    private void configviews(View FragmentView) {
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
        mOnFragmnetSwitch.loadTitle("CHAPTER EXPLANATION");
        OnMenuIconChange.iconchange(ChapterExplanationFragment.this);
        HomeActivity.fragment = ChapterExplanationFragment.this;
    }

    @Override
    public void fetchingsuccess() {
        mDialog.dismiss();
    }

    @Override
    public void fetchingfailure() {
        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
        mDialog.dismiss();
    }

    @Override
    public void progress() {
        mDialog = Utility.showProgressBar(getActivity());
        mDialog.show();
    }

    @Override
    public void datapassing(List<ChapterList> chapterLists) {

    }

    @Override
    public void chapter_explanation_listener(String chapterid) {
        //  chapterExplanationController.fetchchapterid(chapterid);
    }

    @Override
    public void verse_detail(String chapterid, String ayatid, String ayatno) {

    }

    @Override
    public void datapassingChapterExplanation(final ChapterExplanation chapterExplanation) {
        if (chapterExplanation != null) {
            if (!chapterExplanation.getContent().isEmpty()) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setfontsize(malayalamFont);
                        Log.d("explanation", chapterExplanation.getContent());
                        mChapterNameTextView.setText(chapterExplanation.getChapter_name().trim());
                        mChapterMeaningTextView.setText(chapterExplanation.getChapter_name_meaning().trim());
                        mChapterExplationWebView.setBackgroundColor(Color.parseColor("#EDFCF9"));

                        String newBody = "<html><body>" + chapterExplanation.getContent() + "</body></html>";
                        mChapterExplationWebView.loadDataWithBaseURL(null, newBody, "text/html", "utf-8", null);
                        //  mChapterExplationWebView.loadData(chapterExplanation.getContent(),"text/html; charset=UTF-8;", null);
                    }
                });

            }
            else {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void amaniaudioParameter(String chapterno, String chaptername, String chaptermeaning, String verseno) {

    }

    @Override
    public void chapterqa(String chapterno) {

    }

    @Override
    public void optionOn(String chapterno, String grouppostion) {

    }

    @Override
    public void verseopen(String chapterno, String grouppostion) {

    }

    @Override
    public void indexopen(String chaptername, String chapterno) {

    }

    private void setfontsize(String malayalamFont) {
        final WebSettings webSettings = mChapterExplationWebView.getSettings();
        Resources res = getResources();

        if (malayalamFont.equalsIgnoreCase("0")) {
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            webSettings.setDefaultFontSize(9);
        } else if (malayalamFont.equalsIgnoreCase("1")) {
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            webSettings.setDefaultFontSize(10);
        } else if (malayalamFont.equalsIgnoreCase("2")) {
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            webSettings.setDefaultFontSize(12);
        } else if (malayalamFont.equalsIgnoreCase("3")) {
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            webSettings.setDefaultFontSize(14);
        } else if (malayalamFont.equalsIgnoreCase("4")) {
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            webSettings.setDefaultFontSize(16);
        } else if (malayalamFont.equalsIgnoreCase("5")) {
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            webSettings.setDefaultFontSize(18);

        } else if (malayalamFont.equalsIgnoreCase("6")) {
            mChapterNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            mChapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            webSettings.setDefaultFontSize(20);
        }
    }
}
