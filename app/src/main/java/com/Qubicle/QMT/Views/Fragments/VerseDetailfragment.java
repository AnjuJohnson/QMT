package com.Qubicle.QMT.Views.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.VerseDetailAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.VerseDetailFragmentController;
import com.Qubicle.QMT.Listeners.AllAudioListner;
import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.ReciterListner;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.JuzDetailAdapterModel;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.ReciterNew;
import com.Qubicle.QMT.Models.SpinnerchapterlistModel;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.Models.WordMeaningList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.RangeSeekBar;
import com.Qubicle.QMT.utils.Utility;


import java.util.ArrayList;
import java.util.List;


public class VerseDetailfragment extends BaseFragment implements View.OnClickListener, BookmarkListner, VerseDetailListener, AudioListner, AudioNewfragment.Autoscroll, ReciterListner {
    public static String malayalamFont, arabicfont;
    Context context;
    ImageView mImageview, mAutoScrollImageView;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    String chapter_id, ayat_id, VerseMinLimit;
    VerseDetailFragmentController mVerseDetailController;
    RecyclerView mVerseDetailRecycler;
    Dialog mDialog, dialogAudioSet;
    WordMeaningList wordMeaningList = new WordMeaningList();
    public static String ayat_no;
    RecyclerView.LayoutManager layoutManager;
    VerseDetailAdapter verseDetailAdapter;
    String[] repeatsinglearray = {"0", "1", "2", "3", "4", "5","6","7","8","9","10"};
    AllAudioListner allAudioListner;
    List<VerseDetail> verseDetailsTemp;
    public static String sVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, sTranslationNumber;
    int i = 0;
    String audio_category;
    private static final String SELECTED_KEY = "selected_position";
    private int lastFirstVisiblePosition = -1;
    Parcelable state;
    List<AudioCategoryReciterList> reciterAudioCategoryList = new ArrayList<>();
    Boolean mAutoScroll = true;
   public static List<AudioCategoryReciterList> reciterlist;
    public VerseDetailfragment(Context context, String chapterid, String ayatid, String ayatno) {
        this.context = context;
        this.chapter_id = chapterid;
        this.ayat_id = ayatid;
        this.ayat_no = ayatno;
        //   setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_versedetail, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }


  /*  @Override
    public void onPause() {
        // Save ListView state @ onPause
        Log.d("saved", "saving listview state @ onPause");
        state = layoutManager.onSaveInstanceState();
        super.onPause();
    }
*/
    public void mainfunction(View mFragmentView) {
        PreferenceManager.getManager().setReadingPage("surapage");
        sVerseRadioButton = PreferenceManager.getManager().getVerse();
        sTranslationRadioButton = PreferenceManager.getManager().gettranslationOn();
        sWordMeaningRadioButton = PreferenceManager.getManager().getWordMeaning();
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        arabicfont = PreferenceManager.getManager().getArabicFont();
        sTranslationNumber = PreferenceManager.getManager().getTranslation();
        configviews(mFragmentView);
    }

    private void configviews(View mFragmentView) {
        System.out.println("Chapterid" + chapter_id + ayat_id);
        mVerseDetailController = new VerseDetailFragmentController(getActivity(), VerseDetailfragment.this, VerseDetailfragment.this,VerseDetailfragment.this);
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        allAudioListner = (AllAudioListner) getActivity();


        mVerseDetailRecycler = mFragmentView.findViewById(R.id.mVerseDetailRecycler);

        mVerseDetailRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mVerseDetailRecycler.setLayoutManager(layoutManager);



        List<VerseDetail> verseDetailList = mVerseDetailController.checkdatapresent(chapter_id);
        if (verseDetailList == null || verseDetailList.isEmpty()) {

            if (isNetworkAvailable()) {
                progress();
                mVerseDetailController.startfetching(chapter_id, ayat_no);
            }


        } else {
            datapassingVerseDetail(verseDetailList);
        }






/*

        if (isNetworkAvailable()) {
            progress();
            mVerseDetailController.startfetching(chapter_id, ayat_no);
        } else {
            List<VerseDetail> verseDetailList = mVerseDetailController.checkdatapresent(chapter_id);
            if (verseDetailList == null || verseDetailList.isEmpty()) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            } else {
                datapassingVerseDetail(verseDetailList);
            }
        }
*/






      /*  if (isNetworkAvailable()) {
            progress();
            mVerseDetailController.startfetching(chapter_id, ayat_no);
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("CHAPTER");
        OnMenuIconChange.iconchange(VerseDetailfragment.this);
        HomeActivity.fragment = VerseDetailfragment.this;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void fetchingsuccess() {
        if(mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override
    public void fetchingfailure() {
        //  Toast.makeText(getActivity(), " error", Toast.LENGTH_LONG).show();
        if(mDialog!=null){
            mDialog.dismiss();
        }

    }

    @Override
    public void progress() {
        mDialog = Utility.showProgressBar(getActivity());
        mDialog.show();
      /*  mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });*/
    }

    @Override
    public void dataReciterpassing(List<Reciter> audio) {

    }

    @Override
    public void dataReciterAudiopassing(List<AudioCategoryReciterList> audio) {
        reciterlist=audio;
    }


    @Override
    public void datapassingVerseDetail(final List<VerseDetail> verseDetailsList) {
        mVerseDetailController.callReciterAudioCategoryService();
        verseDetailsTemp = new ArrayList();

        final ArrayList<WordMeaningList> list = new ArrayList<WordMeaningList>();

        int scrollto = Integer.parseInt(ayat_no);
        wordMeaningList = new WordMeaningList();
        wordMeaningList.setMeaning_arabic("ٱلرَّحِيمِ");
        wordMeaningList.setMeaning_mala("പരമകാരുണികനും");
        list.add(wordMeaningList);

        wordMeaningList = new WordMeaningList();
        wordMeaningList.setMeaning_arabic("ٱلرَّحۡمَٰنِ");
        wordMeaningList.setMeaning_mala("കരുണാനിധിയുമായ");
        list.add(wordMeaningList);


        wordMeaningList = new WordMeaningList();
        wordMeaningList.setMeaning_arabic("ٱللَّهِ");
        wordMeaningList.setMeaning_mala("അല്ലാഹുവിൻ്റെ");
        list.add(wordMeaningList);


        wordMeaningList = new WordMeaningList();
        wordMeaningList.setMeaning_arabic("بِسۡمِ");
        wordMeaningList.setMeaning_mala("നാമത്തിൽ");
        list.add(wordMeaningList);


        getActivity().runOnUiThread(new Runnable() {
            public void run() {

                for (VerseDetail verseDetail : verseDetailsList) {
                    //    verseDetail.setWordMeaningLists(list);


                    verseDetail.setSurah_no(verseDetail.getSurah_no());
                    verseDetail.setAyat_no(verseDetail.getAyat_no());
                    verseDetail.setMeaning_arabic(verseDetail.getMeaning_arabic());
                    verseDetail.setMeaning_malayalam(verseDetail.getMeaning_malayalam());
                    verseDetail.setMeaning_malayalam_new(verseDetail.getMeaning_malayalam_new());
                    verseDetail.setWord_meaning(verseDetail.getWord_meaning());
                    verseDetail.setAudio(verseDetail.getAudio());
                    verseDetail.setAudio_title(verseDetail.getAudio_title());
                    verseDetail.setChapter_name(verseDetail.getChapter_name());
                    verseDetail.setChapter_name_meaning(verseDetail.getChapter_name_meaning());
                    verseDetail.setSelected(false);
                    verseDetailsTemp.add(verseDetail);

                }


                //check if favourite

                List<Favorites> favoriteslist = mVerseDetailController.getAllFavourites();
                if (favoriteslist != null && favoriteslist.size() != 0) {
                    for (Favorites favorites : favoriteslist) {
                        for (VerseDetail verseDetail : verseDetailsTemp) {

                            if ((favorites.getSurah_no().equalsIgnoreCase(verseDetail.getSurah_no())) && (favorites.getAyat_no()
                                    .equalsIgnoreCase(verseDetail.getAyat_no()))) {
                                verseDetail.setFavourites(true);

                            } else {
                                //   verseDetail.setFavourites(false);
                            }
                        }

                    }

                }


                verseDetailAdapter = new VerseDetailAdapter(VerseDetailfragment.this, VerseDetailfragment.this, VerseDetailfragment.this, verseDetailsTemp, getActivity(),
                        sVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, malayalamFont, arabicfont, sTranslationNumber);
                mVerseDetailRecycler.setAdapter(verseDetailAdapter);
            }
        });

        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };


        if (!verseDetailsList.get(0).getAyat_no().equalsIgnoreCase("0")) {
            scrollto = scrollto - 1;
        }


        mVerseDetailRecycler.scrollToPosition(scrollto);
  /*

//      smoothScroller.setTargetPosition(scrollto);
        mVerseDetailRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
             //   layoutManager.startSmoothScroll(smoothScroller);

            }
        }, 300);
*/

    }

    @Override
    public void verse_explanation(String chapterid, String ayatno) {
        ayat_no = ayatno;
        mOnFragmnetSwitch.onFragmentChange(new VerseExplanationFragment(getActivity(), chapterid, ayatno, malayalamFont), "VERSE EXPLANATION", true);
    }

    @Override
    public void addtofavourites(String chaptername, String chapterno, String ayatno, String verse, String translation) {
        for (VerseDetail v2 : verseDetailsTemp) {
            if ((ayatno.equalsIgnoreCase(v2.getAyat_no())) && (chapterno.equalsIgnoreCase(v2.getSurah_no()))) {
                if (v2.getFavourites()) {
                    v2.setFavourites(false);
                    //delete from db

                    Favorites favorites = mVerseDetailController.checkFavouritespresent(chapterno, ayatno);
                    if (favorites == null) {
                        //    Toast.makeText(getActivity(), "No Notes", Toast.LENGTH_LONG).show();
                    } else {
                        mVerseDetailController.deletefav(favorites);
                        Toast.makeText(getActivity(), "unfavourite", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    //add to db
                    v2.setFavourites(true);
                    mVerseDetailController.addFavourites(chaptername, chapterno, ayatno, verse, translation);
                    Toast.makeText(getActivity(), "favourite", Toast.LENGTH_SHORT).show();
                }

            } else {
                //  v2.setWordmeaning(false);
            }
        }

        verseDetailAdapter.setWordMeaningItems(verseDetailsTemp);
        verseDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void verse_vyakyanakurip(String vyakyanakurip, String chapterid, String ayatno) {
        mOnFragmnetSwitch.onFragmentChange(new VyakyanakuripFragment(vyakyanakurip, getActivity(), chapterid, ayatno, malayalamFont), "VYAKYANAKURIPPU", true);
    }

    @Override
    public void datapassingChapterExplanation(VerseExplanation verseExplanation) {

    }

    @Override
    public void wordmeaningOn(String ayatno) {

        for (VerseDetail v2 : verseDetailsTemp) {
            if (ayatno.equalsIgnoreCase(v2.getAyat_no())) {
                if (v2.getWordmeaning()) {
                    v2.setWordmeaning(false);

                } else {
                    v2.setWordmeaning(true);

                }

            } else {
                //  v2.setWordmeaning(false);
            }
        }

        verseDetailAdapter.setWordMeaningItems(verseDetailsTemp);
        verseDetailAdapter.notifyDataSetChanged();

    }


    @Override
    public void fetchaudio(final String audiocategory, final String ChapterName, final String ChapterNameMeaning, final String ChapterNo, String VerseMinLimitt, String VerseMaxLimit) {
        //show playerselectiondialog


        if(isNetworkAvailable()){
            VerseMinLimit = VerseMinLimitt;
            dialogAudioSet = new Dialog(context);
            dialogAudioSet.setContentView(R.layout.audio_dailog_fragment);
            dialogAudioSet.setCanceledOnTouchOutside(false);
            TextView mChapterNameTextView = (TextView) dialogAudioSet.findViewById(R.id.mChapterNameTextView);
            TextView mChapterMeaningTextView = (TextView) dialogAudioSet.findViewById(R.id.mChapterMeaningTextView);
            ArrayAdapter<String> repeatsingleadapter;
            ArrayAdapter<String> repeatmultipleadapter;
            Spinner repeatsinglespinner = (Spinner) dialogAudioSet.findViewById(R.id.repeatsinglespinner);
            //  Spinner repeatmultiplespinner = (Spinner) dialogAudioSet.findViewById(R.id.repeatmultiplespinner);
            ImageView mAutoScrollImageView = dialogAudioSet.findViewById(R.id.mAutoScrollImageView);
            ImageView reciterImage = (ImageView) dialogAudioSet.findViewById(R.id.reciterImage);
            ImageView closeplayer = (ImageView) dialogAudioSet.findViewById(R.id.closeplayer);
            TextView reciternametextview = (TextView) dialogAudioSet.findViewById(R.id.reciternametextview);
            TextView repeatcount = (TextView) dialogAudioSet.findViewById(R.id.repeatcount);
            //  AppCompatSpinner mReciterSpinner = (AppCompatSpinner) dialogAudioSet.findViewById(R.id.mReciterSpinner);
            int spinnerPosition;
            int spinnermultiplePosition;
            int spinnerReciterPosition;

            //  final RangeSeekBar newrangeseekbar = (RangeSeekBar) dialogAudioSet.findViewById(R.id.rangeSeekbar);
            ImageView mPlayAudio = (ImageView) dialogAudioSet.findViewById(R.id.mPlayAudio);

            ///setting data
            mChapterNameTextView.setText(ChapterName);
            mChapterMeaningTextView.setText(ChapterNameMeaning);
            audio_category = audiocategory;
            //////////////////////////////////////////////////////////////////////////////////////

            //set reciterlist according to category
            //check if reciter from preference is present in the category list
            //fetch reciter audiocategory

            Constants.RECITER_AUDIO_CATEGORY_LIST = new ArrayList<>();
            for (AudioCategoryReciterList audioCategoryReciterList : reciterlist) {
                if (audioCategoryReciterList.getAudio_category().trim().equalsIgnoreCase(audio_category)) {

                    Constants.RECITER_AUDIO_CATEGORY_LIST = audioCategoryReciterList.getReciter_list();


                }

            }




/*

        reciterAudioCategoryList = mVerseDetailController.checkreciterAudioCategorypresent();
        if (reciterAudioCategoryList == null || reciterAudioCategoryList.size() == 0) {

        } else {
            Constants.RECITER_AUDIO_CATEGORY_LIST = new ArrayList<>();
            for (AudioCategoryReciterList audioCategoryReciterList : reciterAudioCategoryList) {
                if (audioCategoryReciterList.getAudio_category().trim().equalsIgnoreCase(audio_category)) {

                    Constants.RECITER_AUDIO_CATEGORY_LIST = audioCategoryReciterList.getReciter_list();


                }

            }
        }*/

/////////////

            String[] reciternamelist = new String[Constants.RECITER_AUDIO_CATEGORY_LIST.size()];

            if (Constants.RECITER_AUDIO_CATEGORY_LIST != null) {

                for (int i = 0; i < Constants.RECITER_AUDIO_CATEGORY_LIST.size(); i++) {
                    reciternamelist[i] = Constants.RECITER_AUDIO_CATEGORY_LIST.get(i).getReciter_name();
                }
            }


            int flag = 0;

//check if default selected reciter is present in the list
            for (ReciterNew reciterNew : Constants.RECITER_AUDIO_CATEGORY_LIST) {
                if (reciterNew.getReciter_name().contains(PreferenceManager.getManager().getReciter())) {
                    flag = 1;
                }
            }
            if (flag == 1) {
                reciternametextview.setText(PreferenceManager.getManager().getReciter());
            } else {
                if (Constants.RECITER_AUDIO_CATEGORY_LIST.size() != 0) {
                    reciternametextview.setText(Constants.RECITER_AUDIO_CATEGORY_LIST.get(0).getReciter_name());

                } else {
                    //  reciternametextview.setText(PreferenceManager.getManager().getReciter());
                }
            }
/////////////////////////////////////////////////////////////////////
            //autoscroll

            if (PreferenceManager.getManager().getAutoScroll().equalsIgnoreCase("DEFAULT")) {
                mAutoScrollImageView.setImageResource(R.drawable.on);
                PreferenceManager.getManager().setAutoScroll("autoscrollOn");
                mAutoScroll = true;
            } else if (PreferenceManager.getManager().getAutoScroll().contains("autoscrollOn")) {
                mAutoScrollImageView.setImageResource(R.drawable.on);
                PreferenceManager.getManager().setAutoScroll("autoscrollOn");

                mAutoScroll = true;
            } else if (PreferenceManager.getManager().getAutoScroll().contains("autoscrollOff")) {
                mAutoScrollImageView.setImageResource(R.drawable.off);
                PreferenceManager.getManager().setAutoScroll("autoscrollOff");

                mAutoScroll = false;
            }

            mAutoScrollImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAutoScroll) {
                        mAutoScrollImageView.setImageResource(R.drawable.off);
                        PreferenceManager.getManager().setAutoScroll("autoscrollOff");
                        mAutoScroll = false;
                        System.out.println(PreferenceManager.getManager().getAutoScroll());
                    } else {
                        mAutoScrollImageView.setImageResource(R.drawable.on);
                        PreferenceManager.getManager().setAutoScroll("autoscrollOn");
                        mAutoScroll = true;
                        System.out.println(PreferenceManager.getManager().getAutoScroll());
                    }
                }
            });


            ////////////////////////////////////////////////////////

/*

        if (PreferenceManager.getManager().getReciter().equalsIgnoreCase("DEFAULT")) {
            //PreferenceManager.getManager().setReciter(Constants.RECITER_LIST.get(0).getName());
            //  mReciterSpinner.setSelection(0);
            reciternametextview.setText(Constants.RECITER_AUDIO_CATEGORY_LIST.get(0).getReciter_name());

        } else {
            reciternametextview.setText(PreferenceManager.getManager().getReciter());

        }
*/


            closeplayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAudioSet.dismiss();
                }
            });


//////////
            reciternametextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu menu = new PopupMenu(getActivity(), view);

                    for (int p = 0; p < reciternamelist.length; p++) {
                        menu.getMenu().add(Menu.NONE, p, p, reciternamelist[p]);
                    }
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();
                            reciternametextview.setText(item.getTitle());
                            //  PreferenceManager.getManager().setReciter(item.getTitle().toString());
                            return true;
                        }

                    });

                }
            });


////////////////////


   /*     ///play verse radio
        mPlayVerseradioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (mPlayVerseradioButton.isChecked()) {
                    mPlayTranslationRadio.setChecked(false);

                    if (audiocategory.equalsIgnoreCase("Tafsir")) {
                        audio_category = "Tafsir";
                    } else if (audiocategory.equalsIgnoreCase("Tajweed")) {
                        audio_category = "Tajweed";
                    } else {
                        audio_category = "Verse";
                    }


                } else {
//                    mPlayTranslationRadio.setChecked(false);
//                    audio_category="Translation";
                }
            }
        });*/

    /*    mPlayTranslationRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (mPlayTranslationRadio.isChecked()) {
                    mPlayVerseradioButton.setChecked(false);

                    if (audiocategory.equalsIgnoreCase("Tafsir")) {
                        audio_category = "Tafsir";
                    } else if (audiocategory.equalsIgnoreCase("Tajweed")) {
                        audio_category = "Tajweed";
                    } else {
                        audio_category = "Translation";
                    }

                } else {
                    // audio_category="Verse";
                }
            }
        });
*/

            //repeatsingle adapetr

            repeatsingleadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, repeatsinglearray);
            repeatsingleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            repeatsinglespinner.setAdapter(repeatsingleadapter);

      /*  repeatmultipleadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, repeatsinglearray);
        repeatmultipleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatmultiplespinner.setAdapter(repeatmultipleadapter);*/


            spinnerPosition = repeatsingleadapter.getPosition(PreferenceManager.getManager().getRepeatSingleValue());
            repeatsinglespinner.setSelection(spinnerPosition);

            if (PreferenceManager.getManager().getRepeatSingleValue().contains("0")) {
                Constants.ISREPEATSINGLE = false;
                repeatcount.setText("");
            } else {
            /*PreferenceManager.getManager().setRepeatMultipleValue("0");
            repeatmultiplespinner.setSelection(0);
            Constants.ISREPEATMULTIPLE = false;*/
                Constants.ISREPEATSINGLE = true;
                // repeatcount.setText("1/"+PreferenceManager.getManager().getRepeatSingleValue());

            }

       /* spinnermultiplePosition = repeatmultipleadapter.getPosition(PreferenceManager.getManager().getRepeatMultipleValue());
        repeatmultiplespinner.setSelection(spinnermultiplePosition);
        if (PreferenceManager.getManager().getRepeatMultipleValue().contains("0")) {
            Constants.ISREPEATMULTIPLE = false;
        } else {
            PreferenceManager.getManager().setRepeatSingleValue("0");
            repeatmultiplespinner.setSelection(0);
            Constants.ISREPEATMULTIPLE = true;
            Constants.ISREPEATSINGLE = false;

        }
*/
            repeatsinglespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    PreferenceManager.getManager().setRepeatSingleValue(repeatsinglearray[i]);
                    repeatcount.setText("1/"+PreferenceManager.getManager().getRepeatSingleValue());
                    if (PreferenceManager.getManager().getRepeatSingleValue().contains("0")) {
                        Constants.ISREPEATSINGLE = false;
                    }
                    else {

                  /*  PreferenceManager.getManager().setRepeatMultipleValue("0");
                    repeatmultiplespinner.setSelection(0);
                    Constants.ISREPEATMULTIPLE = false;*/
                        Constants.ISREPEATSINGLE = true;


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


      /*  repeatmultiplespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PreferenceManager.getManager().setRepeatMultipleValue(repeatsinglearray[i]);
                if (PreferenceManager.getManager().getRepeatMultipleValue().contains("0")) {
                    Constants.ISREPEATMULTIPLE = false;
                } else {
                    PreferenceManager.getManager().setRepeatSingleValue("0");
                    repeatsinglespinner.setSelection(0);
                    Constants.ISREPEATMULTIPLE = true;
                    Constants.ISREPEATSINGLE = false;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
/*
//edit text change setting
        mVerseMinLimitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.toString().length() > 0) {
                    newrangeseekbar.setSelectedMinValue(Integer.parseInt(editable.toString()));
                } else {
                    //   Toast.makeText(getApplicationContext(),"min111 ",Toast.LENGTH_SHORT).show();
                }

            }
        });

        mVerseMaxLimitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.toString().length() > 0) {
                    newrangeseekbar.setSelectedMaxValue(Integer.parseInt(editable.toString()));
                } else {
                    //   Toast.makeText(getApplicationContext(),"min111 ",Toast.LENGTH_SHORT).show();
                }


            }
        });


/////
        //rangeseekbar

        newrangeseekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {


                mVerseMinLimitEditText.setText(String.valueOf(minValue));
                mVerseMaxLimitEditText.setText(String.valueOf(maxValue));
                // Toast.makeText(getApplicationContext(),"min "+minValue+"max "+maxValue,Toast.LENGTH_SHORT).show();
            }
        });*/

////////////////////////
            mPlayAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (VerseMinLimit.equalsIgnoreCase("1")) {
                        VerseMinLimit = "0";
                    }
                    Constants.sChapterno = ChapterNo;
                    Constants.sVerseFrom = VerseMinLimit;
                    Constants.sVerseto = VerseMaxLimit;
                    Constants.sAudioCategory = audio_category;
                    Constants.sReciternames = PreferenceManager.getManager().getReciter();

                    if (Constants.player != null) {

                        dialogAudioSet.dismiss();
                        Constants.player.stop();
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.release();
                        //    Toast.makeText(context, "player state." +   Constants. player.getPlaybackState(), Toast.LENGTH_SHORT).show();
                        if (isNetworkAvailable()) {
                            progress();
                            //  mVerseDetailController.AudioServiceCall(audio_category, ChapterNo, mVerseMinLimitEditText.getText().toString(), mVerseMaxLimitEditText.getText().toString());
                            //change
                            if (!reciternametextview.getText().toString().isEmpty()) {
                                mVerseDetailController.AudioServiceCall(audio_category, reciternametextview.getText().toString(), ChapterNo, VerseMinLimit, VerseMaxLimit);

                            } else {
                                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }

                        } else {
                            mDialog.dismiss();
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        //call audio service
                        dialogAudioSet.dismiss();

                        if (isNetworkAvailable()) {
                            progress();
                            //  mVerseDetailController.AudioServiceCall(audio_category, ChapterNo, mVerseMinLimitEditText.getText().toString(), mVerseMaxLimitEditText.getText().toString());
                            if (!reciternametextview.getText().toString().isEmpty()) {
                                mVerseDetailController.AudioServiceCall(audio_category, reciternametextview.getText().toString(), ChapterNo, VerseMinLimit, VerseMaxLimit);

                            } else {
                                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }


                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }


                }
            });

            dialogAudioSet.show();
            Window window = dialogAudioSet.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogAudioSet.getWindow().setGravity(Gravity.BOTTOM);
            dialogAudioSet.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogAudioSet.getWindow().setWindowAnimations(R.style.DialogAnimation);

        }
else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fetchjuzaudio(String audiocategory, List<SpinnerchapterlistModel> spinnerchapterlistModels, String verseMinLimit, String verseMaxLimit) {

    }


    @Override
    public void fetchingaudiofailure() {
        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
        mDialog.dismiss();
    }

    @Override
    public void dataaudipassing(final List<Audio> audio) {
        if (audio != null && audio.size() > 0) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mDialog.dismiss();
                    allAudioListner.dataaudipassing(audio, "sura", audio_category);


                }
            });
        } else {
            mDialog.dismiss();
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void getVerseNo(String page, String chaptermo, String verseno) {

        if (PreferenceManager.getManager().getAutoScroll().contains("autoscrollOn")) {


            int i = 0;
            for (VerseDetail vo : verseDetailsTemp) {

                if (vo.getAyat_no().equalsIgnoreCase(verseno) && vo.getSurah_no().equalsIgnoreCase(chaptermo)) {


                    final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
                        @Override
                        protected int getVerticalSnapPreference() {
                            return LinearSmoothScroller.SNAP_TO_START;
                        }
                    };
                    smoothScroller.setTargetPosition(i);
                    mVerseDetailRecycler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layoutManager.startSmoothScroll(smoothScroller);

                            for (VerseDetail v2 : verseDetailsTemp) {
                                if (vo == v2) {
                                    v2.setSelected(true);
                                } else {
                                    v2.setSelected(false);
                                }
                            }

                            verseDetailAdapter.setItems(verseDetailsTemp);
                            verseDetailAdapter.notifyDataSetChanged();

                        }
                    }, 300);

                    return;
                }
                // vo.setSelected(false);


                i++;
            }


        } else if (PreferenceManager.getManager().getAutoScroll().contains("autoscrollOff")) {
            for (VerseDetail v2 : verseDetailsTemp) {
                v2.setSelected(false);
            }

            verseDetailAdapter.setItems(verseDetailsTemp);
            verseDetailAdapter.notifyDataSetChanged();

        }


    }

    public void datasetchanged() {
        for (VerseDetail v2 : verseDetailsTemp) {
            v2.setSelected(false);

        }

        verseDetailAdapter.setItems(verseDetailsTemp);
        verseDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void callversedetails(String page, String chapterno, String verseno, String juzno) {

    }

    @Override
    public void passVerseDetails(String page, String chaptername, String chapterno, String verseno, String juzno) {

        Bookmark bookmark = mVerseDetailController.checkBookmarkAlreadypresent(chaptername, verseno, page);
        if (bookmark == null) {
            mVerseDetailController.addbookmark(page, chaptername, chapterno, verseno);
            //show bookmark addede dialog
            showBookmarkAddedDialog();
        } else {
            Toast.makeText(getActivity(), "Bookmark exists", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void sharestring(String sharestring) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Verse");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharestring);
        startActivity(Intent.createChooser(sharingIntent, "share via"));
    }

    @Override
    public void passdetailsForNotes(String chaptername, String chapterno, String verseno) {
        ayat_no = verseno;
        mOnFragmnetSwitch.onFragmentChange(new CreateNotefragment(getActivity(), chaptername, chapterno, verseno), "NOTE VIEW", true);
    }

    @Override
    public void shownotes(String chaptername, String chapterno, String verseno) {
        ayat_no = verseno;
        mOnFragmnetSwitch.onFragmentChange(new Notesfragment(getActivity(), chaptername, chapterno, verseno, "verse"), "NOTES", true);
    }

    @Override
    public void popupdetailpage() {
        mVerseDetailRecycler.scrollToPosition(8);
    }

    @Override
    public void deletenote(int noteid, int position) {

    }

    private void showBookmarkAddedDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.bookmark_added);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        };
        // custom dialog


        handler.postDelayed(runnable, 2000);

    }
}