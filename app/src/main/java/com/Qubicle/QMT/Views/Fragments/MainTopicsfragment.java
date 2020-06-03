package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.AlltopicsAdapter;
import com.Qubicle.QMT.Adapter.MainTopicAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.MaintopicsfragmentController;
import com.Qubicle.QMT.Controllers.ReadingFragmentController;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class MainTopicsfragment extends BaseFragment implements QuranIndexListener {
    WebView mWebview;
    TextView mSuraTextView, mJuzTextView;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    MaintopicsfragmentController mMaintopiccontroller;
    Dialog mDialog;
    List<MaintopicIndex> maintopicIndexList;
    RecyclerView mMaintopicsRecycler;
    RecyclerView.LayoutManager layoutManager;
    MainTopicAdapter mainTopicAdapter;
    String malayalamFont, arabicfont;
    EditText searchEdittext;
    String flag = "0";

    public MainTopicsfragment(Context context) {
        this.context = getActivity();

        //   setRetainInstance(true);
    }

    public static MainTopicsfragment newInstance(String title) {
        MainTopicsfragment fragment = new MainTopicsfragment();

        return fragment;
    }

    public MainTopicsfragment() {
        this.context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_maintopics, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {
        Constants.INDEX_PAGE = "main";
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mMaintopiccontroller = new MaintopicsfragmentController(MainTopicsfragment.this, getActivity());
        mMaintopicsRecycler = mFragmentView.findViewById(R.id.mMaintopicsRecycler);
        mMaintopicsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mMaintopicsRecycler.setLayoutManager(layoutManager);
        searchEdittext = mFragmentView.findViewById(R.id.searchEdittext);

        if (isNetworkAvailable()) {
            progress();
            mMaintopiccontroller.startfetching();
        } else {
            mDialog.dismiss();
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }


        searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("QURAN INDEX");
        OnMenuIconChange.iconchange(MainTopicsfragment.this);
        HomeActivity.fragment = MainTopicsfragment.this;
    }


    @Override
    public void fetchingsuccess(List<ChapterList> chapterlist) {

    }

    @Override
    public void parsemainindex(List<MaintopicIndex> maintopicIndexListt) {
        if (maintopicIndexListt != null) {
            mDialog.dismiss();
            maintopicIndexList = maintopicIndexListt;
            mainTopicAdapter = new MainTopicAdapter(MainTopicsfragment.this, getActivity(), maintopicIndexList, malayalamFont);
            mMaintopicsRecycler.setAdapter(mainTopicAdapter);

        }


    }

    @Override
    public void parsealltopicindex(List<Keyword> maintopicIndexList) {

    }

    @Override
    public void fetchingfailure() {

    }

    void filter(String text) {

        List<MaintopicIndex> temp = new ArrayList();
        for (MaintopicIndex maintopicIndex : maintopicIndexList) {
            flag = "0";
            List<Keyword> keywords = new ArrayList<>();

            if (maintopicIndex.getAlphabet().contains(text)) {
                temp.add(maintopicIndex);

            }
            else {

                for (int i = 0; i < maintopicIndex.getKeyword().size(); i++) {
                    if (maintopicIndex.getKeyword().get(i).getKeyword().contains(text)) {

                        Keyword keyword = new Keyword();
                        keyword.setId(maintopicIndex.getKeyword().get(i).getId());
                        keyword.setKeyword(maintopicIndex.getKeyword().get(i).getKeyword());
                        keywords.add(keyword);
                        flag = "1";
                    } else {

                    }
                }
                if (flag.equalsIgnoreCase("1")) {
                    MaintopicIndex index = new MaintopicIndex();
                    index.setAlphabet(maintopicIndex.getAlphabet());
                    index.setKeyword(keywords);
                    temp.add(index);
                }

            }

        }
        mainTopicAdapter.updateList(temp,text);


    }

    @Override
    public void progress() {
        if (mDialog == null) {
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }

    }

    @Override
    public void passdatatiIndex(String chaptername, String chapterno) {

    }


    @Override
    public void passkeywordreference(String referenceid, String keyword) {
        Bundle bundle = new Bundle();
        bundle.putString("REFERENCE_ID", referenceid);
        bundle.putString("PAGE", "maintopic");
        bundle.putString("KEYWORD", keyword);
        MainttopicsIndexVerseListfragment fragment = new MainttopicsIndexVerseListfragment();
        fragment.setArguments(bundle);
        mOnFragmnetSwitch.onFragmentChange(fragment, "QURAN INDEX", true);

    }

    @Override
    public void parsemainindexverse(List<IndexVerseList> maintopicIndexList) {

    }
}