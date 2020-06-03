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
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.Qubicle.QMT.Adapter.AmaniExpandableListAdapter;
import com.Qubicle.QMT.Adapter.ExpandableListAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.SuraFragmentController;
import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.AnimatedExpandableListView;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anju on 22-08-2019.
 */
public class AmaniAudioFragment extends BaseFragment implements SuraListener {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    Dialog mDialog;
    AmaniExpandableListAdapter expandableListAdapter;
    private AnimatedExpandableListView mExpandableView;
    public SuraFragmentController suraFragmentController;
    String malayalamFont, arabicfont,page;
    EditText searchEdittext;
    String[] beforestring={"ര്","ന്","ല്"};
    String[] afterstring={"ർ","ൻ","ൽ"};
    List<ChapterList> chapterLists;
    public AmaniAudioFragment(Context context,String page) {
        this.context = context;
        this.page = page;


    }

    public AmaniAudioFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_amanitafseer, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        suraFragmentController = new SuraFragmentController(AmaniAudioFragment.this, getActivity());
        configviews(mFragmentView);

        //check whether is there any data in table
        //if not call webservice else get it from table

        if (isNetworkAvailable()) {
            progress();
            suraFragmentController.startfetching();
        } else {

         chapterLists = suraFragmentController.checkdatapresent();
            if (chapterLists == null || chapterLists.isEmpty()) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            }
            else {
                suraFragmentController.parsedata(chapterLists);
            }
        }




      /*  List<ChapterList> chapterLists = suraFragmentController.checkdatapresent();
        if (chapterLists == null || chapterLists.isEmpty()) {
            if (isNetworkAvailable()) {
                progress();
                suraFragmentController.startfetching();
            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        } else {
            suraFragmentController.parsedata(chapterLists);
        }*/

    }

    private void configviews(View mFragmentView) {
        mExpandableView = mFragmentView.findViewById(R.id.mExpandableView);
        onclick(mExpandableView);
        mExpandableView.setChildIndicator(null);
        mExpandableView.setChildDivider(getResources().getDrawable(R.color.transparent));
        mExpandableView.setDivider(getResources().getDrawable(R.color.light_grey_line));
        mExpandableView.setDividerHeight(2);

        malayalamFont = PreferenceManager.getManager().getMalayalamFont();


        arabicfont = PreferenceManager.getManager().getArabicFont();
        searchEdittext=mFragmentView.findViewById(R.id.searchEdittext);

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

            if(!d.getSurah_no().equalsIgnoreCase("0")){
                if(d.getChapter_name().contains(text)){
                    temp.add(d);
                }
            }

        }
        //update recyclerview
        expandableListAdapter.updateList(temp);
    }
    public void onclick(final AnimatedExpandableListView ExpandListView) {
        ExpandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (ExpandListView.isGroupExpanded(groupPosition)) {

                    ExpandListView.collapseGroup(groupPosition);

                    //  ExpandListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    ExpandListView.expandGroup(groupPosition);
                    //   ExpandListView.expandGroupWithAnimation(groupPosition);
                }


             //mugavura

              /*  if (groupPosition == 0) {

                } else {
                    if (ExpandListView.isGroupExpanded(groupPosition)) {

                        ExpandListView.collapseGroup(groupPosition);

                      //  ExpandListView.collapseGroupWithAnimation(groupPosition);
                    } else {
                        ExpandListView.expandGroup(groupPosition);
                     //   ExpandListView.expandGroupWithAnimation(groupPosition);
                    }
                }*/


                return true;
            }

        });


        ExpandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    ExpandListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("AMANI TAFSEER");
        OnMenuIconChange.iconchange(AmaniAudioFragment.this);
        HomeActivity.fragment = AmaniAudioFragment.this;
    }

    @Override
    public void fetchingsuccess() {
        mDialog.dismiss();
        // Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void fetchingfailure() {
      chapterLists = suraFragmentController.checkdatapresent();
        if (chapterLists == null || chapterLists.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
        }
        else {
            suraFragmentController.parsedata(chapterLists);
        }


       // Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
        mDialog.dismiss();
    }

    @Override
    public void progress() {
        System.out.println("1111");

        if(mDialog==null){
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }


    }

    @Override
    public void datapassing(List<ChapterList> chapterListss) {

        if(mDialog!=null){
            mDialog.dismiss();
        }

        chapterLists=chapterListss;
        expandableListAdapter = new AmaniExpandableListAdapter(AmaniAudioFragment.this, getActivity(), chapterLists, malayalamFont, arabicfont);
        //   expandableListAdapter.setData(chapterLists);


        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // setting list adapter
                mExpandableView.setAdapter(expandableListAdapter);
            }
        });
    }

    @Override
    public void chapter_explanation_listener(String chapterid) {
        //  Toast.makeText(getActivity(), "chapterid "+chapterid, Toast.LENGTH_LONG).show();


    }

    @Override
    public void verse_detail(String chaptername, String chaptermeaning, String verseno) {



    }

    @Override
    public void datapassingChapterExplanation(ChapterExplanation chapterExplanation) {

    }

    @Override
    public void amaniaudioParameter(String chapterno, String chaptername, String chaptermeaning, String verseno) {
        Bundle bundle = new Bundle();
        bundle.putString("CHAPTERNAME", chaptername);
        bundle.putString("CHAPTERNO", chapterno);
        bundle.putString("CHAPTERMEANING", chaptermeaning);
        bundle.putString("VERSENO", verseno);
        //  bundle.putString("CHAPTERMEANING", chaptername);
        bundle.putString("PAGE", page);

        AmaniPlayerfragment fragment = new AmaniPlayerfragment();
        fragment.setArguments(bundle);


        mOnFragmnetSwitch.onFragmentChange(fragment,"AMANI TAFSEER",true);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        suraFragmentController.destroy();
    }

}
