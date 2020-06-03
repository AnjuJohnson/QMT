package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;


public class TajweedAudiofragment extends BaseFragment implements View.OnTouchListener {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TextView _view;
    ViewGroup _root;
    float dX;
    float dY;
    int lastAction;
    int prevX,prevY;
    LinearLayout floatingLayout;
    public TajweedAudiofragment(Context context) {
        this.context = context;

    }

    public TajweedAudiofragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.tajweed_audiofragment, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();

        floatingLayout =mFragmentView. findViewById(R.id.floating_layout);
        floatingLayout.setOnTouchListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        {
            final FrameLayout.LayoutParams par=(FrameLayout.LayoutParams)view.getLayoutParams();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_MOVE:
                {
                    par.topMargin+=(int)event.getRawY()-prevY;
                    prevY=(int)event.getRawY();
                    par.leftMargin+=(int)event.getRawX()-prevX;
                    prevX=(int)event.getRawX();
                    view.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP:
                {
                    par.topMargin+=(int)event.getRawY()-prevY;
                    par.leftMargin+=(int)event.getRawX()-prevX;
                    view.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_DOWN:
                {
                    prevX=(int)event.getRawX();
                    prevY=(int)event.getRawY();
                    par.bottomMargin=-2*view.getHeight();
                    par.rightMargin=-2*view.getWidth();
                    view.setLayoutParams(par);
                    return true;
                }
            }
            return false;
        }
    }
}