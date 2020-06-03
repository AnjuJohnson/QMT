package com.Qubicle.QMT.Views.Fragments;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.MemorizationFragmentController;
import com.Qubicle.QMT.Listeners.MemorizationTestListner;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Practice;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class Practisefragment extends BaseFragment implements MemorizationTestListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TextView tvDescription, chapternameTextView, next, previous;
    TextView showmore, versefromtextview, versetotextview, mChapternoTextView, mMeaningMalayalamTextView;
    boolean expand = false;
    RelativeLayout chapterlayout, fromlayout, tolayout;
    MemorizationFragmentController memorizationFragmentController;
    String[] chapterlist;
    List<ChapterList> chapterFullList = new ArrayList<>();
    private String[] verselist;
    String mTranslation, mChapterno="1", mVerseFrom="1", mVerseTo="1",mReciter;
    List<Practice> VerseList = new ArrayList<>();
    LinearLayout versedetaillayout, versemeaninglayout;
    int max = 0, min = 0, current;
    RadioButton filterradiobutton;
    String filtertext = "Show Both";
    public static List<AudioCategoryReciterList> reciterlist;
String defaultreciter;
    public Practisefragment(Context context) {
        this.context = context;

    }

    public Practisefragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.practise_fragment, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mTranslation = PreferenceManager.getManager().getTranslation();

        if (mTranslation.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
            mTranslation = "meaning_malayalam";
        } else if (mTranslation.equalsIgnoreCase("Amani Thafseer")) {
            mTranslation = "meaning_malayalam_new";

        }
        chapterlayout = mFragmentView.findViewById(R.id.chapterlayout);
        chapternameTextView = mFragmentView.findViewById(R.id.chapternameTextView);

        memorizationFragmentController = new MemorizationFragmentController(getActivity(), Practisefragment.this);

        if (isNetworkAvailable()) {
            memorizationFragmentController.callReciterAudioCategoryService();
        }


      //set chapter list
        List<ChapterList> chapterLists = memorizationFragmentController.checkdatapresent();
        if (chapterLists == null || chapterLists.isEmpty()) {
            if (isNetworkAvailable()) {
                progress();
                memorizationFragmentController.startfetching();
            }
        } else {
            memorizationFragmentController.parsedata(chapterLists);
        }

        chapterlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chapterlist != null) {
                    PopupMenu menu = new PopupMenu(getActivity(), view);

                    for (int p = 0; p < chapterlist.length; p++) {
                        menu.getMenu().add(Menu.NONE, p, p, chapterlist[p]);
                    }
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();
                            chapternameTextView.setText(item.getTitle());


                            for (ChapterList chapterList : chapterFullList) {
                                if (chapterList.getChapter_name().equalsIgnoreCase(item.getTitle().toString())) {
                                    setverse(chapterList.getSurah_no());
                                    mChapterno = chapterList.getSurah_no();
                                }
                            }


                            callpracticeapi();


                            return true;
                        }

                    });
                }


            }
        });

    }

    public void setverse(String chapterno) {
        List<ChapterList> lists = memorizationFragmentController.checkchapterdetail(chapterno);
        if (lists != null) {

            verselist = new String[lists.get(0).getAyat().size()];
            for (int i = 0; i < lists.get(0).getAyat().size(); i++) {
                verselist[i] = lists.get(0).getAyat().get(i).getAyat_no();
            }
        }
        // setversetopopup(verselist);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {

    }

    @Override
    public void datapassing(List<ChapterList> chapterLists) {

        if (chapterLists != null) {
            chapterFullList = chapterLists;
            chapterlist = new String[chapterLists.size()];
            for (int i = 0; i < chapterLists.size(); i++) {
                chapterlist[i] = chapterLists.get(i).getChapter_name();
            }
        }

    }

    @Override
    public void versedetail(List<Verse> verseList) {

    }

    @Override
    public void Practiceversedetail(List<Practice> verseList) {
        if (verseList != null) {
            VerseList = new ArrayList<>();
            VerseList = verseList;
        }

    }

    @Override
    public void dataReciterAudiopassing(List<AudioCategoryReciterList> audio) {
        reciterlist = audio;
        if(reciterlist!=null){
            defaultreciter=reciterlist.get(0).getReciter_list().get(0).getReciter_name();
        }
callpracticeapi();
    }

    public void recitercalling(View view) {
        //set reciter according to catogory 'verse'
        if (isNetworkAvailable()) {
          //  memorizationFragmentController.callReciterAudioCategoryService();

            if(reciterlist!=null){
                Constants.RECITER_AUDIO_CATEGORY_LIST = new ArrayList<>();
                for (AudioCategoryReciterList audioCategoryReciterList : reciterlist) {
                    if (audioCategoryReciterList.getAudio_category().trim().equalsIgnoreCase("Verse")) {
                        Constants.RECITER_AUDIO_CATEGORY_LIST = audioCategoryReciterList.getReciter_list();

                    }

                }
            }
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }


        String[] reciternamelist = new String[Constants.RECITER_AUDIO_CATEGORY_LIST.size()];

        if (Constants.RECITER_AUDIO_CATEGORY_LIST != null) {

            for (int i = 0; i < Constants.RECITER_AUDIO_CATEGORY_LIST.size(); i++) {
                reciternamelist[i] = Constants.RECITER_AUDIO_CATEGORY_LIST.get(i).getReciter_name();
            }
        }

//show popupmenu
        PopupMenu menu = new PopupMenu(getActivity(), view);
        if (reciternamelist != null) {

            for (int p = 0; p < reciternamelist.length; p++) {
                menu.getMenu().add(Menu.NONE, p, p,reciternamelist[p]);
            }
            menu.show();
        }


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                defaultreciter = item.getTitle().toString();
                 callpracticeapi();
                return true;
            }

        });
    }

    private void callpracticeapi(){
memorizationFragmentController.startfetchingpracticeapi(mChapterno,mVerseFrom,mVerseTo,mTranslation,defaultreciter);
    }
}
