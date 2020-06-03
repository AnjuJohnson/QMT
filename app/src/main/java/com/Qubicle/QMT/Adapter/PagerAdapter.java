package com.Qubicle.QMT.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Qubicle.QMT.Views.Fragments.Alltopicfragment;
import com.Qubicle.QMT.Views.Fragments.Bookmarksfragment;
import com.Qubicle.QMT.Views.Fragments.Feedbackfragment;
import com.Qubicle.QMT.Views.Fragments.MainTopicsfragment;
import com.Qubicle.QMT.utils.Constants;

/**
 * Created by Anju on 16-10-2019.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               // Feedbackfragment tab1 = new Feedbackfragment();
                MainTopicsfragment tab1 =  MainTopicsfragment.newInstance("main");

            return tab1;
            case 1:
             //   Feedbackfragment tab2 = new Feedbackfragment();
                Alltopicfragment tab2 =  Alltopicfragment.newInstance("all");
               // MainTopicsfragment tab2 =  MainTopicsfragment.newInstance("main");
                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
