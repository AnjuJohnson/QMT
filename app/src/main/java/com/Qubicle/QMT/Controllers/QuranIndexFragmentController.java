package com.Qubicle.QMT.Controllers;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Fragments.Alltopicfragment;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.MainTopicsfragment;
import com.Qubicle.QMT.Views.Fragments.QuranIndexfragment;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.Qubicle.QMT.Views.Fragments.SuraFragment;

/**
 * Created by Anju on 22-08-2019.
 */
public class QuranIndexFragmentController {
    Context mcontext;
    FragmentManager mFragmentManager;
    //Readingfragment readingfragment;
    QuranIndexfragment readingfragment;
    public QuranIndexFragmentController(Context context, QuranIndexfragment fragment) {
        this.mcontext=context;
        this.readingfragment=fragment;

    }
    public void loadFragmentMain(){

      //  Fragment childFragment =MainTopicsfragment.newInstance("main");
        Fragment childFragment =new MainTopicsfragment();
        FragmentTransaction transaction = readingfragment.getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frametab, childFragment).commit();
      /*  transaction.addToBackStack("READING");
        transaction.commitAllowingStateLoss();*/

    }
    public void loadFragmentAll(){

     //   Fragment childFragment = Alltopicfragment.newInstance("all");
        Fragment childFragment = new Alltopicfragment();
        FragmentTransaction transaction = readingfragment.getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frametab, childFragment).commit();
        /*transaction.addToBackStack("READING");
        transaction.commitAllowingStateLoss();*/
    }
}
