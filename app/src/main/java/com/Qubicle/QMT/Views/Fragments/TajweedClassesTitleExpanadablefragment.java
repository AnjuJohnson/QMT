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
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.TajweedClassExpandableAdapter;
import com.Qubicle.QMT.Adapter.TajweedClassesTitlesAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.TajweedClassesTitlesFragmentController;
import com.Qubicle.QMT.Listeners.TajweedListner;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Models.TajweedClassAudio;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.AnimatedExpandableListView;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class TajweedClassesTitleExpanadablefragment extends BaseFragment implements TajweedListner {
    WebView mWebview;
    RecyclerView mAlltopicsRecycler, mJuzTextView;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TajweedClassesTitlesFragmentController tajweedClassesTitlesFragmentController;
    String page;
    TajweedClassExpandableAdapter titlesAdapter;
    RecyclerView.LayoutManager layoutManager;
    String malayalamFont, arabicfont;
    EditText searchEdittext;
    List<Tajweed> tajweedList;
    List<Tajweed> tajweedListSingle;
    String[] beforestring = {"ര്", "ന്", "ല്"};
    String[] afterstring = {"ർ", "ൻ", "ൽ"};
    Dialog mDialog;
    private AnimatedExpandableListView mExpandableView;
    public TajweedClassesTitleExpanadablefragment(Context context) {
        this.context = context;

    }

    public TajweedClassesTitleExpanadablefragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_amanitafseer, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {

        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mExpandableView = mFragmentView.findViewById(R.id.mExpandableView);
        onclick(mExpandableView);
        mExpandableView.setChildIndicator(null);
        mExpandableView.setChildDivider(getResources().getDrawable(R.color.transparent));
        mExpandableView.setDivider(getResources().getDrawable(R.color.light_grey_line));
        mExpandableView.setDividerHeight(2);

        searchEdittext = mFragmentView.findViewById(R.id.searchEdittext);

        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        tajweedClassesTitlesFragmentController = new TajweedClassesTitlesFragmentController(TajweedClassesTitleExpanadablefragment.this, getActivity());
        if (isNetworkAvailable()) {
            progress();
            tajweedClassesTitlesFragmentController.startfetching();
        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
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
    public void onclick(final AnimatedExpandableListView ExpandListView) {
        ExpandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (ExpandListView.isGroupExpanded(groupPosition)) {

                    ExpandListView.collapseGroup(groupPosition);

                    //  ExpandListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    ExpandListView.expandGroup(groupPosition);
                    //   ExpandListView.expandGroupWithAnimation(groupPosition);
                }


                //mugavura

              /*  if (groupPosition == 0) {

                } else {
                    if (ExpandListView.isGroupExpanded(groupPosition)) {

                        ExpandListView.collapseGroup(groupPosition);

                      //  ExpandListView.collapseGroupWithAnimation(groupPosition);
                    } else {
                        ExpandListView.expandGroup(groupPosition);
                     //   ExpandListView.expandGroupWithAnimation(groupPosition);
                    }
                }*/


                return true;
            }

        });


        ExpandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    ExpandListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

    }

    void filter(String text) {
        List<Tajweed> temp = new ArrayList();
        for (Tajweed d : tajweedList) {
            if (d.getTitle().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        titlesAdapter.updateList(temp);
    }



    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("TAJWEED CLASSES");
        OnMenuIconChange.iconchange(TajweedClassesTitleExpanadablefragment.this);
        HomeActivity.fragment = TajweedClassesTitleExpanadablefragment.this;
    }


    @Override
    public void fetchingsuccess(List<Tajweed> TajweedTitles) {
        tajweedList = TajweedTitles;
        if (mDialog != null) {
            mDialog.dismiss();
        }

        tajweedListSingle=new ArrayList<>();
        for(int i=0;i<TajweedTitles.size();i++){
            if(TajweedTitles.get(i).getTopics().size()==1){
               Tajweed tajweednew=new Tajweed();
               tajweednew.setId(TajweedTitles.get(i).getId());
               tajweednew.setTitle(TajweedTitles.get(i).getTitle());
               tajweednew.setOrder_by(TajweedTitles.get(i).getOrder_by());

                tajweedListSingle.add(tajweednew);

            }
            else {
                tajweedListSingle.add(TajweedTitles.get(i));
            }
        }


        titlesAdapter = new TajweedClassExpandableAdapter(TajweedClassesTitleExpanadablefragment.this, getActivity(),tajweedList,tajweedListSingle);
        mExpandableView.setAdapter(titlesAdapter);


    }



    @Override
    public void fetchingfailure() {
        Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progress() {
        if (mDialog == null) {
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }
    }

    @Override
    public void passaudioparameter(String topic_id,String titleid,String topic) {
 Bundle bundle = new Bundle();
        bundle.putString("TOPIC_ID", topic_id);
        bundle.putString("TITLE_ID", titleid);
        bundle.putString("TOPIC", topic);

        TajweedClassesTopicsPlayerNewfragment fragment = new TajweedClassesTopicsPlayerNewfragment();
        fragment.setArguments(bundle);
        mOnFragmnetSwitch.onFragmentChange(fragment, "TAJWEED CLASSES AUDIO", true);
    }

    @Override
    public void dataTajweedClassesAudio(List<TajweedClassAudio> tajweedclasslist) {

    }




}