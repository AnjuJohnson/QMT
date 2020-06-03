package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.Qubicle.QMT.Adapter.ExpandableListAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.SuraFragmentController;
import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anju on 22-08-2019.
 */
public class SuraFragment extends BaseFragment implements SuraListener {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    Dialog mDialog;
    ExpandableListAdapter expandableListAdapter;
    private ExpandableListView mExpandableView;
    public SuraFragmentController suraFragmentController;
    String malayalamFont, arabicfont;
    Boolean isopen = false;
    List<ChapterList> chapterListsss;
    int previousGroup = -1;
    public SuraFragment(Context context) {
        this.context = context;


    }
    public static SuraFragment newInstance(String title) {
        SuraFragment fragment = new SuraFragment();

        return fragment;
    }
    public SuraFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_sura, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        PreferenceManager.getManager().setReadingPage("surapage");
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        suraFragmentController = new SuraFragmentController(SuraFragment.this, getActivity());
        configviews(mFragmentView);

        //check whether is there any data in table
        //if not call webservice else get it from table

        if (isNetworkAvailable()) {
            progress();
            suraFragmentController.startfetching();
        } else {

            List<ChapterList> chapterLists = suraFragmentController.checkdatapresent();
            if (chapterLists == null || chapterLists.isEmpty()) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            } else {
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

    }
    public void onclick(final ExpandableListView ExpandListView) {
        ExpandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                System.out.println("grup"+groupPosition);
              /*  if (ExpandListView.isGroupExpanded(groupPosition)) {

                    ExpandListView.collapseGroup(groupPosition);

                } else {
                    ExpandListView.expandGroup(groupPosition);

                }*/



//handling using view

/*

                if (v != null) {
                    ImageView chapterqa = (ImageView) v.findViewById(R.id.imageView8);
                    TextView chapterarabictitle = (TextView) v.findViewById(R.id.arabic);


                    chapterarabictitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           */
/* if (isopen) {

                                if(previousGroup!=groupPosition){
                                    ExpandListView.collapseGroup(previousGroup);
                                    ExpandListView.expandGroup(groupPosition);
                                }
                                ExpandListView.collapseGroup(groupPosition);


                                isopen = false;
                            } else {

                                ExpandListView.expandGroup(groupPosition);

                                isopen = true;
                                previousGroup = groupPosition;
                            }
*//*


                            System.out.println("grup"+groupPosition);
                            if (ExpandListView.isGroupExpanded(groupPosition)) {

                                ExpandListView.collapseGroup(groupPosition);

                            } else {
                                ExpandListView.expandGroup(groupPosition);

                            }

                            //  ExpandListView.expandGroup(groupPosition);
                        }
                    });

                }
*/


                return true;
            }

        });


        ExpandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                System.out.println("grup"+groupPosition);
                if (groupPosition != previousGroup)
                    ExpandListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("READING");
        OnMenuIconChange.iconchange(SuraFragment.this);
        //HomeActivity.fragment = SuraFragment.this;
    }

    @Override
    public void fetchingsuccess() {
        mDialog.dismiss();
        // Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void fetchingfailure() {
        List<ChapterList> chapterLists = suraFragmentController.checkdatapresent();
        if (chapterLists == null || chapterLists.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
        } else {
            suraFragmentController.parsedata(chapterLists);
        }


        // Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
        mDialog.dismiss();
    }

    @Override
    public void progress() {
        System.out.println("1111");

        if (mDialog == null) {
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

    }

    @Override
    public void datapassing(List<ChapterList> chapterLists) {

        if (mDialog != null) {
            mDialog.dismiss();
        }

      //  chapterListsss=new ArrayList<>();
        chapterListsss=chapterLists;
        expandableListAdapter = new ExpandableListAdapter(SuraFragment.this, getActivity(), chapterListsss, malayalamFont, arabicfont);
        //   expandableListAdapter.setData(chapterLists);


        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // setting list adapter
                //if(mExpandableView.getAdapter() == null)
                mExpandableView.setAdapter(expandableListAdapter);
            }
        });
    }

    @Override
    public void chapter_explanation_listener(String chapterid) {
        //  Toast.makeText(getActivity(), "chapterid "+chapterid, Toast.LENGTH_LONG).show();

        mOnFragmnetSwitch.onFragmentChange(new ChapterExplanationFragment(getActivity(), chapterid, malayalamFont), "CHAPTER EXPLANATION", true);
    }

    @Override
    public void verse_detail(String chapterid, String ayatid, String ayatno) {


        if (chapterid.equalsIgnoreCase("0")) {
            mOnFragmnetSwitch.onFragmentChange(new VerseExplanationFragment(getActivity(), chapterid, ayatid, malayalamFont), "VERSE EXPLANATION", true);


        } else {
            Constants.VERSEDETAILPAGE_BACKPRESS="reading_page";
            mOnFragmnetSwitch.onFragmentChange(new VerseDetailfragment(getActivity(), chapterid, ayatid, ayatno), "CHAPTER", true);

        }
    }

    @Override
    public void datapassingChapterExplanation(ChapterExplanation chapterExplanation) {

    }

    @Override
    public void amaniaudioParameter(String chapterno, String chaptername, String chaptermeaning, String verseno) {

    }

    @Override
    public void chapterqa(String chapterno) {
        mOnFragmnetSwitch.onFragmentChange(new QuestionAnswerfragment(getActivity(), chapterno), "QUESTION AND ANSWER", true);
    }

    @Override
    public void optionOn(String chapterno, String grouppostion) {
        for (ChapterList v2 : chapterListsss) {
            if (chapterno.equalsIgnoreCase(v2.getSurah_no())) {
                if (v2.getOptionsOn()) {
                    v2.setOptionsOn(false);
                    if (mExpandableView.isGroupExpanded(Integer.parseInt(grouppostion))) {

                        mExpandableView.collapseGroup(Integer.parseInt(grouppostion));

                    } else {
                     //   mExpandableView.expandGroup(Integer.parseInt(grouppostion));

                    }


                } else {
                    v2.setOptionsOn(true);

                }

            } else {
                //  v2.setWordmeaning(false);
            }
        }

        expandableListAdapter.optionUpdate(chapterListsss);
        expandableListAdapter.notifyDataSetChanged();
    }

    @Override
    public void verseopen(String chapterno, String grouppostion) {
        System.out.println("grup"+grouppostion);
        if (mExpandableView.isGroupExpanded(Integer.parseInt(grouppostion))) {

            mExpandableView.collapseGroup(Integer.parseInt(grouppostion));

        } else {
            mExpandableView.expandGroup(Integer.parseInt(grouppostion));

        }
    }

    @Override
    public void indexopen(String chaptername, String chapterno) {
        Constants.CHAPTERNAME=chaptername;
        Constants.ALLCHAPTERPAGE="sura";
        mOnFragmnetSwitch.onFragmentChange(new AlltopicsIndexfragment(getActivity(),chaptername,chapterno,"sura"),"ALL CHAPTERS",true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        suraFragmentController.destroy();
    }

}
