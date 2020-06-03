package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.MaintopicsfragmentController;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;


public class AudioMainfragment extends BaseFragment implements View.OnClickListener  {
    RecyclerView mAlltopicsRecycler;
    TextView mChapternameTextview,mJuzTextView;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    String chaptername;
    EditText searchEdittext;
    MaintopicsfragmentController mMaintopiccontroller;
    CardView mcardviewRecitation,mcardviewDirect,mcardviewAmani;
    public AudioMainfragment(Context context ) {
        this.context = context;


    }

    public AudioMainfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.audio_main_dashboard, container, false);
          mainfunction(mFragmentView,savedInstanceState);
        return mFragmentView;

    }
    public void mainfunction(View mFragmentView,Bundle savedInstanceState) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();

        mcardviewRecitation=mFragmentView.findViewById(R.id.cardView);
        mcardviewDirect=mFragmentView.findViewById(R.id.cardView2);
        mcardviewAmani=mFragmentView.findViewById(R.id.cardView3);
        mcardviewRecitation.setOnClickListener(this);
        mcardviewDirect.setOnClickListener(this);
        mcardviewAmani.setOnClickListener(this);


    }
    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("AUDIO");
        OnMenuIconChange.iconchange(AudioMainfragment.this);
        HomeActivity.fragment = AudioMainfragment.this;
    }

    @Override
    public void onClick(View view) {
        if(view == mcardviewRecitation) {
            mOnFragmnetSwitch.onFragmentChange(new ReciterChapterfragment(getActivity(),"RECITATION"),"RECITATION",true);

        }
        else if(view==mcardviewDirect){
            mOnFragmnetSwitch.onFragmentChange(new ReciterChapterfragment(getActivity(),"DIRECT TRANSLATION"),"DIRECT TRANSLATION",true);
        }
        else if(view==mcardviewAmani){
            mOnFragmnetSwitch.onFragmentChange(new AmaniAudioFragment(getActivity(),"AMANI TAFSEER"),"AMANI TAFSEER",true);
        }
    }
}