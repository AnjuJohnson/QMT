package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.viewpager.widget.ViewPager;

import com.Qubicle.QMT.Adapter.PagerAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.QuranIndexFragmentController;
import com.Qubicle.QMT.Controllers.ReadingFragmentController;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.CustomViewPager;
import com.Qubicle.QMT.utils.Utility;
import com.google.android.material.tabs.TabLayout;


public class QuranIndexfragment extends BaseFragment implements View.OnClickListener {
    TextView mSuraTextView,mJuzTextView;
    Context context;

    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
 //   ReadingFragmentController mReadingController;
    QuranIndexFragmentController mReadingController;
    ViewPager viewPager;
    Fragment fragment = null;
    PagerAdapter adapter;

    public QuranIndexfragment(Context context) {
        this.context = context;

        //   setRetainInstance(true);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_quran_index, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        Log.i("Tag", "onCreateView");
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {
        mSuraTextView=mFragmentView.findViewById(R.id.mSuraTextView);
        mJuzTextView=mFragmentView.findViewById(R.id.mJuzTextview);
        mSuraTextView.setOnClickListener(this);
        mJuzTextView.setOnClickListener(this);
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();

        mReadingController=new QuranIndexFragmentController(getActivity(),QuranIndexfragment.this);


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
      /*  viewPager = (ViewPager)view. findViewById(R.id.pager);

        adapter = new PagerAdapter(getChildFragmentManager(),2);
        viewPager.setAdapter(adapter);*/
        //  viewPager.disableScroll(true);
        Log.i("Tag", "onViewCreated");
        if(Constants.INDEX_PAGE.equalsIgnoreCase("main")){

            mSuraTextView.setBackgroundResource(R.drawable.tab_leftovalshape_grey);
            mJuzTextView.setBackgroundResource(R.drawable.tab_right_ovalshape_blue);
            mSuraTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
            mJuzTextView.setTextColor(getResources().getColor(R.color.white));

            mReadingController.loadFragmentMain();
        }
        else if(Constants.INDEX_PAGE.equalsIgnoreCase("all")){

          //  mViewPager.setCurrentItem(getItem(+1), true);
            mSuraTextView.setBackgroundResource(R.drawable.tab_ovalshape_blue);
            mJuzTextView.setBackgroundResource(R.drawable.tab_ovalshape);
            mSuraTextView.setTextColor(getResources().getColor(R.color.white));
            mJuzTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
            mReadingController.loadFragmentAll();




        }

    }
   /* private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("QURAN INDEX");
        OnMenuIconChange.iconchange(QuranIndexfragment.this);
        HomeActivity.fragment = QuranIndexfragment.this;
    }


    @Override
    public void onClick(View view) {
        if(view == mSuraTextView) {
            mSuraTextView.setBackgroundResource(R.drawable.tab_ovalshape_blue);
            mJuzTextView.setBackgroundResource(R.drawable.tab_ovalshape);
            mSuraTextView.setTextColor(getResources().getColor(R.color.white));
            mJuzTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
            Constants.INDEX_PAGE="all";
            mReadingController.loadFragmentAll();





          //  viewPager.setCurrentItem(getItem(-1), true);
/*
            if (viewPager.getCurrentItem() != 0)
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);*/

        }
        else if(view==mJuzTextView){
            Constants.INDEX_PAGE="main";
            mSuraTextView.setBackgroundResource(R.drawable.tab_leftovalshape_grey);
            mJuzTextView.setBackgroundResource(R.drawable.tab_right_ovalshape_blue);
            mSuraTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
            mJuzTextView.setTextColor(getResources().getColor(R.color.white));
            mReadingController.loadFragmentMain();


       /* //    viewPager.setCurrentItem(getItem(+1), true);
            if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount())
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);*/
        }
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}