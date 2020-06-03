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
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.RecitationChapterAdapter;
import com.Qubicle.QMT.Adapter.TajweedClassesTitlesAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.AlltopicsFragmentController;
import com.Qubicle.QMT.Controllers.TajweedClassesTitlesFragmentController;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Listeners.TajweedListner;
import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Models.TajweedClassAudio;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class TajweedClassesTitlesfragment extends BaseFragment implements TajweedListner {
    WebView mWebview;
    RecyclerView mAlltopicsRecycler, mJuzTextView;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TajweedClassesTitlesFragmentController tajweedClassesTitlesFragmentController;
    String page;
    TajweedClassesTitlesAdapter titlesAdapter;
    RecyclerView.LayoutManager layoutManager;
    String malayalamFont, arabicfont;
    EditText searchEdittext;
    List<Tajweed> tajweedList;
    String[] beforestring = {"ര്", "ന്", "ല്"};
    String[] afterstring = {"ർ", "ൻ", "ൽ"};
    Dialog mDialog;

    public TajweedClassesTitlesfragment(Context context) {
        this.context = context;

    }

    public TajweedClassesTitlesfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.alltopic_fragments, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mAlltopicsRecycler = mFragmentView.findViewById(R.id.mAlltopicsRecycler);
        searchEdittext = mFragmentView.findViewById(R.id.searchEdittext);
        mAlltopicsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mAlltopicsRecycler.setLayoutManager(layoutManager);
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        tajweedClassesTitlesFragmentController = new TajweedClassesTitlesFragmentController(TajweedClassesTitlesfragment.this, getActivity());
        if (isNetworkAvailable()) {
            progress();
            tajweedClassesTitlesFragmentController.startfetching();
        } else {
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
    void filter(String text) {
        List<Tajweed> temp = new ArrayList();
        for (Tajweed d : tajweedList) {
            if (d.getTitle().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        titlesAdapter.updateList(temp);
    }



    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("TAJWEED CLASSES");
        OnMenuIconChange.iconchange(TajweedClassesTitlesfragment.this);
        HomeActivity.fragment = TajweedClassesTitlesfragment.this;
    }


    @Override
    public void fetchingsuccess(List<Tajweed> TajweedTitles) {
        tajweedList = TajweedTitles;
        if (mDialog != null) {
            mDialog.dismiss();
        }
        titlesAdapter = new TajweedClassesTitlesAdapter(TajweedClassesTitlesfragment.this, getActivity(), tajweedList);
        mAlltopicsRecycler.setAdapter(titlesAdapter);


    }



    @Override
    public void fetchingfailure() {
        Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progress() {
        if (mDialog == null) {
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }
    }

    @Override
    public void passaudioparameter(String sortorder,String minSortOrder,String maxSortOrder) {
 Bundle bundle = new Bundle();
        bundle.putString("SORTORDER", sortorder);
        bundle.putString("MIN_SORTORDER", minSortOrder);
        bundle.putString("MAX_SORTORDER", maxSortOrder);
        TajweedClassesPlayerfragment fragment = new TajweedClassesPlayerfragment();
        fragment.setArguments(bundle);
        mOnFragmnetSwitch.onFragmentChange(fragment, "TAJWEED CLASSES AUDIO", true);
    }

    @Override
    public void dataTajweedClassesAudio(List<TajweedClassAudio> tajweedclasslist) {

    }




}