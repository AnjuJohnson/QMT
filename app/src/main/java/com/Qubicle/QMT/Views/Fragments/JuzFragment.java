package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.JuzExpandableListAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.JuzFragmentController;
import com.Qubicle.QMT.Listeners.JuzListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;

/**
 * Created by Anju on 22-08-2019.
 */
public class JuzFragment extends BaseFragment implements JuzListener {

    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    RecyclerView mJuzRecyclerView;
    public JuzFragmentController juzFragmentController;
    Dialog mDialog;
    JuzExpandableListAdapter juzAdapter;
    private ExpandableListView mExpandableView;
    public JuzFragment() {

    }

    public static JuzFragment newInstance(String title) {
        JuzFragment fragment = new JuzFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_juz, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }
    public void mainfunction(View mFragmentView) {



        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        juzFragmentController = new JuzFragmentController(JuzFragment.this, getActivity());
        configviews(mFragmentView);
        if (isNetworkAvailable()) {
            progress();
            juzFragmentController.startfetching();
        } else {

            List<JuzList> juzLists = juzFragmentController.checkdatapresent();
            if (juzLists == null || juzLists.isEmpty()) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            }
            else {
                datapassing(juzLists);
            }
        }



/*

        if (isNetworkAvailable()) {
            progress();
            juzFragmentController. startfetching();
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
*/




    }
    private void configviews(View mFragmentView){

        mExpandableView =mFragmentView. findViewById(R.id.mExpandableView);
     /*   onclick(mExpandableView);
        mExpandableView.setChildIndicator(null);
        mExpandableView.setChildDivider(getResources().getDrawable(R.color.transparent));
        mExpandableView.setDivider(getResources().getDrawable(R.color.light_grey_line));
        mExpandableView.setDividerHeight(2);*/




        mExpandableView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    mExpandableView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        mExpandableView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        mExpandableView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("READING");
        OnMenuIconChange.iconchange(JuzFragment.this);
     //   HomeActivity.fragment = JuzFragment.this;
    }

    @Override
    public void fetchingsuccess() {

    }

    @Override
    public void fetchingfailure() {
        List<JuzList> juzLists = juzFragmentController.checkdatapresent();
        if (juzLists == null || juzLists.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
        }
        else {
            datapassing(juzLists);
        }
        mDialog.dismiss();
    }

    @Override
    public void progress() {
        mDialog = Utility.showProgressBar(getActivity());
        mDialog.show();
    }

    @Override
    public void datapassing(List<JuzList> juzList) {

        if(mDialog!=null){
            mDialog.dismiss();
        }


        juzAdapter = new JuzExpandableListAdapter(JuzFragment.this,getActivity(),juzList);
        getActivity(). runOnUiThread(new Runnable() {
            public void run() {
                mExpandableView.setAdapter(juzAdapter);
            }
        });

        //chech if there is juz data


        if (isNetworkAvailable()) {
          //  progress();
            //check if there is any data
            List<JuzDetailNew> juzDetailNewList=juzFragmentController.checkdatapresentAlljuz();

            if (juzDetailNewList.size() != 0&&juzDetailNewList!=null) {

                int delete = juzFragmentController.deletealljuzdetails();
                if (delete == 1) {
                    juzFragmentController.startfetchingJuzDetail();
                }
            }
            else {
                juzFragmentController.startfetchingJuzDetail();
            }

        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT);
        }


    }

    @Override
    public void fetchjuzdetails(String juzno) {
        String number = juzno.replaceAll("\\D+","");
        System.out.println("juzno "+number);
        mOnFragmnetSwitch.onFragmentChange(new JuzDetailfragment(getActivity(),number,"default","",""),"JUZ",true);
    }

    @Override
    public void datapassingJuzDetail(List<JuzDetailNew> juzDetailsList) {

    }

    @Override
    public void verse_explanation(String chapterid, String ayatno) {

    }

    @Override
    public void addtofavourites(String chaptername, String chapterno, String ayatno, String verse, String translation) {

    }
}
