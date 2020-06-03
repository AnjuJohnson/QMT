package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.SettingsFragmentController;
import com.Qubicle.QMT.Listeners.SettingsListener;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteAllVerseExplanationTask;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anju on 28-08-2019.
 */
public class SettingsFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, SettingsListener {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    AppCompatSeekBar seekBar, mArabicSeekbar;
    TextView malayalamTextView, mTranslationTextView, mArabicTextView;
    AppCompatSpinner mTranslationSpinner, mReciterSpinner;
    String[] translation = {"Cheriya Mundam/Parappoor", "Amani Thafseer"};
    Boolean mAutoScroll = true, mVerse = true, mWordMeaning = true, mTranslation = true;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> reciteradapter;
    ImageView mAutoScrollImageView, syncImageview, mVerseImageView, mTranslationImageView;
    private int spinnerPosition;
    SettingsFragmentController settingsFragmentController;
    String[] reciternamelist = new String[Constants.RECITER_LIST.size()];
    Dialog mDialog;
    private int max = 2000, min = 0;
    private int count = 1;
    private int maxlimit=2000;

    public SettingsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        seekBar = mFragmentView.findViewById(R.id.malayalamSeekbar);
        mArabicSeekbar = mFragmentView.findViewById(R.id.mArabicSeekbar);
        malayalamTextView = mFragmentView.findViewById(R.id.malayalamTextView);
        mArabicTextView = mFragmentView.findViewById(R.id.mArabicTextView);
        mTranslationSpinner = mFragmentView.findViewById(R.id.mTranslationSpinner);
        mReciterSpinner = mFragmentView.findViewById(R.id.mReciterSpinner);
        syncImageview = mFragmentView.findViewById(R.id.syncImageview);
        settingsFragmentController = new SettingsFragmentController(getActivity(), SettingsFragment.this);


        //    mAutoScrollImageView = mFragmentView.findViewById(R.id.mAutoScrollImageView);
        //   mWordMeaningImageView = mFragmentView.findViewById(R.id.mWordMeaningImageView);
        mVerseImageView = mFragmentView.findViewById(R.id.mVerseImageView);
        mTranslationImageView = mFragmentView.findViewById(R.id.mTranslationImageView);
        //   mAutoScrollImageView.setOnClickListener(this);
        //   mWordMeaningImageView.setOnClickListener(this);
        mVerseImageView.setOnClickListener(this);
        mTranslationImageView.setOnClickListener(this);
        mTranslationSpinner.setOnItemSelectedListener(this);
        syncImageview.setOnClickListener(this);
        configViews();
    }

    private void configViews() {
        if (Constants.RECITER_LIST != null) {

            for (int i = 0; i < Constants.RECITER_LIST.size(); i++) {
                reciternamelist[i] = Constants.RECITER_LIST.get(i).getName();
            }

//reciter
            reciteradapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, reciternamelist);
            reciteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mReciterSpinner.setAdapter(reciteradapter);


            if (PreferenceManager.getManager().getReciter().equalsIgnoreCase("DEFAULT")) {
                PreferenceManager.getManager().setReciter(Constants.RECITER_LIST.get(0).getName());
                mReciterSpinner.setSelection(0);

            } else {

                spinnerPosition = reciteradapter.getPosition(PreferenceManager.getManager().getReciter());
                mReciterSpinner.setSelection(spinnerPosition);

            }
            mReciterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    PreferenceManager.getManager().setReciter(reciternamelist[i]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }

        //translation
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, translation);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTranslationSpinner.setAdapter(adapter);

        if (PreferenceManager.getManager().getTranslation().equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
            PreferenceManager.getManager().setTranslation(translation[0]);

            mTranslationSpinner.setSelection(0);

        } else if (PreferenceManager.getManager().getTranslation().contains("Cheriya Mundam/Parappoor")) {
            PreferenceManager.getManager().setTranslation(translation[0]);

            mTranslationSpinner.setSelection(0);

        } else if (PreferenceManager.getManager().getTranslation().contains("Amani Thafseer")) {
            PreferenceManager.getManager().setTranslation(translation[1]);
            mTranslationSpinner.setSelection(1);

        }


/*//autoscroll

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
        }*/
//////////////
/*//word meaning
        if (PreferenceManager.getManager().getWordMeaning().equalsIgnoreCase("DEFAULT")) {
            mWordMeaningImageView.setImageResource(R.drawable.on);
            PreferenceManager.getManager().setWordMeaning("wordmeaningOn");

            mWordMeaning = true;
        } else if (PreferenceManager.getManager().getWordMeaning().contains("wordmeaningOn")) {
            mWordMeaningImageView.setImageResource(R.drawable.on);
            PreferenceManager.getManager().setWordMeaning("wordmeaningOn");

            mWordMeaning = true;
        } else if (PreferenceManager.getManager().getWordMeaning().contains("wordmeaningOff")) {
            mWordMeaningImageView.setImageResource(R.drawable.off);
            PreferenceManager.getManager().setWordMeaning("wordmeaningOff");

            mWordMeaning = false;
        }*/
        ///////////
        if (PreferenceManager.getManager().getVerse().equalsIgnoreCase("DEFAULT")) {
            mVerseImageView.setImageResource(R.drawable.on);
            PreferenceManager.getManager().setVerse("verseOn");

            mVerse = true;
        } else if (PreferenceManager.getManager().getVerse().contains("verseOn")) {
            mVerseImageView.setImageResource(R.drawable.on);
            PreferenceManager.getManager().setVerse("verseOn");

            mVerse = true;
        } else if (PreferenceManager.getManager().getVerse().contains("verseOff")) {
            mVerseImageView.setImageResource(R.drawable.off);
            PreferenceManager.getManager().setVerse("verseOff");

            mVerse = false;
        }
        ////////////
        if (PreferenceManager.getManager().gettranslationOn().equalsIgnoreCase("DEFAULT")) {
            mTranslationImageView.setImageResource(R.drawable.on);
            PreferenceManager.getManager().settranslationOn("translationOn");

            mTranslation = true;
        } else if (PreferenceManager.getManager().gettranslationOn().contains("translationOn")) {
            mTranslationImageView.setImageResource(R.drawable.on);
            PreferenceManager.getManager().settranslationOn("translationOn");
            mTranslation = true;
        } else if (PreferenceManager.getManager().gettranslationOn().contains("translationOff")) {
            mTranslationImageView.setImageResource(R.drawable.off);
            PreferenceManager.getManager().settranslationOn("translationOff");
            mTranslation = false;
        }


        ////////////
        if (PreferenceManager.getManager().getMalayalamFont().equalsIgnoreCase("DEFAULT")) {
            malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            seekBar.setProgress(seekBar.getMax() / 2);
            PreferenceManager.getManager().setMalayalamFont(String.valueOf(seekBar.getMax() / 2));
        } else {
            int font = Integer.parseInt(PreferenceManager.getManager().getMalayalamFont());
            seekBar.setProgress(font);
            setfontmalayalam(font, "malayalam");
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                PreferenceManager.getManager().setMalayalamFont(String.valueOf(progress));
                System.out.println("font" + Integer.valueOf(progress));
                setfontmalayalam(progress, "malayalam");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if (PreferenceManager.getManager().getArabicFont().equalsIgnoreCase("DEFAULT")) {
            mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            mArabicSeekbar.setProgress(mArabicSeekbar.getMax() / 2);
            PreferenceManager.getManager().setArabicFont(String.valueOf((mArabicSeekbar.getMax() / 2)));
        } else {
            int afont = Integer.parseInt(PreferenceManager.getManager().getArabicFont());
            mArabicSeekbar.setProgress(afont);
            setfontmalayalam(afont, "arabic");
        }
        mArabicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressarabic, boolean b) {
                PreferenceManager.getManager().setArabicFont(String.valueOf(progressarabic));
                System.out.println("font" + Integer.valueOf(progressarabic));
                setfontmalayalam(progressarabic, "arabic");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setfontmalayalam(Integer progress, String langauage) {
        if (progress == 1) {

            if (langauage.equalsIgnoreCase("malayalam")) {
                malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            } else if (langauage.equalsIgnoreCase("arabic")) {
                mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            }

        } else if (progress == 2) {

            if (langauage.equalsIgnoreCase("malayalam")) {
                malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            } else if (langauage.equalsIgnoreCase("arabic")) {
                mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }

        } else if (progress == 3) {
            if (langauage.equalsIgnoreCase("malayalam")) {
                malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            } else if (langauage.equalsIgnoreCase("arabic")) {
                mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
        } else if (progress == 4) {
            if (langauage.equalsIgnoreCase("malayalam")) {
                malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            } else if (langauage.equalsIgnoreCase("arabic")) {
                mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        } else if (progress == 5) {
            if (langauage.equalsIgnoreCase("malayalam")) {
                malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            } else if (langauage.equalsIgnoreCase("arabic")) {
                mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        } else if (progress == 6) {
            if (langauage.equalsIgnoreCase("malayalam")) {
                malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            } else if (langauage.equalsIgnoreCase("arabic")) {
                mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
        } else if (progress == 0) {
            if (langauage.equalsIgnoreCase("malayalam")) {
                malayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            } else if (langauage.equalsIgnoreCase("arabic")) {
                mArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("SETTINGS");
        OnMenuIconChange.iconchange(SettingsFragment.this);
        HomeActivity.fragment = SettingsFragment.this;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        PreferenceManager.getManager().setTranslation(translation[i]);
        System.out.println("itemselected" + PreferenceManager.getManager().getTranslation());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
       /* if (view == mAutoScrollImageView) {
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
        }*/ /*else if (view == mWordMeaningImageView) {
            if (mWordMeaning) {
                if((!mVerse)&&(!mTranslation)){
                    Toast.makeText(getActivity(),"Turn On any Reading settings",Toast.LENGTH_LONG).show();

                }
                else {
                    mWordMeaningImageView.setImageResource(R.drawable.off);
                    PreferenceManager.getManager().setWordMeaning("wordmeaningOff");
                    mWordMeaning = false;
                    System.out.println(PreferenceManager.getManager().getWordMeaning());
                }

            } else {
                mWordMeaningImageView.setImageResource(R.drawable.on);
                PreferenceManager.getManager().setWordMeaning("wordmeaningOn");
                mWordMeaning = true;
                System.out.println(PreferenceManager.getManager().getWordMeaning());
            }
        }*/
        if (view == mVerseImageView) {
            if (mVerse) {

                if (!mTranslation) {
                    Toast.makeText(getActivity(), "Turn On any Reading settings", Toast.LENGTH_LONG).show();

                } else {
                    mVerseImageView.setImageResource(R.drawable.off);
                    PreferenceManager.getManager().setVerse("verseOff");
                    mVerse = false;
                    System.out.println(PreferenceManager.getManager().getVerse());
                }

            } else {
                mVerseImageView.setImageResource(R.drawable.on);
                PreferenceManager.getManager().setVerse("verseOn");
                mVerse = true;
                System.out.println(PreferenceManager.getManager().getVerse());
            }
        } else if (view == mTranslationImageView) {
            if (mTranslation) {
                if (!mVerse) {
                    Toast.makeText(getActivity(), "Turn On any Reading settings", Toast.LENGTH_LONG).show();

                } else {
                    mTranslationImageView.setImageResource(R.drawable.off);
                    PreferenceManager.getManager().settranslationOn("translationOff");
                    mTranslation = false;
                    System.out.println(PreferenceManager.getManager().gettranslationOn());
                }


            } else {
                mTranslationImageView.setImageResource(R.drawable.on);
                PreferenceManager.getManager().settranslationOn("translationOn");
                mTranslation = true;
                System.out.println(PreferenceManager.getManager().gettranslationOn());
            }
        } else if (view == syncImageview) {
            if (isNetworkAvailable()) {
                progress();
             //verse detail
                settingsFragmentController.startfetching();
            } else {
                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void fetchingfailure() {
        mDialog.dismiss();
        Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT);
    }

    @Override
    public void progress() {
       // Toast.makeText(getActivity(), "This will take several minutes", Toast.LENGTH_LONG);


        if(mDialog==null){
            mDialog = Utility.showProgressBarWithTitle(getActivity());
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

    }


    @Override
    public void successVerseDetail() {
       // mDialog.dismiss();
        Toast.makeText(getActivity(), "versed saved", Toast.LENGTH_SHORT).show();
        if (isNetworkAvailable()) {
          //  progress();
            settingsFragmentController.startfetchingChapterExplanation();


        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void successChapterExplanation() {
        count=1;
       // mDialog.dismiss();
        Toast.makeText(getActivity(), "Chapter Explanation saved", Toast.LENGTH_SHORT).show();
        if (isNetworkAvailable()) {
            // progress();

            settingsFragmentController.startfetchingVerseExplanation(String.valueOf(min), String.valueOf(max));
            count = count + 1;
        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void successVerseExplanation() {

        //check if maxlimit reach 6000
        if (max < 8000) {

            if (isNetworkAvailable()) {
                progress();

                if (count < 5) {

                    min = max + 1;
                    max = maxlimit * count;
                    settingsFragmentController.startfetchingVerseExplanation(String.valueOf(min), String.valueOf(max));
                    count = count + 1;
                }
                else {
                    mDialog.dismiss();
                }

            } else {
                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
            }


        } else {

         //   Toast.makeText(getActivity(), "Sync Completed", Toast.LENGTH_SHORT).show();

            Log.d(" sync", "complete");

            //check if there is any data
            List<JuzDetailNew> juzDetailNewList=settingsFragmentController.checkdatapresentAlljuz();

            if (juzDetailNewList.size() != 0&&juzDetailNewList!=null) {

                int delete = settingsFragmentController.deletealljuzdetails();
                if (delete == 1) {
                    settingsFragmentController.startfetchingJuzDetail();

                }
            }
            else {
                settingsFragmentController.startfetchingJuzDetail();
            }
        }

    }

    @Override
    public void successJuzDetail() {
        mDialog.dismiss();
        Log.d(" sync", "complete");
        Toast.makeText(getActivity(), "Sync Complete", Toast.LENGTH_SHORT).show();
    }

}

