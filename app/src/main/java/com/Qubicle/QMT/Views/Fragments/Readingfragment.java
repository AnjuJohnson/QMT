package com.Qubicle.QMT.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.Qubicle.QMT.Adapter.ReadingPagerAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.ReadingFragmentController;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;



public class Readingfragment extends BaseFragment implements View.OnClickListener {
    WebView mWebview;
    TextView mSuraTextView,mJuzTextView;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    ReadingFragmentController mReadingController;
    String page;
    ViewPager readingviewpager;
    ReadingPagerAdapter adapter;
    public Readingfragment(Context context) {
        this.context = context;

     //   setRetainInstance(true);
    }

    public Readingfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_reading, container, false);
          mainfunction(mFragmentView,savedInstanceState);
        return mFragmentView;

    }
    public void mainfunction(View mFragmentView,Bundle savedInstanceState) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mReadingController=new ReadingFragmentController(getActivity(),Readingfragment.this);
        mSuraTextView=mFragmentView.findViewById(R.id.mSuraTextView);
        mJuzTextView=mFragmentView.findViewById(R.id.mJuzTextview);
        readingviewpager=mFragmentView.findViewById(R.id.readingviewpager);
        readingviewpager.setOffscreenPageLimit(2);
        readingviewpager.setAdapter(adapter);
        mSuraTextView.setOnClickListener(this);
        mJuzTextView.setOnClickListener(this);

        page= PreferenceManager.getManager().getReadingPage();


            if(!page.isEmpty()){
                if(page.equalsIgnoreCase("juzpage")){
                    mJuzTextView.setBackgroundResource(R.drawable.tab_right_ovalshape_blue);
                    mSuraTextView.setBackgroundResource(R.drawable.tab_leftovalshape_grey);

                    mSuraTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
                    mJuzTextView.setTextColor(getResources().getColor(R.color.white));
                  //  mReadingController.loadFragmentJuz();
                   /* if (readingviewpager.getCurrentItem() < readingviewpager.getAdapter().getCount())
                        readingviewpager.setCurrentItem(readingviewpager.getCurrentItem() + 1);*/

                }

                else if(page.equalsIgnoreCase("surapage")){
                    mSuraTextView.setBackgroundResource(R.drawable.tab_ovalshape_blue);
                    mJuzTextView.setBackgroundResource(R.drawable.tab_ovalshape);
                    mSuraTextView.setTextColor(getResources().getColor(R.color.white));
                    mJuzTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
                   // mReadingController.loadFragmentSura();
                   /* if (readingviewpager.getCurrentItem() != 0)
                        readingviewpager.setCurrentItem(readingviewpager.getCurrentItem() - 1);*/
                }

                else {
                    mSuraTextView.setBackgroundResource(R.drawable.tab_ovalshape_blue);
                    mJuzTextView.setBackgroundResource(R.drawable.tab_ovalshape);
                    mSuraTextView.setTextColor(getResources().getColor(R.color.white));
                    mJuzTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
                  //  mReadingController.loadFragmentSura();
                   /* if (readingviewpager.getCurrentItem() != 0)
                        readingviewpager.setCurrentItem(readingviewpager.getCurrentItem() - 1);*/
                }
            }



    }
    private int getItem(int i) {
        return readingviewpager.getCurrentItem() + i;
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("READING");
        OnMenuIconChange.iconchange(Readingfragment.this);
        HomeActivity.fragment = Readingfragment.this;
    }

    @Override
    public void onClick(View view) {
        if(view == mSuraTextView) {
            mSuraTextView.setBackgroundResource(R.drawable.tab_ovalshape_blue);
            mJuzTextView.setBackgroundResource(R.drawable.tab_ovalshape);
            mSuraTextView.setTextColor(getResources().getColor(R.color.white));
            mJuzTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
         //   mReadingController.loadFragmentSura();
            if (readingviewpager.getCurrentItem() != 0)
                readingviewpager.setCurrentItem(readingviewpager.getCurrentItem() - 1);

        }
        else if(view==mJuzTextView){
            mJuzTextView.setBackgroundResource(R.drawable.tab_right_ovalshape_blue);
            mSuraTextView.setBackgroundResource(R.drawable.tab_leftovalshape_grey);

            mSuraTextView.setTextColor(getResources().getColor(R.color.juz_text_blue));
            mJuzTextView.setTextColor(getResources().getColor(R.color.white));
        //    mReadingController.loadFragmentJuz();
            if (readingviewpager.getCurrentItem() < readingviewpager.getAdapter().getCount())
                readingviewpager.setCurrentItem(readingviewpager.getCurrentItem() + 1);
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        adapter = new ReadingPagerAdapter(getChildFragmentManager());
    }
}