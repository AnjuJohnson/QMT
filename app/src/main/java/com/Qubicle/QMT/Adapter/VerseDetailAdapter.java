package com.Qubicle.QMT.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.WordMeaningListNew;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.RtlGridLayoutManager;

import java.util.List;

/**
 * Created by Anju on 03-09-2019.
 */
public class VerseDetailAdapter extends RecyclerView.Adapter<VerseDetailAdapter.ViewHolder> {
    private List<VerseDetail> verseDetailList;
    Context context;
    public VerseDetailListener mVerseListener;
    public String mVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, malayalamFont, arabicfont, sTranslationNumber;
    public AudioListner mAudioListener;
    public BookmarkListner bookmarkListner;
    String ShareString;
String playstorelink="https://play.google.com/store/apps/details?id=com.qradio&hl=en_IN";
Boolean versearabicBool=true,translationBool=true,wordmeaningBool=true;
    public VerseDetailAdapter(BookmarkListner bookmarkListner, AudioListner audioListner, VerseDetailListener verseDetailListener, List<VerseDetail> verseDetailList, Context context, String mVerseRadioButton,
                              String sTranslationRadioButton, String sWordMeaningRadioButton, String malayalamFont, String arabicfont,
                              String sTranslationNumber) {
        this.verseDetailList = verseDetailList;
        this.context = context;
        this.mVerseListener = verseDetailListener;
        this.mVerseRadioButton = mVerseRadioButton;
        this.sTranslationRadioButton = sTranslationRadioButton;
        this.sWordMeaningRadioButton = sWordMeaningRadioButton;
        this.malayalamFont = malayalamFont;
        this.arabicfont = arabicfont;
        this.sTranslationNumber = sTranslationNumber;
        this.mAudioListener = audioListner;
        this.bookmarkListner = bookmarkListner;
    }


    public void setItems(List<VerseDetail> newVerselist) {
        this.verseDetailList = newVerselist;
    }

    public void setWordMeaningItems(List<VerseDetail> newVerselist) {
        this.verseDetailList = newVerselist;
    }

    @Override
    public VerseDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.verselist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VerseDetailAdapter.ViewHolder viewHolder, final int i) {


        if (verseDetailList.get(i).getSelected().toString().equalsIgnoreCase("true")) {
            System.out.println("selection  " + i + verseDetailList.get(i).getSelected().toString());

            viewHolder.cardview.setBackgroundColor(Color.parseColor("#33000000"));
        } else {
            System.out.println("unselection  " + i + verseDetailList.get(i).getSelected().toString());

            viewHolder.cardview.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewHolder.mChapternoTextView.setText(verseDetailList.get(i).getSurah_no() + ":" + verseDetailList.get(i).getAyat_no());
        //  viewHolder.mVersenoTextView.setText(":"+verseDetailList.get(i).getAyat_no());


        if (mVerseRadioButton.equalsIgnoreCase("verseOn")) {
            viewHolder.mMeaningArabicTextView.setText(verseDetailList.get(i).getMeaning_arabic());

        } else if (mVerseRadioButton.equalsIgnoreCase("verseOff")) {
            viewHolder.mMeaningArabicTextView.setVisibility(View.GONE);
        }
////
        if (sTranslationRadioButton.equalsIgnoreCase("translationOn")) {
            //  viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam());

            if (sTranslationNumber.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
                viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam());
            } else if (sTranslationNumber.equalsIgnoreCase("Amani Thafseer")) {
                viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam_new());
            }

        } else if (sTranslationRadioButton.equalsIgnoreCase("translationOff")) {
            viewHolder.mMeaningMalayalamTextView.setVisibility(View.GONE);
        }



/*mChapternoTextView
        for(int j=0;j<verseDetailList.get(i).getWordMeaningLists().size();j++){
            TextView textView1 = new TextView(context);
            textView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView1.setText(verseDetailList.get(i).getWordMeaningLists().get(j).getMeaning_mala());
         //   textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
            textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            viewHolder.mDynamicLayout.addView(textView1);
        }*/

//check if there is vyakaranakurip
        if (verseDetailList.get(i).getDetailed_definition().isEmpty()) {
            viewHolder.vykaranakuripImageView.setVisibility(View.GONE);
        } else {
            viewHolder.vykaranakuripImageView.setVisibility(View.VISIBLE);

        }

        viewHolder.vykaranakuripImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mVerseListener.verse_vyakyanakurip(verseDetailList.get(i).getDetailed_definition(), verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getChapter_name_meaning());
            }
        });

       /* viewHolder.mVerseExplanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerseListener.verse_explanation(verseDetailList.get(i).getSurah_no(), verseDetailList.get(i).getAyat_no());
            }
        });*/
//suraname and bismi

        if (i == 0) {

            if (verseDetailList.get(i).getSurah_no().equalsIgnoreCase("9")) {

                viewHolder.cardview1.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setText(verseDetailList.get(i).getChapter_name());
                viewHolder.mBismiTextview.setVisibility(View.GONE);

            } else {
                viewHolder.cardview1.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setText(verseDetailList.get(i).getChapter_name());
                viewHolder.mBismiTextview.setVisibility(View.VISIBLE);
            }

        } else {
            viewHolder.cardview1.setVisibility(View.GONE);
            viewHolder.mSuraNameTextview.setVisibility(View.GONE);
            viewHolder.mBismiTextview.setVisibility(View.GONE);
        }


        viewHolder.mOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
                //creating a popup menu
                PopupMenu popup = new PopupMenu(wrapper, viewHolder.mOptionMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.verse_popup_menu);
                Menu popupMenu = popup.getMenu();
                //  popupMenu.findItem(R.id.mAudioTajweed).setEnabled(false);
                //      popupMenu.findItem(R.id.mAudioTajweed).setTitle(Html.fromHtml("<font color='#C6C6C6'>Audio Tajweed</font>"));
                //   popupMenu.findItem(R.id.mAudioTafseer).setTitle(Html.fromHtml("<font color='#C6C6C6'>Audio Tafseer</font>"));
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {


                            case R.id.mAudioTajweed:
                                mAudioListener.fetchaudio("Tajweed", verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getChapter_name_meaning(), verseDetailList.get(i).getSurah_no(), verseDetailList.get(0).getAyat_no(), String.valueOf(verseDetailList.size()));
                                return true;
                            case R.id.mAudioTafseer:
                                mAudioListener.fetchaudio("Tafsir", verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getChapter_name_meaning(), verseDetailList.get(i).getSurah_no(), verseDetailList.get(0).getAyat_no(), String.valueOf(verseDetailList.size()));
                                return true;
                            case R.id.mTranslation:
                                mAudioListener.fetchaudio("Translation", verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getChapter_name_meaning(), verseDetailList.get(i).getSurah_no(), verseDetailList.get(0).getAyat_no(), String.valueOf(verseDetailList.size()));
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });


        viewHolder.vishadheekarnmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerseListener.verse_explanation(verseDetailList.get(i).getSurah_no(), verseDetailList.get(i).getAyat_no());
            }
        });
//makenote
        viewHolder.makenoteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.passdetailsForNotes(verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getSurah_no(),
                        verseDetailList.get(i).getAyat_no());
            }
        });
//add bookmark
        viewHolder.bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.passVerseDetails("Sura", verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getSurah_no(),
                        verseDetailList.get(i).getAyat_no(), "");
            }
        });

        // play
        viewHolder.playImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAudioListener.fetchaudio("Verse", verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getChapter_name_meaning(), verseDetailList.get(i).getSurah_no(), verseDetailList.get(i).getAyat_no(), String.valueOf(verseDetailList.size()));

            }
        });
// favourites
        viewHolder.favouritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerseListener.addtofavourites(verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getSurah_no(),
                        verseDetailList.get(i).getAyat_no(),verseDetailList.get(i).getMeaning_arabic(),viewHolder.mMeaningMalayalamTextView.getText().toString());
            }
        });


        // share
        viewHolder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show pop up
showpopupDialog(verseDetailList.get(i).getSurah_no(),verseDetailList.get(i).getAyat_no(),verseDetailList.get(i).getMeaning_arabic(),verseDetailList.get(i).getMeaning_malayalam(),verseDetailList.get(i).getMeaning_malayalam_new(),
        verseDetailList.get(i).getWord_meaning());


             /*   if (mVerseRadioButton.equalsIgnoreCase("verseOn")) {
                    ShareString = verseDetailList.get(i).getMeaning_arabic();

                }
                if (sTranslationRadioButton.equalsIgnoreCase("translationOn")) {
                    if (sTranslationNumber.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {

                        ShareString = ShareString + "\n" + verseDetailList.get(i).getMeaning_malayalam();

                    } else if (sTranslationNumber.equalsIgnoreCase("Amani Thafseer")) {
                        ShareString = ShareString + "\n" + verseDetailList.get(i).getMeaning_malayalam_new();

                    }

                }

                if (sWordMeaningRadioButton.equalsIgnoreCase("wordmeaningOn")) {
                    String wordstring = "\n";
                    for (int j = 0; j < verseDetailList.get(i).getWord_meaning().size(); j++) {
                        wordstring = wordstring + "\n" + verseDetailList.get(i).getWord_meaning().get(j).getMalayalam_word() + "=" +
                                verseDetailList.get(i).getWord_meaning().get(j).getArabic_word();

                                       *//* wordstring=wordstring+"\n"+verseDetailList.get(i).getWordMeaningLists().get(j).getMeaning_arabic()+"="+
                                                verseDetailList.get(i).getWordMeaningLists().get(j).getMeaning_mala();*//*
                    }
                    ShareString = ShareString + wordstring;
                }
                bookmarkListner.sharestring(ShareString);*/


            }
        });

        // wordmeaning on and off
        viewHolder.wordmeaningImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mVerseListener.wordmeaningOn(verseDetailList.get(i).getAyat_no());


            }
        });
       //check wordmeaning


        if (verseDetailList.get(i).getWordmeaning()) {
            viewHolder.mWordMeaningRecylerView.setVisibility(View.VISIBLE);
            WordMeaningHorizontalNewAdapter horizontalImageAdapter = new WordMeaningHorizontalNewAdapter(verseDetailList.get(i).getWord_meaning(), sWordMeaningRadioButton, malayalamFont,
                    arabicfont);
            viewHolder.mWordMeaningRecylerView.setAdapter(horizontalImageAdapter);
        } else {
            viewHolder.mWordMeaningRecylerView.setVisibility(View.GONE);
        }
///check if favourites

        if (verseDetailList.get(i).getFavourites()) {
            viewHolder.favouritesImageView.setImageResource(R.drawable.fillfavour);

        } else {
            viewHolder.favouritesImageView.setImageResource(R.drawable.favourite);
        }

    }

    @Override
    public int getItemCount() {
        return verseDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSuraNameTextview, mVyakyanakuripTextView, mChapternoTextView, mVersenoTextView, mMeaningArabicTextView, mMeaningMalayalamTextView, mVerseExplanation;
        RecyclerView mWordMeaningRecylerView;
        RecyclerView.LayoutManager layoutManager;
        LinearLayout mDynamicLayout;
        ImageView mOptionMenu, mBismiTextview, makenoteImageView, favouritesImageView, bookmarkImageView, shareImageView, playImageView,
                wordmeaningImageView, vishadheekarnmImageView, vykaranakuripImageView;
        CardView cardview, cardview1;

        public ViewHolder(View view) {
            super(view);
            cardview = (CardView) view.findViewById(R.id.cardview);
            cardview1 = (CardView) view.findViewById(R.id.cardview1);
            mOptionMenu = (ImageView) view.findViewById(R.id.mOptionMenu);
            mChapternoTextView = (TextView) view.findViewById(R.id.mChapternoTextView);
            mVersenoTextView = (TextView) view.findViewById(R.id.mVersenoTextView);
            mMeaningArabicTextView = (TextView) view.findViewById(R.id.mMeaningArabicTextView);
            mMeaningMalayalamTextView = (TextView) view.findViewById(R.id.mMeaningMalayalamTextView);
            mWordMeaningRecylerView = (RecyclerView) view.findViewById(R.id.mWordMeaningRecylerView);
            mVerseExplanation = (TextView) view.findViewById(R.id.mVerseExplanation);
            mVyakyanakuripTextView = (TextView) view.findViewById(R.id.mVyakyanakuripTextView);
            mBismiTextview = (ImageView) view.findViewById(R.id.mBismiTextview);


            makenoteImageView = (ImageView) view.findViewById(R.id.makenoteImageView);
            bookmarkImageView = (ImageView) view.findViewById(R.id.bookmarkImageView);
            favouritesImageView = (ImageView) view.findViewById(R.id.favouritesImageView);
            shareImageView = (ImageView) view.findViewById(R.id.shareImageView);
            playImageView = (ImageView) view.findViewById(R.id.playImageView);
            wordmeaningImageView = (ImageView) view.findViewById(R.id.wordmeaningImageView);
            vishadheekarnmImageView = (ImageView) view.findViewById(R.id.vishadheekarnmImageView);
            vykaranakuripImageView = (ImageView) view.findViewById(R.id.vykaranakuripImageView);


            mSuraNameTextview = (TextView) view.findViewById(R.id.mSuraNameTextview);
            // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
            RtlGridLayoutManager gridLayoutManager = new RtlGridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
            mWordMeaningRecylerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

            //arabic font

            if (arabicfont.equalsIgnoreCase("0")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                //    mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);

            } else if (arabicfont.equalsIgnoreCase("1")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                //   mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            } else if (arabicfont.equalsIgnoreCase("2")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                //   mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            } else if (arabicfont.equalsIgnoreCase("3")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                //  mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            } else if (arabicfont.equalsIgnoreCase("4")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                //   mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            } else if (arabicfont.equalsIgnoreCase("5")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                //   mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            } else if (arabicfont.equalsIgnoreCase("6")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                // mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            }

////////
            if (malayalamFont.equalsIgnoreCase("0")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);

            } else if (malayalamFont.equalsIgnoreCase("1")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            } else if (malayalamFont.equalsIgnoreCase("2")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            } else if (malayalamFont.equalsIgnoreCase("3")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            } else if (malayalamFont.equalsIgnoreCase("4")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            } else if (malayalamFont.equalsIgnoreCase("5")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            } else if (malayalamFont.equalsIgnoreCase("6")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            }


        }
    }

public void showpopupDialog(String chapterno,String verseno,String verse, String translationn, String translation_new, List<WordMeaningListNew> wordmeaninglist){
    // custom dialog
    final Dialog dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    dialog.setContentView(R.layout.share_dialog);
    TextView mSharetextview = (TextView) dialog.findViewById(R.id.mSharetextview);
    CheckBox versearabic = (CheckBox) dialog.findViewById(R.id.versearabic);
    CheckBox translation = (CheckBox) dialog.findViewById(R.id.translation);
    CheckBox wordmeaning = (CheckBox) dialog.findViewById(R.id.wordmeaning);
    versearabic.setChecked(true);
    translation.setChecked(true);
    wordmeaning.setChecked(true);
    versearabic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(!b){
              versearabic.setChecked(false);
                versearabicBool=false;
            }
            else {
                versearabic.setChecked(true);
                versearabicBool=true;
            }
        }
    });
    translation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(!b){
                translation.setChecked(false);
                translationBool=false;
            }
            else {
                translation.setChecked(true);
                translationBool=true;
            }
        }
    });
    wordmeaning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(!b){
                wordmeaning.setChecked(false);
                wordmeaningBool=false;
            }
            else {
                wordmeaning.setChecked(true);
                wordmeaningBool=true;
            }
        }
    });

    mSharetextview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ShareString=chapterno+" : "+verseno+"\n";

            if (versearabicBool) {
                ShareString = ShareString+verse;

            }
            if (translationBool) {
                if (sTranslationNumber.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {

                    ShareString = ShareString + "\n" + translationn;

                } else if (sTranslationNumber.equalsIgnoreCase("Amani Thafseer")) {
                    ShareString = ShareString + "\n" + translation_new;

                }

            }

            if (wordmeaningBool) {
                String wordstring = "\n";
                for (int j = 0; j < wordmeaninglist.size(); j++) {
                    wordstring = wordstring + "\n" + wordmeaninglist.get(j).getMalayalam_word() + " = " +
                            wordmeaninglist.get(j).getArabic_word();

                                   /*   wordstring=wordstring+"\n"+verseDetailList.get(i).getWordMeaningLists().get(j).getMeaning_arabic()+"="+
                    verseDetailList.get(i).getWordMeaningLists().get(j).getMeaning_mala();*/
                }
                ShareString = ShareString + wordstring;
            }
            bookmarkListner.sharestring(ShareString+"\n"+playstorelink);
        }
    });

    dialog.show();
}
}
