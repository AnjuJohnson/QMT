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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.VerseExplanationController;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

/**
 * Created by Anju on 27-08-2019.
 */
public class DashBoardFragment extends BaseFragment implements View.OnClickListener {
    Context context;
    Dialog mDialog;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
 RelativeLayout reading,bookmark,audio,tafseer,tajweed,memorization;
    public DashBoardFragment(Context context) {

        this.context = context;

      //  setRetainInstance(true);
    }

    public DashBoardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.dashboard, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        configviews(mFragmentView);


    }
private void configviews(View FragmentView){
    Constants.INDEX_PAGE="all";
    Constants.SEARCH_ON=false;
    PreferenceManager.getManager().setReadingPage("surapage");
    OnMenuIconChange = (Utility.menuIconChange) getActivity();
    mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
    reading=FragmentView.findViewById(R.id.reading);
    bookmark=FragmentView.findViewById(R.id.bookmark);
    audio=FragmentView.findViewById(R.id.audio);
    tajweed=FragmentView.findViewById(R.id.tajweed);
    memorization=FragmentView.findViewById(R.id.memorization);
    bookmark.setOnClickListener(this);
    reading.setOnClickListener(this);
    audio.setOnClickListener(this);
    tajweed.setOnClickListener(this);
   memorization.setOnClickListener(this);

    //tajweed.setVisibility(View.GONE);
}
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("");
        OnMenuIconChange.iconchange(DashBoardFragment.this);
        HomeActivity.fragment = DashBoardFragment.this;
    }


    @Override
    public void onClick(View view) {
        if(view == reading) {
            mOnFragmnetSwitch.onFragmentChange(new Readingfragment(getActivity()),"",true);
           // ChangeFragment(new Readingfragment(HomeActivity.this, "surapage"), CURRENT_TAG, true);
        }
        else if(view==tajweed){
            mOnFragmnetSwitch.onFragmentChange(new Tajweedfragment(getActivity()),"TAJWEED",true);
        }
        else if(view==bookmark){
            mOnFragmnetSwitch.onFragmentChange(new Bookmarksfragment(getActivity()),"BOOKMARKS",true);
        } else if(view==audio){
            mOnFragmnetSwitch.onFragmentChange(new AudioMainfragment(getActivity()),"AUDIO",true);
        } else if(view==memorization){
            mOnFragmnetSwitch.onFragmentChange(new MemorizationMainfragment(getActivity()),"MEMORIZATION",true);
        }
    }
}
