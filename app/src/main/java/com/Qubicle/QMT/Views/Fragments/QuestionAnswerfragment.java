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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Adapter.JuzExpandableListAdapter;
import com.Qubicle.QMT.Adapter.QAAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.QuestionAnswerFragmentController;
import com.Qubicle.QMT.Controllers.ReadingFragmentController;
import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class QuestionAnswerfragment extends BaseFragment implements QAListener {

    Context context;
    EditText searchEdittext;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    QuestionAnswerFragmentController questionAnswerFragmentController;
    String page, chapterno;
    private ExpandableListView mExpandableView;
    Dialog mDialog;
    QAAdapter qaAdapter;

    List<QA> qaList = new ArrayList<>();

    public QuestionAnswerfragment(Context context, String chapterno) {
        this.context = context;
        this.chapterno = chapterno;
    }

    public QuestionAnswerfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_qa, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        questionAnswerFragmentController = new QuestionAnswerFragmentController(getActivity(), QuestionAnswerfragment.this, QuestionAnswerfragment.this);
        configviews(mFragmentView);


       /* for(int i=0;i<5;i++){
            QA qa=new QA();
            qa.setId(String.valueOf(i));
            qa.setQuestion("how many verse"+i+i+" in chapter "+i+"\n"+"how many verse"+i+i+" in chapter ");
            qa.setAnswer("7");
            qaList.add(qa);
        }
        fetchingsuccess(qaList);*/
        if (isNetworkAvailable()) {
            progress();
            questionAnswerFragmentController.startfetching(chapterno);
        }
    }

    private void configviews(View mFragmentView) {
        searchEdittext = mFragmentView.findViewById(R.id.searchEdittext);
        mExpandableView = mFragmentView.findViewById(R.id.mExpandableView);
        mExpandableView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
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
        List<QA> temp = new ArrayList();
        for (QA d : qaList) {
            if (d.getQuestion().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        qaAdapter.updateList(temp, text);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("QUESTION AND ANSWER");
        OnMenuIconChange.iconchange(QuestionAnswerfragment.this);
        HomeActivity.fragment = QuestionAnswerfragment.this;
    }


    @Override
    public void fetchingsuccess(List<QA> qaListt) {
        if (mDialog != null) {
            mDialog.dismiss();
        }

        qaList=qaListt;
        if (qaList.size() == 0) {
            Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_LONG).show();
        } else {
            qaAdapter = new QAAdapter(QuestionAnswerfragment.this, getActivity(), qaList);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mExpandableView.setAdapter(qaAdapter);
                }
            });
        }

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
    public void reference(String chapterno, String verseno) {
        mOnFragmnetSwitch.onFragmentChange(new VerseDetailfragment(getActivity(),chapterno,"",verseno),"CHAPTER",true);


    }
}