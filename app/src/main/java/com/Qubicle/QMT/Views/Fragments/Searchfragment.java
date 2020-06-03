package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.SearchAdapter;
import com.Qubicle.QMT.Adapter.VerseDetailAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.SearchFragmentController;
import com.Qubicle.QMT.Listeners.SearchListner;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class Searchfragment extends BaseFragment implements SearchListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    ImageView mSearchImageView;
    EditText mMailIdEditTextView, searchEdittext;
    SearchFragmentController searchFragmentController;
    AppCompatSpinner mchapterSpinner, mVerseSpinner;
    RecyclerView mSearchRecycler;
    List<ChapterList> chapterLists;
    ArrayAdapter<String> chapteradapter, verseadapter;
    String[] chapternames;
    String[] versenames;
    String chapterno, schaptername = "", sverseno = "", stranslation, sword = "";
    String flag = "0";
    Dialog mDialog;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter SearchAdapter;
    TextView nodatatextview;
    List<VerseDetail> verseDetails=new ArrayList<>();
    public static String malayalamFont, arabicfont, sTranslationNumber;

    public Searchfragment(Context context) {
        this.context = context;

    }

    public Searchfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        searchEdittext = mFragmentView.findViewById(R.id.searchEdittext);
        mSearchImageView = mFragmentView.findViewById(R.id.mSearchImageView);
        mSearchRecycler = mFragmentView.findViewById(R.id.mSearchRecycler);
        mchapterSpinner = mFragmentView.findViewById(R.id.mchapterSpinner);
        mVerseSpinner = mFragmentView.findViewById(R.id.mVerseSpinner);
        nodatatextview = mFragmentView.findViewById(R.id.nodatatextview);
        searchFragmentController = new SearchFragmentController(getActivity(), Searchfragment.this);
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        arabicfont = PreferenceManager.getManager().getArabicFont();
        sTranslationNumber = PreferenceManager.getManager().getTranslation();


        mSearchRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mSearchRecycler.setLayoutManager(layoutManager);

//load chapterlist

        chapterLists = searchFragmentController.checkdatapresent();
        if (chapterLists == null || chapterLists.isEmpty()) {
            //  Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
        } else {
            setchapter(chapterLists);
        }

        versenames = new String[1];
        versenames[0] = "Verse";
        setverseadapter();

        mchapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //
                flag = "0";
                if (chapternames[i].contains("Chapter")) {
                    flag = "0";
                    System.out.println(flag + "flag");
                    schaptername = "";
                    versenames = new String[1];
                    versenames[0] = "Verse";
                    setverseadapter();
                } else {
                    for (ChapterList chapterList : chapterLists) {
                        if (chapterList.getChapter_name().contains(chapternames[i].trim())) {
                            chapterno = chapterList.getSurah_no();
                            flag = "1";
                            System.out.println(flag + "flag");
                        }

                    }

                    if (flag.equalsIgnoreCase("1")) {
                        schaptername = chapterno;
                        setverse(chapterno);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mVerseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (versenames[i].contains("Verse")) {
                    sverseno = "";

                } else {

                    sverseno = versenames[i];

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSearchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sword = searchEdittext.getText().toString().trim();
                if ((schaptername.isEmpty()) && (sverseno.isEmpty()) && (sword.isEmpty())) {
                //    Toast.makeText(getActivity(), "Select any fields", Toast.LENGTH_SHORT).show();
                    verseDetails=new ArrayList<>();
                    dataSearchpassing(verseDetails);



                } else {
                    if (sTranslationNumber.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
                        stranslation = "meaning_malayalam";
                    } else if (sTranslationNumber.equalsIgnoreCase("Amani Thafseer")) {
                        stranslation = "meaning_malayalam_new";
                    }
                    if (isNetworkAvailable()) {
                        progress();
                        searchFragmentController.startfetching(schaptername, sverseno, sword, stranslation);
                    } else {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    private void setverse(String chapterno) {
        for (ChapterList chapterList : chapterLists) {
            if (chapterList.getSurah_no().equalsIgnoreCase(chapterno)) {
                versenames = new String[chapterList.getAyat().size() + 1];

                versenames[0] = "Verse";
                for (int i = 0; i < chapterList.getAyat().size(); i++) {
                    versenames[i + 1] = chapterList.getAyat().get(i).getAyat_no();
                }

                setverseadapter();
            }

        }


    }

    private void setverseadapter() {

        verseadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, versenames);
        verseadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mVerseSpinner.setAdapter(verseadapter);

    }

    private void setchapter(List<ChapterList> chapterLists) {
        chapternames = new String[chapterLists.size() + 1];
        chapternames[0] = "Chapter";
        for (int i = 0; i < chapterLists.size(); i++) {

            chapternames[i + 1] = chapterLists.get(i).getChapter_name() + "  ";
        }

        chapteradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, chapternames);
        chapteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mchapterSpinner.setAdapter(chapteradapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("SEARCH");
        OnMenuIconChange.iconchange(Searchfragment.this);
        HomeActivity.fragment = Searchfragment.this;
    }


    @Override
    public void fetchingfailure() {
        mDialog.dismiss();
    }

    @Override
    public void progress() {
        mDialog = Utility.showProgressBar(getActivity());
        mDialog.show();
    }

    @Override
    public void dataSearchpassing(List<VerseDetail> verseDetailsS) {
        verseDetails=verseDetailsS;

        if(mDialog!=null){
            if(verseDetails.size()!=0){
                mDialog.dismiss();
                mSearchRecycler.setVisibility(View.VISIBLE);
                nodatatextview.setVisibility(View.GONE);
                SearchAdapter = new SearchAdapter(Searchfragment.this, verseDetails, getActivity(),
                        malayalamFont, arabicfont,sword);
                mSearchRecycler.setAdapter(SearchAdapter);
            }
            else {
                mSearchRecycler.setVisibility(View.GONE);
                nodatatextview.setVisibility(View.VISIBLE);
                mDialog.dismiss();
            }
        }
else {
            mSearchRecycler.setVisibility(View.GONE);
            nodatatextview.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public void openversedetail(String chapterno, String verseno) {
        mOnFragmnetSwitch.onFragmentChange(new VerseDetailfragment(getActivity(),chapterno,"",verseno),"CHAPTER",true);

    }


}