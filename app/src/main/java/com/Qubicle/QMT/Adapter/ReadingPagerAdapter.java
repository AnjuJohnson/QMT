package com.Qubicle.QMT.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.Qubicle.QMT.Views.Fragments.Alltopicfragment;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.MainTopicsfragment;
import com.Qubicle.QMT.Views.Fragments.SuraFragment;

/**
 * Created by Anju on 16-10-2019.
 */
public class ReadingPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    private String title[] = {"One", "Two"};

    public ReadingPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               // Feedbackfragment tab1 = new Feedbackfragment();
                SuraFragment tab1 =  SuraFragment.newInstance("sura");

            return tab1;
            case 1:

                JuzFragment tab2 =  JuzFragment.newInstance("juz");

                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return title.length;
    }

}
