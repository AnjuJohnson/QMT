package com.Qubicle.QMT.Views.Fragments;

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
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.RecitationChapterAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.AlltopicsFragmentController;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class DirectTransaltionfragment extends BaseFragment implements QuranIndexListener {
    WebView mWebview;
    RecyclerView mAlltopicsRecycler,mJuzTextView;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    AlltopicsFragmentController mAlltopicController;
    String page;
    RecitationChapterAdapter alltopicsAdapter;
    RecyclerView.LayoutManager layoutManager;
    String malayalamFont, arabicfont;
EditText searchEdittext;
    List<ChapterList> chapterLists;
    String[] beforestring={"ര്","ന്","ല്"};
    String[] afterstring={"ർ","ൻ","ൽ"};
    public DirectTransaltionfragment(Context context) {
        this.context = context;

     //   setRetainInstance(true);
    }

    public DirectTransaltionfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.alltopic_fragments, container, false);
          mainfunction(mFragmentView,savedInstanceState);
        return mFragmentView;

    }
    public void mainfunction(View mFragmentView,Bundle savedInstanceState) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mAlltopicsRecycler=mFragmentView.findViewById(R.id.mAlltopicsRecycler);
        searchEdittext=mFragmentView.findViewById(R.id.searchEdittext);
        mAlltopicsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mAlltopicsRecycler.setLayoutManager(layoutManager);
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        mAlltopicController = new AlltopicsFragmentController(DirectTransaltionfragment.this, getActivity());
       chapterLists = mAlltopicController.checkdatapresent();
        if (chapterLists == null || chapterLists.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
        }
        else {
            parsedata(chapterLists);
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

                String filtered = editable.toString();


                for(int i=0;i<beforestring.length;i++){
                    if(editable.toString().contains(beforestring[i])){
                        filtered=  editable.toString().replaceAll(beforestring[i],afterstring[i]);
                    }

                }

                filter(filtered);
            }
        });


    }
    void filter(String text){
        List<ChapterList> temp = new ArrayList();
        for(ChapterList d:  chapterLists){
            if(d.getChapter_name().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        alltopicsAdapter.updateList(temp);
    }
    public void parsedata(List<ChapterList> chapterLists) {


        alltopicsAdapter = new RecitationChapterAdapter(DirectTransaltionfragment.this, getActivity(), chapterLists, malayalamFont);
        mAlltopicsRecycler.setAdapter(alltopicsAdapter);


    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("RECITATION");
        OnMenuIconChange.iconchange(DirectTransaltionfragment.this);
        HomeActivity.fragment = DirectTransaltionfragment.this;
    }


    @Override
    public void fetchingsuccess(List<ChapterList> chapterlist) {

    }

    @Override
    public void parsemainindex(List<MaintopicIndex> maintopicIndexList) {

    }

    @Override
    public void parsealltopicindex(List<Keyword> maintopicIndexList) {

    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {

    }

    @Override
    public void passdatatiIndex(String chaptername, String chapterno) {
        Bundle bundle = new Bundle();
        bundle.putString("CHAPTERNAME", chaptername);
        bundle.putString("CHAPTERNO", chapterno);
        bundle.putString("CHAPTERMEANING", "");
        bundle.putString("VERSENO", "");
        bundle.putString("PAGE", page);

        AmaniPlayerfragment fragment = new AmaniPlayerfragment();
        fragment.setArguments(bundle);


        mOnFragmnetSwitch.onFragmentChange(fragment,"DIRECT TRANSLATION",true);

    }



    @Override
    public void passkeywordreference(String referenceid,String keyword) {

    }

    @Override
    public void parsemainindexverse(List<IndexVerseList> maintopicIndexList) {

    }
}