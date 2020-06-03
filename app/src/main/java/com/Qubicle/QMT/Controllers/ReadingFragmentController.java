package com.Qubicle.QMT.Controllers;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.Qubicle.QMT.Views.Fragments.SuraFragment;

/**
 * Created by Anju on 22-08-2019.
 */
public class ReadingFragmentController {
    Context mcontext;
    FragmentManager mFragmentManager;
    Readingfragment readingfragment;
    Fragment childFragment;

    public ReadingFragmentController(Context context,Readingfragment fragment) {
        this.mcontext=context;
        this.readingfragment=fragment;

    }
    public void loadFragmentSura(){

        /* childFragment = new SuraFragment();

        FragmentTransaction transaction = readingfragment.getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frametab, childFragment);
        transaction.addToBackStack("READING_SURA");
        transaction.commit();*/

    }
    public void loadFragmentJuz(){

       /*  childFragment = new JuzFragment();
        FragmentTransaction transaction = readingfragment.getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frametab, childFragment);
        transaction.addToBackStack("READING_JUZ");
        transaction.commit();
      */
    }


}
