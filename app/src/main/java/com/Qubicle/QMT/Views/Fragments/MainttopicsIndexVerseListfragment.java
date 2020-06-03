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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.MainTopicAdapter;
import com.Qubicle.QMT.Adapter.MainindexVerseAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.MaintopicsVersefragmentController;
import com.Qubicle.QMT.Controllers.MaintopicsfragmentController;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class MainttopicsIndexVerseListfragment extends BaseFragment implements QuranIndexListener {
    WebView mWebview;
    TextView occurencetextview, mJuzTextView,foundtextview;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    MaintopicsVersefragmentController mMaintopiccontroller;
    Dialog mDialog;
    List<MaintopicIndex> maintopicIndexList;
    RecyclerView mMaintopicsRecycler;
    RecyclerView.LayoutManager layoutManager;
    MainTopicAdapter mainTopicAdapter;
    String malayalamFont, arabicfont;
    EditText searchEdittext;
    String flag = "0";

    String referenceid;
    private String page,keyword;

    public MainttopicsIndexVerseListfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_maintopics_verse, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mMaintopiccontroller = new MaintopicsVersefragmentController(MainttopicsIndexVerseListfragment.this);
        mMaintopicsRecycler = mFragmentView.findViewById(R.id.mMaintopicsRecycler);
        occurencetextview = mFragmentView.findViewById(R.id.occurencetextview);
        foundtextview = mFragmentView.findViewById(R.id.foundtextview);
        mMaintopicsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mMaintopicsRecycler.setLayoutManager(layoutManager);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            referenceid = bundle.getString("REFERENCE_ID");
            page = bundle.getString("PAGE");
            keyword = bundle.getString("KEYWORD");
        }


        if (isNetworkAvailable()) {
            progress();
            occurencetextview.setVisibility(View.GONE);
            foundtextview.setVisibility(View.GONE);
            if (page.equalsIgnoreCase("maintopic")) {
                Constants.INDEX_PAGE="main";
                mMaintopiccontroller.startfetching(referenceid, page);
            } else if (page.equalsIgnoreCase("alltopic")) {
                occurencetextview.setVisibility(View.GONE);
                foundtextview.setVisibility(View.GONE);
                Constants.INDEX_PAGE="all";
                mMaintopiccontroller.startfetching(referenceid, page);
            }

        } else {
            occurencetextview.setVisibility(View.GONE);
            foundtextview.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onResume() {
        super.onResume();

        if(page.equalsIgnoreCase("maintopic")){
            mOnFragmnetSwitch.loadTitle("TOPICS");
        }
        else if(page.equalsIgnoreCase("alltopic")){
            mOnFragmnetSwitch.loadTitle("ALL CHAPTERS");
        }

        OnMenuIconChange.iconchange(MainttopicsIndexVerseListfragment.this);
        HomeActivity.fragment = MainttopicsIndexVerseListfragment.this;
    }


    @Override
    public void fetchingsuccess(List<ChapterList> chapterlist) {

    }

    @Override
    public void parsemainindex(List<MaintopicIndex> maintopicIndexListt) {

    }

    @Override
    public void parsealltopicindex(List<Keyword> maintopicIndexList) {

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
    public void passdatatiIndex(String chapterno, String verseno) {


       mOnFragmnetSwitch.onFragmentChange(new VerseExplanationFragment(getActivity(), chapterno, verseno, malayalamFont), "VERSE EXPLANATION", true);




        //check size of arraylist

       /* if (!chaptername.equalsIgnoreCase("0")) {

            if (chaptername.equalsIgnoreCase("1")) {
               // mOnFragmnetSwitch.onFragmentChange(new VerseDetailfragment(getActivity(), chapterno, "", verseno), "CHAPTER", true);



            } else {
                Bundle bundle = new Bundle();

                bundle.putString("KEYORD_ID", chaptername);
                bundle.putString("PAGE", page);
                IndexVerseDetailsfragment fragment = new IndexVerseDetailsfragment();
                fragment.setArguments(bundle);
                mOnFragmnetSwitch.onFragmentChange(fragment, "VERSE", true);
            }

        }

*/
    }


    @Override
    public void passkeywordreference(String referenceid,String keyword) {


    }

    @Override
    public void parsemainindexverse(List<IndexVerseList> maintopicIndexList) {
        if (maintopicIndexList.size() != 0) {

            mDialog.dismiss();
            if (page.equalsIgnoreCase("maintopic")) {
                occurencetextview.setVisibility(View.VISIBLE);
                foundtextview.setVisibility(View.VISIBLE);
            } else if (page.equalsIgnoreCase("alltopic")) {
                occurencetextview.setVisibility(View.VISIBLE);
                foundtextview.setVisibility(View.GONE);

            }

            if(maintopicIndexList.size()==1){

               // occurencetextview.setText("' "+keyword+" '"+" - " +"found in " + maintopicIndexList.size() + " place");
                occurencetextview.setText(keyword);
                foundtextview.setText("found in " + maintopicIndexList.size() + " place");

            }
           else {

             //   occurencetextview.setText("' "+keyword+" '"+" - " +"found in " + maintopicIndexList.size() + " places");
                occurencetextview.setText(keyword);
                foundtextview.setText("found in " + maintopicIndexList.size() + " places");
            }

            MainindexVerseAdapter MainindexVerseAdapter = new MainindexVerseAdapter(maintopicIndexList, MainttopicsIndexVerseListfragment.this,page,keyword.trim());
            mMaintopicsRecycler.setAdapter(MainindexVerseAdapter);
        } else {
            mDialog.dismiss();
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
            occurencetextview.setVisibility(View.GONE);
            foundtextview.setVisibility(View.GONE);
        }
    }


}