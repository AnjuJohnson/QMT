package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.AllTopicIndexAdapter;
import com.Qubicle.QMT.Adapter.MainTopicAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.AlltopicsIndexfragmentController;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class AlltopicsIndexfragment extends BaseFragment implements QuranIndexListener {
    RecyclerView mAlltopicsRecycler;
    TextView mChapternameTextview,mJuzTextView;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    String chaptername,chapterno,page;
    EditText searchEdittext;
    AlltopicsIndexfragmentController mMaintopiccontroller;
    List<Keyword> maintopicIndexList;
    MainTopicAdapter mainTopicAdapter;
    AllTopicIndexAdapter allTopicAdapter;
    Dialog mDialog;
    RelativeLayout searchlayout;
    CardView cardView;
    public AlltopicsIndexfragment(Context context,String chaptername,String chapterno,String page) {
        this.context = context;
        this.chaptername = chaptername;
        this.chapterno = chapterno;
        this.page = page;

     //   setRetainInstance(true);
    }

    public AlltopicsIndexfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.alltopic_index_fragments, container, false);
          mainfunction(mFragmentView,savedInstanceState);
        return mFragmentView;

    }
    public void mainfunction(View mFragmentView,Bundle savedInstanceState) {
        Constants.INDEX_PAGE="all";
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        searchEdittext=mFragmentView.findViewById(R.id.searchEdittext);
        cardView=mFragmentView.findViewById(R.id.cardView);
        mChapternameTextview=mFragmentView.findViewById(R.id.mChapternameTextview);
        mAlltopicsRecycler=mFragmentView.findViewById(R.id.mAlltopicsRecycler);
        mChapternameTextview.setText(chaptername);
        mAlltopicsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mAlltopicsRecycler.setLayoutManager(layoutManager);
        mMaintopiccontroller = new AlltopicsIndexfragmentController(AlltopicsIndexfragment.this, getActivity());
        cardView.setVisibility(View.GONE);

        if (isNetworkAvailable()) {
            progress();
            mMaintopiccontroller.startfetching(chapterno);
        }
        else {
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

    void filter(String text){
        List<Keyword> temp = new ArrayList();
        for(Keyword d:  maintopicIndexList){
            if(d.getKeyword().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        allTopicAdapter.updateList(temp,text);
    }




    @Override
    public void onResume() {
        super.onResume();
        if(page.equalsIgnoreCase("sura")){
            mOnFragmnetSwitch.loadTitle("ഉള്ളടക്കം");
        }
        else if(page.equalsIgnoreCase("index")){
            mOnFragmnetSwitch.loadTitle("ALL CHAPTERS");
        }

        OnMenuIconChange.iconchange(AlltopicsIndexfragment.this);
        HomeActivity.fragment = AlltopicsIndexfragment.this;
    }


    @Override
    public void fetchingsuccess(List<ChapterList> chapterlist) {

    }

    @Override
    public void parsemainindex(List<MaintopicIndex> maintopicIndexListt) {

    }

    @Override
    public void parsealltopicindex(List<Keyword> maintopicIndexListt) {
        if(maintopicIndexListt!=null){

            mDialog.dismiss();
            maintopicIndexList = maintopicIndexListt;
            allTopicAdapter = new AllTopicIndexAdapter(AlltopicsIndexfragment.this, getActivity(), maintopicIndexList,"");
            mAlltopicsRecycler.setAdapter(allTopicAdapter);
        }

    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {
        if (mDialog == null) {
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }
    }

    @Override
    public void passdatatiIndex(String chaptername,String chapterno) {

    }

    @Override
    public void passkeywordreference(String referenceid,String keyword) {
        Bundle bundle = new Bundle();
        bundle.putString("REFERENCE_ID", referenceid);
        bundle.putString("PAGE","alltopic");
        bundle.putString("KEYWORD",keyword);
        MainttopicsIndexVerseListfragment fragment=new MainttopicsIndexVerseListfragment();
        fragment.setArguments(bundle);
        mOnFragmnetSwitch.onFragmentChange(fragment, "", true);
    }

    @Override
    public void parsemainindexverse(List<IndexVerseList> maintopicIndexList) {

    }
}