package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.Qubicle.QMT.Adapter.JuzDetailAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.JuzDetailFragmentController;
import com.Qubicle.QMT.Listeners.AllAudioListner;
import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.JuzListener;
import com.Qubicle.QMT.Listeners.ReciterListner;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailAdapterModel;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.ReciterNew;
import com.Qubicle.QMT.Models.SpinnerchapterlistModel;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.WordMeaningList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.RangeSeekBar;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class JuzDetailfragment extends BaseFragment implements View.OnClickListener, JuzListener, AudioListner, BookmarkListner, ReciterListner {
   public static String malayalamFont, arabicfont;
    Context context;
    ImageView mImageview;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    String juzno,bookmarkpage,chapterno,verseno;
    JuzDetailFragmentController mJuzDetailController;
    RecyclerView mVerseDetailRecycler;
    Dialog mDialog, dialogAudioSet;
    WordMeaningList wordMeaningList = new WordMeaningList();
    public static String juz_no;
    RecyclerView.LayoutManager layoutManager;
    JuzDetailAdapter verseDetailAdapter;
    String[] repeatsinglearray = {"0", "1", "2", "3", "4", "5","6","7","8","9","10"};
    int i = 0;
    String VerseFrom, VerseTo;
    AllAudioListner allAudioListner;
    String chapterFrom, chapterTo;
    List<JuzDetailAdapterModel> juzDetailAdapterModellist = new ArrayList<>();
    public static String sVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, sTranslationNumber;
    List<AudioCategoryReciterList> reciterAudioCategoryList = new ArrayList<>();
    String audio_category ;
    Boolean mAutoScroll = true;
    public static List<AudioCategoryReciterList> reciterlist;

    public JuzDetailfragment(Context context, String juzno,String bookmarkpage,String chapterno,String verseno) {
        this.context = context;
        this.juzno = juzno;
        this.bookmarkpage = bookmarkpage;
        this.chapterno = chapterno;
        this.verseno = verseno;


        //setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_versedetail, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        sTranslationNumber = PreferenceManager.getManager().getTranslation();
        sVerseRadioButton = PreferenceManager.getManager().getVerse();
        sTranslationRadioButton = PreferenceManager.getManager().gettranslationOn();
        sWordMeaningRadioButton = PreferenceManager.getManager().getWordMeaning();
        malayalamFont = PreferenceManager.getManager().getMalayalamFont();
        arabicfont = PreferenceManager.getManager().getArabicFont();
        configviews(mFragmentView);
    }

    private void configviews(View mFragmentView) {
        PreferenceManager.getManager().setReadingPage("juzpage");
        mJuzDetailController = new JuzDetailFragmentController(getActivity(), JuzDetailfragment.this, JuzDetailfragment.this,JuzDetailfragment.this);
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        allAudioListner = (AllAudioListner) getActivity();
        mVerseDetailRecycler = mFragmentView.findViewById(R.id.mVerseDetailRecycler);
        mVerseDetailRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mVerseDetailRecycler.setLayoutManager(layoutManager);



        List<JuzDetailNew> juzDetailList = mJuzDetailController.checkdatapresent(juzno);

        if (juzDetailList == null || juzDetailList.isEmpty()) {
            if (isNetworkAvailable()) {
                progress();
                mJuzDetailController.startfetching(juzno);
            }
        }
        else {
            datapassingJuzDetail(juzDetailList);
        }






       /* if (isNetworkAvailable()) {
            progress();
            mJuzDetailController.startfetching(juzno);
        } else {
            List<JuzDetailNew> juzDetailList = mJuzDetailController.checkdatapresent(juzno);

            if (juzDetailList == null || juzDetailList.isEmpty()) {
                Toast.makeText(getActivity(), "No Data Loaded", Toast.LENGTH_SHORT).show();
            }
            else {
                datapassingJuzDetail(juzDetailList);
            }
        }

*/
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("JUZ");
        OnMenuIconChange.iconchange(JuzDetailfragment.this);
        HomeActivity.fragment = JuzDetailfragment.this;
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
        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
        if(mDialog!=null){
            mDialog.dismiss();
        }

    }


    @Override
    public void fetchaudio(final String audiocategory,String chaptername, String chaptername_meaning, String chapterNo, String verseMinLimit, String verseMaxLimit) {

    }

    @Override
    public void fetchjuzaudio(String audiocategory,List<SpinnerchapterlistModel> spinnerchapterlistModels, String verseMinLimit, String verseMaxLimit) {
        //show playerselectiondialog

        if(isNetworkAvailable()) {

            dialogAudioSet = new Dialog(context);
            dialogAudioSet.setContentView(R.layout.audio_juz_dailog_fragment);
            dialogAudioSet.setCanceledOnTouchOutside(false);
            ImageView mAutoScrollImageView = dialogAudioSet.findViewById(R.id.mAutoScrollImageView);
            ImageView closeplayer = (ImageView) dialogAudioSet.findViewById(R.id.closeplayer);
      /*  RadioButton mPlayVerseradioButton = (RadioButton) dialogAudioSet.findViewById(R.id.mPlayVerseradioButton);
        RadioButton mPlayTranslationRadio = (RadioButton) dialogAudioSet.findViewById(R.id.mPlayTranslationRadio);*/
            ArrayAdapter<String> repeatsingleadapter;
            ArrayAdapter<String> repeatmultipleadapter;
            Spinner repeatsinglespinner = (Spinner) dialogAudioSet.findViewById(R.id.repeatsinglespinner);
            //Spinner repeatmultiplespinner = (Spinner) dialogAudioSet.findViewById(R.id.repeatmultiplespinner);
            final RangeSeekBar newrangeseekbar = (RangeSeekBar) dialogAudioSet.findViewById(R.id.rangeSeekbar);
            TextView reciternametextview = (TextView) dialogAudioSet.findViewById(R.id.reciternametextview);
            //  Spinner chapterTospinner = (Spinner) dialogAudioSet.findViewById(R.id.ChapterTospinner);
            //   AppCompatSpinner mReciterSpinner = (AppCompatSpinner) dialogAudioSet.findViewById(R.id.mReciterSpinner);
            int spinnerPosition;
            int spinnermultiplePosition;
            int spinnerReciterPosition;
            TextView repeatcount = (TextView) dialogAudioSet.findViewById(R.id.repeatcount);
            ImageView mPlayAudio = (ImageView) dialogAudioSet.findViewById(R.id.mPlayAudio);
            ImageView From_popupimageView = (ImageView) dialogAudioSet.findViewById(R.id.From_imageView);
            ImageView To_popupimageView = (ImageView) dialogAudioSet.findViewById(R.id.To_imageView);
            final EditText mVerseMinLimitEditText = (EditText) dialogAudioSet.findViewById(R.id.mVerseMinLimitEditText);
            final EditText mVerseMaxLimitEditText = (EditText) dialogAudioSet.findViewById(R.id.mVerseMaxLimitEditText);
            final TextView chapterfrom_textview = (TextView) dialogAudioSet.findViewById(R.id.chapterfrom_textview);
            final TextView chapterto_textView = (TextView) dialogAudioSet.findViewById(R.id.chapterto_textView);
            ImageView reciterImage = (ImageView) dialogAudioSet.findViewById(R.id.reciterImage);
            ///setting data
            chapterfrom_textview.setText(spinnerchapterlistModels.get(0).getChapter_name());
            chapterto_textView.setText(spinnerchapterlistModels.get(spinnerchapterlistModels.size() - 1).getChapter_name());
            chapterFrom = spinnerchapterlistModels.get(0).getSurah_no();
            chapterTo = spinnerchapterlistModels.get(spinnerchapterlistModels.size() - 1).getSurah_no();
            //   mChapterMeaningTextView.setText(ChapterNameMeaning);
        /*mPlayTranslationRadio.setEnabled(false);
        mPlayVerseradioButton.setChecked(true);*/
            mVerseMinLimitEditText.setText(verseMinLimit);
            mVerseMaxLimitEditText.setText(verseMaxLimit);
            newrangeseekbar.setRangeValues(Integer.parseInt(verseMinLimit), Integer.parseInt(verseMaxLimit));

            ArrayAdapter<String> reciteradapter;
            ArrayAdapter<String> chapterfromadapter;

            //for tafsir


            audio_category=audiocategory;
//////////////////////////////////////////////////////////

            Constants.RECITER_AUDIO_CATEGORY_LIST = new ArrayList<>();
            for (AudioCategoryReciterList audioCategoryReciterList : reciterlist) {
                if (audioCategoryReciterList.getAudio_category().equalsIgnoreCase(audio_category)) {

                    Constants.RECITER_AUDIO_CATEGORY_LIST = audioCategoryReciterList.getReciter_list();

                }

            }





      /*  reciterAudioCategoryList = mJuzDetailController.checkreciterAudioCategorypresent();
        if (reciterAudioCategoryList == null || reciterAudioCategoryList.size() == 0) {

        } else {
            Constants.RECITER_AUDIO_CATEGORY_LIST = new ArrayList<>();
            for (AudioCategoryReciterList audioCategoryReciterList : reciterAudioCategoryList) {
                if (audioCategoryReciterList.getAudio_category().equalsIgnoreCase(audio_category)) {

                    Constants.RECITER_AUDIO_CATEGORY_LIST = audioCategoryReciterList.getReciter_list();


                }

            }
        }*/

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


/////////

            From_popupimageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu menu = new PopupMenu(getActivity(), view);

                    for (int p = 0; p < spinnerchapterlistModels.size(); p++) {
                        menu.getMenu().add(Menu.NONE, Integer.parseInt(spinnerchapterlistModels.get(p).getSurah_no()), Integer.parseInt(spinnerchapterlistModels.get(p).getSurah_no()), spinnerchapterlistModels.get(p).getChapter_name());
                    }
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();
                            chapterfrom_textview.setText(item.getTitle());
                            chapterFrom = String.valueOf(id);
                            return true;
                        }

                    });

                }
            });

            To_popupimageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu menu = new PopupMenu(getActivity(), view);

                    for (int p = 0; p < spinnerchapterlistModels.size(); p++) {
                        menu.getMenu().add(Menu.NONE, Integer.parseInt(spinnerchapterlistModels.get(p).getSurah_no()), Integer.parseInt(spinnerchapterlistModels.get(p).getSurah_no()), spinnerchapterlistModels.get(p).getChapter_name());
                    }
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();
                            chapterto_textView.setText(item.getTitle());
                            chapterTo = String.valueOf(id);
                            return true;
                        }

                    });

                }
            });

////////
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


//reciter

      /*  if (PreferenceManager.getManager().getReciter().equalsIgnoreCase("DEFAULT")) {
            PreferenceManager.getManager().setReciter(Constants.RECITER_LIST.get(0).getName());
           // mReciterSpinner.setSelection(0);
            reciternametextview.setText(Constants.RECITER_LIST.get(0).getName());


        } else {
            reciternametextview.setText(PreferenceManager.getManager().getReciter());

        }*/
///////////////////////////
            closeplayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAudioSet.dismiss();
                }
            });


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
                            // PreferenceManager.getManager().setReciter(item.getTitle().toString());
                            return true;
                        }

                    });

                }
            });
            //////////////


            //repeatsingle adapetr

            repeatsingleadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, repeatsinglearray);
            repeatsingleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            repeatsinglespinner.setAdapter(repeatsingleadapter);

       /* repeatmultipleadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, repeatsinglearray);
        repeatmultipleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatmultiplespinner.setAdapter(repeatmultipleadapter);*/


            spinnerPosition = repeatsingleadapter.getPosition(PreferenceManager.getManager().getRepeatSingleValue());
            repeatcount.setText(PreferenceManager.getManager().getRepeatSingleValue()+"/5");
            repeatsinglespinner.setSelection(spinnerPosition);
            if (PreferenceManager.getManager().getRepeatSingleValue().contains("0")) {
                Constants.ISREPEATSINGLE = false;
            } else {
          /*  PreferenceManager.getManager().setRepeatMultipleValue("0");
            repeatmultiplespinner.setSelection(0);
            Constants.ISREPEATMULTIPLE = false;*/
                Constants.ISREPEATSINGLE = true;


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

        }*/

            repeatsinglespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    PreferenceManager.getManager().setRepeatSingleValue(repeatsinglearray[i]);
                    repeatcount.setText("1/"+PreferenceManager.getManager().getRepeatSingleValue());
                    if (PreferenceManager.getManager().getRepeatSingleValue().contains("0")) {
                        Constants.ISREPEATSINGLE = false;
                    } else {

                   /* PreferenceManager.getManager().setRepeatMultipleValue("0");
                    repeatmultiplespinner.setSelection(0);
                    Constants.ISREPEATMULTIPLE = false;*/
                        Constants.ISREPEATSINGLE = true;


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

/*

        repeatmultiplespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });
*/


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

            newrangeseekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {


                    mVerseMinLimitEditText.setText(String.valueOf(minValue));
                    mVerseMaxLimitEditText.setText(String.valueOf(maxValue));
                    // Toast.makeText(getApplicationContext(),"min "+minValue+"max "+maxValue,Toast.LENGTH_SHORT).show();
                }
            });


////////////////////////
            mPlayAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String verselimit;

                    if(mVerseMinLimitEditText.getText().toString().equalsIgnoreCase("1")){
                        verselimit="0";
                    }
                    else {
                        verselimit=mVerseMinLimitEditText.getText().toString();
                    }




                    Constants.sChapterFrom = chapterFrom;
                    Constants.sChapterTo = chapterTo;
                    Constants.sVerseFrom = verselimit;
                    Constants.sVerseto =  mVerseMaxLimitEditText.getText().toString();
                    Constants.sAudioCategory = audio_category;
                    Constants.sReciternames= PreferenceManager.getManager().getReciter();

                    if (Constants.player != null) {
                        dialogAudioSet.dismiss();
                        Constants.player.stop();
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.release();
                        //    Toast.makeText(context, "player state." +   Constants. player.getPlaybackState(), Toast.LENGTH_SHORT).show();
                        if (isNetworkAvailable()) {
                            progress();
                            //change
                            if (!reciternametextview.getText().toString().isEmpty()) {
                                mJuzDetailController.AudioServiceCall(audio_category,reciternametextview.getText().toString(),chapterFrom, chapterTo, verselimit, mVerseMaxLimitEditText.getText().toString());

                            }
                            else {
                                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }

                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        //call audio service
                        dialogAudioSet.dismiss();

                        if (isNetworkAvailable()) {
                            progress();

                            if (!reciternametextview.getText().toString().isEmpty()) {
                                mJuzDetailController.AudioServiceCall(audio_category,reciternametextview.getText().toString(),chapterFrom, chapterTo,verselimit, mVerseMaxLimitEditText.getText().toString());

                            }
                            else {
                                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }

                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
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
    public void fetchingaudiofailure() {

    }

    @Override
    public void progress() {
        mDialog = Utility.showProgressBar(getActivity());
        mDialog.show();
    }

    @Override
    public void dataReciterpassing(List<Reciter> audio) {

    }

    @Override
    public void dataReciterAudiopassing(List<AudioCategoryReciterList> audio) {
        reciterlist=audio;
    }

    @Override
    public void dataaudipassing(List<Audio> audio) {

        if (audio != null && audio.size() > 0) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mDialog.dismiss();
                    //call interface method in activity

                    allAudioListner.juzdataaudipassing(audio,"juz",audio_category);

                }
            });

        } else {
            mDialog.dismiss();
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void datapassing(List<JuzList> juzLists) {

    }

    @Override
    public void fetchjuzdetails(String juzno) {

    }

    @Override
    public void datapassingJuzDetail(final List<JuzDetailNew> juzDetailsList) {
        mJuzDetailController.callReciterAudioCategoryService();
        juzDetailAdapterModellist = new ArrayList<>();


      /*  final ArrayList<WordMeaningList> list=new ArrayList<WordMeaningList>();
        final List<VerseDetail> verseDetailsTemp=new ArrayList();

         wordMeaningList=new WordMeaningList();
        wordMeaningList.setMeaning_arabic("ٱلرَّحِيمِ");
        wordMeaningList.setMeaning_mala("പരമകാരുണികനും");
        list.add(wordMeaningList);

        wordMeaningList=new WordMeaningList();
        wordMeaningList.setMeaning_arabic("ٱلرَّحۡمَٰنِ");
        wordMeaningList.setMeaning_mala("കരുണാനിധിയുമായ");
        list.add(wordMeaningList);


        wordMeaningList=new WordMeaningList();
        wordMeaningList.setMeaning_arabic("ٱللَّهِ");
        wordMeaningList.setMeaning_mala("അല്ലാഹുവിൻ്റെ");
        list.add(wordMeaningList);


        wordMeaningList=new WordMeaningList();
        wordMeaningList.setMeaning_arabic("بِسۡمِ");
        wordMeaningList.setMeaning_mala("നാമത്തിൽ");
        list.add(wordMeaningList);


*/
        getActivity().runOnUiThread(new Runnable() {
            public void run() {

             /*   for(VerseDetail verseDetail:verseDetailsList ){
                    verseDetail.setWordMeaningLists(list);
                    verseDetail.setSurah_no(verseDetail.getSurah_no());
                    verseDetail.setAyat_no(verseDetail.getAyat_no());
                    verseDetail.setMeaning_arabic(verseDetail.getMeaning_arabic());
                    verseDetail.setMeaning_malayalam(verseDetail.getMeaning_malayalam());
                    verseDetail.setWord_meaning(verseDetail.getWord_meaning());
                    verseDetail.setAudio(verseDetail.getAudio());
                    verseDetail.setAudio_title(verseDetail.getAudio_title());
                    verseDetailsTemp.add(verseDetail);

                }
*/

                //set list to populate


                for (int i = 0; i < juzDetailsList.size(); i++) {

                    for (int j = 0; j < juzDetailsList.get(i).getVerse().size(); j++) {
                        JuzDetailAdapterModel juzDetailAdapterModel = new JuzDetailAdapterModel();
                        juzDetailAdapterModel.setSurah_no(juzDetailsList.get(i).getSurah_no());

                        juzDetailAdapterModel.setChapter_name_meaning(juzDetailsList.get(i).getChapter_name_meaning());
                        juzDetailAdapterModel.setSelected(false);
                        juzDetailAdapterModel.setAyat_no(juzDetailsList.get(i).getVerse().get(j).getAyat_no());
                        juzDetailAdapterModel.setMeaning_arabic(juzDetailsList.get(i).getVerse().get(j).getMeaning_arabic());
                        juzDetailAdapterModel.setMeaning_malayalam_new(juzDetailsList.get(i).getVerse().get(j).getMeaning_malayalam_new());
                        juzDetailAdapterModel.setMeaning_malayalam(juzDetailsList.get(i).getVerse().get(j).getMeaning_malayalam());
                        juzDetailAdapterModel.setJuzno(juzno);
                        juzDetailAdapterModel.setChapternameAll(juzDetailsList.get(i).getChapter_name());

                        //to add chaptername

                        if(j==0){
                            juzDetailAdapterModel.setChapter_name(juzDetailsList.get(i).getChapter_name());
                        }
                        else {
                            juzDetailAdapterModel.setChapter_name(" ");
                        }


                        juzDetailAdapterModellist.add(juzDetailAdapterModel);
                    }

                }



                //check if favourite

                List<Favorites> favoriteslist = mJuzDetailController.getAllFavourites();
                if (favoriteslist != null && favoriteslist.size() != 0) {
                    for (Favorites favorites : favoriteslist) {
                        for (JuzDetailAdapterModel verseDetail : juzDetailAdapterModellist) {

                            if ((favorites.getSurah_no().equalsIgnoreCase(verseDetail.getSurah_no())) && (favorites.getAyat_no()
                                    .equalsIgnoreCase(verseDetail.getAyat_no()))) {
                                verseDetail.setFavourites(true);

                            }
                            else {
                                //   verseDetail.setFavourites(false);
                            }
                        }

                    }

                }

                verseDetailAdapter = new JuzDetailAdapter(JuzDetailfragment.this,juzDetailsList, JuzDetailfragment.this, JuzDetailfragment.this, juzDetailAdapterModellist, getActivity(),
                        sVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, malayalamFont, arabicfont,sTranslationNumber);
                mVerseDetailRecycler.setAdapter(verseDetailAdapter);


            }
        });

        if(bookmarkpage.equalsIgnoreCase("bookmark")){
            callbookmarkverse(chapterno,verseno);
        }

    }

    public void getVerseNo(String chaptermo, String verseno) {
        if (PreferenceManager.getManager().getAutoScroll().contains("autoscrollOn")) {
            int i = 0;
            for (JuzDetailAdapterModel vo : juzDetailAdapterModellist) {

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
//new highlighting

                            for (JuzDetailAdapterModel v2 : juzDetailAdapterModellist) {
                                if (vo == v2) {
                                    v2.setSelected(true);
                                } else {
                                    v2.setSelected(false);
                                }
                            }

                            verseDetailAdapter.setItems(juzDetailAdapterModellist);
                            verseDetailAdapter.notifyDataSetChanged();




                        }
                    }, 300);
                    return;
                }
                i++;
            }



        }
        else if (PreferenceManager.getManager().getAutoScroll().contains("autoscrollOff")) {
            for (JuzDetailAdapterModel v2 : juzDetailAdapterModellist) {
                v2.setSelected(false);
            }

            verseDetailAdapter.setItems(juzDetailAdapterModellist);
            verseDetailAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void verse_explanation(String chapterid, String ayatno) {
        mOnFragmnetSwitch.onFragmentChange(new VerseExplanationFragment(getActivity(), chapterid, ayatno, malayalamFont), "VERSE EXPLANATION", true);
    }

    @Override
    public void addtofavourites(String chaptername, String chapterno, String ayatno, String verse, String translation) {
        for (JuzDetailAdapterModel v2 : juzDetailAdapterModellist) {
            if ((ayatno.equalsIgnoreCase(v2.getAyat_no())) && (chapterno.equalsIgnoreCase(v2.getSurah_no()))) {
                if (v2.getFavourites()) {
                    v2.setFavourites(false);
                    //delete from db

                    Favorites favorites = mJuzDetailController.checkFavouritespresent(chapterno, ayatno);
                    if (favorites == null) {
                        //    Toast.makeText(getActivity(), "No Notes", Toast.LENGTH_LONG).show();
                    } else {
                        mJuzDetailController.deletefav(favorites);
                        Toast.makeText(getActivity(), "unfavourite", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    //add to db
                    v2.setFavourites(true);
                    mJuzDetailController.addFavourites(chaptername, chapterno, ayatno, verse, translation);
                    Toast.makeText(getActivity(), "favourite", Toast.LENGTH_SHORT).show();
                }

            } else {
                //  v2.setWordmeaning(false);
            }
        }

        verseDetailAdapter.setItems(juzDetailAdapterModellist);
        verseDetailAdapter.notifyDataSetChanged();
    }

    public void datasetchanged(){
        for (JuzDetailAdapterModel v2 : juzDetailAdapterModellist) {
            v2.setSelected(false);

        }

        verseDetailAdapter.setItems(juzDetailAdapterModellist);
        verseDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void callversedetails(String page, String chapterno, String verseno,String juzno) {

    }

    @Override
    public void passVerseDetails(String page, String chaptername, String chapterno, String verseno,String juzno) {
        Bookmark bookmark = mJuzDetailController.checkBookmarkAlreadypresent(chaptername,verseno,page);
        if (bookmark == null) {
            mJuzDetailController.addbookmark(page,chaptername,chapterno,verseno,juzno);
            showBookmarkAddedDialog();
        }
        else {
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
        mOnFragmnetSwitch.onFragmentChange(new CreateNotefragment(getActivity(),chaptername,chapterno,verseno),"NOTE VIEW",true);
    }

    @Override
    public void shownotes(String chaptername, String chapterno, String verseno) {
        mOnFragmnetSwitch.onFragmentChange(new Notesfragment(getActivity(),chaptername,chapterno,verseno,"verse"),"NOTEs",true);
    }

    @Override
    public void popupdetailpage() {

    }

    @Override
    public void deletenote(int noteid,int position) {

    }


    private void callbookmarkverse(String chaptermo, String verseno){
        int i = 0;
        for (JuzDetailAdapterModel vo : juzDetailAdapterModellist) {

            if (vo.getAyat_no().equalsIgnoreCase(verseno) && vo.getSurah_no().equalsIgnoreCase(chaptermo)) {


                mVerseDetailRecycler.scrollToPosition(i);

                return;
            }
            i++;
        }
    }
    private void showBookmarkAddedDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.bookmark_added);
        dialog. getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        final Handler handler  = new Handler();
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