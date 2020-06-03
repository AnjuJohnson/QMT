package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.TajweedUlAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.TajweedUlFragmentController;
import com.Qubicle.QMT.Listeners.TajweedUlListner;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.TajweedUlQuranAudio;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anju on 22-08-2019.
 */
public class TajweedUlFragment extends BaseFragment  implements TajweedUlListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    Dialog mDialog;
    TajweedUlAdapter tajweedUlAdapter;

    public TajweedUlFragmentController suraFragmentController;
    String malayalamFont, arabicfont,page;
    EditText searchEdittext;
    String[] beforestring={"ര്","ന്","ല്"};
    String[] afterstring={"ർ","ൻ","ൽ"};
    List<ChapterList> chapterLists;
    RecyclerView mAlltopicsRecycler;
    RecyclerView.LayoutManager layoutManager;
    public TajweedUlFragment(Context context) {
        this.context = context;
    }

    public TajweedUlFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.alltopic_fragments, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        suraFragmentController = new TajweedUlFragmentController(TajweedUlFragment.this, getActivity());
        configviews(mFragmentView);

        //check whether is there any data in table
        //if not call webservice else get it from table

        if (isNetworkAvailable()) {
            progress();
            suraFragmentController.startfetching();
        } else {

         chapterLists = suraFragmentController.checkdatapresentTajweed();
            if (chapterLists == null || chapterLists.isEmpty()) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            }
            else {
                suraFragmentController.parsedata(chapterLists);
            }
        }




    }

    private void configviews(View mFragmentView) {
        mAlltopicsRecycler = mFragmentView.findViewById(R.id.mAlltopicsRecycler);

        mAlltopicsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mAlltopicsRecycler.setLayoutManager(layoutManager);

        malayalamFont = PreferenceManager.getManager().getMalayalamFont();


        arabicfont = PreferenceManager.getManager().getArabicFont();
        searchEdittext=mFragmentView.findViewById(R.id.searchEdittext);

        searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String filtered = editable.toString();
                for(int i=0;i<beforestring.length;i++){
                    if(editable.toString().contains(beforestring[i])){
                        filtered= editable.toString().replaceAll(beforestring[i],afterstring[i]);
                    }

                }

                filter(filtered);
            }
        });

    }
    void filter(String text){
        List<ChapterList> temp = new ArrayList();
        for(ChapterList d:  chapterLists){

            if(!d.getSurah_no().equalsIgnoreCase("0")){
                if(d.getChapter_name().contains(text)){
                    temp.add(d);
                }
            }


        }
        //update recyclerview
        tajweedUlAdapter.updateList(temp);
    }




    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("TAJWEED UL QURAN");
        OnMenuIconChange.iconchange(TajweedUlFragment.this);
        HomeActivity.fragment = TajweedUlFragment.this;
    }



    @Override
    public void fetchingfailure() {
      chapterLists = suraFragmentController.checkdatapresentTajweed();
        if (chapterLists == null || chapterLists.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
        }
        else {
            suraFragmentController.parsedata(chapterLists);
        }


       // Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
        mDialog.dismiss();
    }

    @Override
    public void progress() {
        System.out.println("1111");

        if(mDialog==null){
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }


    }

    @Override
    public void datapassing(List<ChapterList> chapterListss) {

        if(mDialog!=null){
            mDialog.dismiss();
        }

        chapterLists=chapterListss;
        tajweedUlAdapter = new TajweedUlAdapter(TajweedUlFragment.this, getActivity(), chapterLists, malayalamFont);
        //   expandableListAdapter.setData(chapterLists);


        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // setting list adapter
                mAlltopicsRecycler.setAdapter(tajweedUlAdapter);
            }
        });
    }

    @Override
    public void passaudioparameter(String chapterno) {
        Bundle bundle = new Bundle();
        bundle.putString("CHAPTER_NO", chapterno);

        TajweedUlQuranPlayerfragment fragment = new TajweedUlQuranPlayerfragment();
        fragment.setArguments(bundle);
        mOnFragmnetSwitch.onFragmentChange(fragment, "TAJWEED UL QURAN AUDIO", true);
      /* Feedbackfragment fragment=new Feedbackfragment();
        mOnFragmnetSwitch.onFragmentChange(fragment, "TAJWEED UL QURAN AUDIO", true);
*/
    }

    @Override
    public void dataTajweedClassesAudio(List<TajweedUlQuranAudio> chapterLists) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        suraFragmentController.destroy();
    }

}
