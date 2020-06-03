package com.Qubicle.QMT.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.JuzListener;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailAdapterModel;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.SpinnerchapterlistModel;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.WordMeaningListNew;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.RtlGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anju on 03-09-2019.
 */
public class JuzDetailAdapter extends RecyclerView.Adapter<JuzDetailAdapter.ViewHolder> {
    private List<JuzDetailAdapterModel> verseDetailList;
    private List<JuzDetailNew> JuzDetailNewlist;
    Context context;
    String[] verselistarray;
    public JuzListener mVerseListener;
    public AudioListner mAudioListener;
    public BookmarkListner bookmarkListner;
    public String sTranslationNumber,mVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, malayalamFont, arabicfont;
    List<SpinnerchapterlistModel> spinnerchapterlistModels;
    String ShareString;
    String playstorelink="https://play.google.com/store/apps/details?id=com.qradio&hl=en_IN";
    Boolean versearabicBool=true,translationBool=true;
    public JuzDetailAdapter(BookmarkListner bookmarkListner,List<JuzDetailNew> juzDetailNewList, AudioListner audioListner, JuzListener verseDetailListener, List<JuzDetailAdapterModel> verseDetailList, Context context, String mVerseRadioButton,
                            String sTranslationRadioButton, String sWordMeaningRadioButton, String malayalamFont, String arabicfont,
                            String sTranslationNumber) {
        this.verseDetailList = verseDetailList;
        this.JuzDetailNewlist = juzDetailNewList;
        this.context = context;
        this.mVerseListener = verseDetailListener;
        this.mVerseRadioButton = mVerseRadioButton;
        this.sTranslationRadioButton = sTranslationRadioButton;
        this.sWordMeaningRadioButton = sWordMeaningRadioButton;
        this.malayalamFont = malayalamFont;
        this.arabicfont = arabicfont;
        this.mAudioListener = audioListner;
        this.bookmarkListner=bookmarkListner;
        this.sTranslationNumber = sTranslationNumber;

    }

    public void setItems(List<JuzDetailAdapterModel> newVerselist) {
        this.verseDetailList = newVerselist;
    }

    @Override
    public JuzDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.verselist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JuzDetailAdapter.ViewHolder viewHolder, final int i) {

        if (verseDetailList.get(i).getSelected().toString().equalsIgnoreCase("true")) {
            System.out.println("selection  " + i + verseDetailList.get(i).getSelected().toString());

            viewHolder.cardview.setBackgroundColor(Color.parseColor("#33000000"));
        } else {
            System.out.println("unselection  " + i + verseDetailList.get(i).getSelected().toString());

            viewHolder.cardview.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        if (!verseDetailList.get(i).getChapter_name().equalsIgnoreCase(" ")) {
           /* viewHolder.chapternameTextView.setVisibility(View.VISIBLE);
            viewHolder.chapternameTextView.setText(verseDetailList.get(i).getChapter_name());*/
            if (verseDetailList.get(i).getSurah_no().equalsIgnoreCase("9")) {
                viewHolder.chapternameTextView.setVisibility(View.GONE);
                viewHolder.cardview1.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setText(verseDetailList.get(i).getChapter_name());
                viewHolder.mBismiTextview.setVisibility(View.GONE);

            } else {
                viewHolder.chapternameTextView.setVisibility(View.GONE);
                viewHolder.cardview1.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setText(verseDetailList.get(i).getChapter_name());
                viewHolder.mBismiTextview.setVisibility(View.VISIBLE);
            }


        } else {

            viewHolder.cardview1.setVisibility(View.GONE);
            viewHolder.mSuraNameTextview.setVisibility(View.GONE);
            viewHolder.mBismiTextview.setVisibility(View.GONE);

            viewHolder.chapternameTextView.setVisibility(View.GONE);
        }


        viewHolder.mChapternoTextView.setText(verseDetailList.get(i).getSurah_no() + ":" + verseDetailList.get(i).getAyat_no());
        //   viewHolder.mVersenoTextView.setText(" :"+verseDetailList.get(i).getAyat_no());


        if (mVerseRadioButton.equalsIgnoreCase("verseOn")) {
            viewHolder.mMeaningArabicTextView.setText(verseDetailList.get(i).getMeaning_arabic());

        } else if (mVerseRadioButton.equalsIgnoreCase("verseOff")) {
            viewHolder.mMeaningArabicTextView.setVisibility(View.GONE);
        }
////
        if (sTranslationRadioButton.equalsIgnoreCase("translationOn")) {

            if (sTranslationNumber.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
                viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam());
            } else if (sTranslationNumber.equalsIgnoreCase("Amani Thafseer")) {
                viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam_new());
            }



         //   viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam());


        } else if (sTranslationRadioButton.equalsIgnoreCase("translationOff")) {
            viewHolder.mMeaningMalayalamTextView.setVisibility(View.GONE);
        }


        viewHolder.mVerseExplanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerseListener.verse_explanation(verseDetailList.get(i).getSurah_no(), verseDetailList.get(i).getAyat_no());
            }
        });
     /*   WordMeaningHorizontalAdapter horizontalImageAdapter = new WordMeaningHorizontalAdapter(verseDetailList.get(i).getWordMeaningLists(),sWordMeaningRadioButton,malayalamFont,
                arabicfont);
        viewHolder.  mWordMeaningRecylerView.setAdapter(horizontalImageAdapter);
*/

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
                //   popupMenu.findItem(R.id.mAudioTajweed).setTitle(Html.fromHtml("<font color='#C6C6C6'>Audio Tajweed</font>"));
                //    popupMenu.findItem(R.id.mAudioTafseer).setTitle(Html.fromHtml("<font color='#C6C6C6'>Audio Tafseer</font>"));
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                         /*   case R.id.mshare:

                                ShareString=verseDetailList.get(i).getMeaning_arabic()+"\n"+verseDetailList.get(i).getMeaning_malayalam();
                                bookmarkListner.sharestring(ShareString);
                                return true;
                            case R.id.mNoteView:
                                bookmarkListner.shownotes(verseDetailList.get(i).getChapter_name(),verseDetailList.get(i).getSurah_no(),
                                        verseDetailList.get(i).getAyat_no());
                                return true;
                            case R.id.mBookMark:
                                bookmarkListner.passVerseDetails("Juz",verseDetailList.get(i).getChapternameAll(),verseDetailList.get(i).getSurah_no(),
                                        verseDetailList.get(i).getAyat_no(),verseDetailList.get(i).getJuzno());
                                return true;
                            case R.id.mMakeNote:
                                bookmarkListner.passdetailsForNotes(verseDetailList.get(i).getChapter_name(),verseDetailList.get(i).getSurah_no(),
                                        verseDetailList.get(i).getAyat_no());
                                return true;



                          */
                            case R.id.mTranslation:
                                spinnerchapterlistModels = new ArrayList<>();
                                for (int i = 0; i < JuzDetailNewlist.size(); i++) {
                                    SpinnerchapterlistModel spinnerchapterlistModel = new SpinnerchapterlistModel();
                                    spinnerchapterlistModel.setSurah_no(JuzDetailNewlist.get(i).getSurah_no());
                                    spinnerchapterlistModel.setChapter_name(JuzDetailNewlist.get(i).getChapter_name());
                                    spinnerchapterlistModels.add(spinnerchapterlistModel);
                                }


                                mAudioListener.fetchjuzaudio("Translation", spinnerchapterlistModels, verseDetailList.get(0).getAyat_no(), verseDetailList.get(verseDetailList.size() - 1).getAyat_no());

                                //      mAudioListener.fetchjuzaudio(verseDetailList.get(0).getSurah_no(),verseDetailList.get(verseDetailList.size()-1).getSurah_no(),verseDetailList.get(0).getChapter_name(), verseDetailList.get(verseDetailList.size()-1).getChapter_name(), verselistarray,"0", String.valueOf(verseDetailList.size()));
                                return true;

                            case R.id.mAudioTajweed:
                                spinnerchapterlistModels = new ArrayList<>();
                                for (int i = 0; i < JuzDetailNewlist.size(); i++) {
                                    SpinnerchapterlistModel spinnerchapterlistModel = new SpinnerchapterlistModel();
                                    spinnerchapterlistModel.setSurah_no(JuzDetailNewlist.get(i).getSurah_no());
                                    spinnerchapterlistModel.setChapter_name(JuzDetailNewlist.get(i).getChapter_name());
                                    spinnerchapterlistModels.add(spinnerchapterlistModel);
                                }


                                mAudioListener.fetchjuzaudio("Tajweed", spinnerchapterlistModels, verseDetailList.get(0).getAyat_no(), verseDetailList.get(verseDetailList.size() - 1).getAyat_no());

                                return true;
                            case R.id.mAudioTafseer:
                                spinnerchapterlistModels = new ArrayList<>();
                                for (int i = 0; i < JuzDetailNewlist.size(); i++) {
                                    SpinnerchapterlistModel spinnerchapterlistModel = new SpinnerchapterlistModel();
                                    spinnerchapterlistModel.setSurah_no(JuzDetailNewlist.get(i).getSurah_no());
                                    spinnerchapterlistModel.setChapter_name(JuzDetailNewlist.get(i).getChapter_name());
                                    spinnerchapterlistModels.add(spinnerchapterlistModel);
                                }


                                mAudioListener.fetchjuzaudio("Tafsir", spinnerchapterlistModels, verseDetailList.get(0).getAyat_no(), verseDetailList.get(verseDetailList.size() - 1).getAyat_no());

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
//makenote
        viewHolder.makenoteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.passdetailsForNotes(verseDetailList.get(i).getChapter_name(),verseDetailList.get(i).getSurah_no(),
                        verseDetailList.get(i).getAyat_no());
            }
        });
//add bookmark
        viewHolder.bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.passVerseDetails("Juz",verseDetailList.get(i).getChapternameAll(),verseDetailList.get(i).getSurah_no(),
                        verseDetailList.get(i).getAyat_no(),verseDetailList.get(i).getJuzno());
            }
        });
// favourites
        viewHolder.favouritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    mAudioListener.fetchaudio("Verse",verseDetailList.get(i).getChapter_name(),verseDetailList.get(i).getChapter_name_meaning(),verseDetailList.get(i).getSurah_no(),verseDetailList.get(i).getAyat_no(), String.valueOf(verseDetailList.size()));

            }
        });
        // share
        viewHolder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopupDialog(verseDetailList.get(i).getSurah_no(),verseDetailList.get(i).getAyat_no(),verseDetailList.get(i).getMeaning_arabic(),verseDetailList.get(i).getMeaning_malayalam(),verseDetailList.get(i).getMeaning_malayalam_new()
                        );


               /* ShareString=verseDetailList.get(i).getMeaning_arabic()+"\n"+verseDetailList.get(i).getMeaning_malayalam();
                bookmarkListner.sharestring(ShareString);*/

            }
        });

        viewHolder.vishadheekarnmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerseListener.verse_explanation(verseDetailList.get(i).getSurah_no(), verseDetailList.get(i).getAyat_no());            }
        });
        // play
        viewHolder.playImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerchapterlistModels = new ArrayList<>();
                for (int i = 0; i < JuzDetailNewlist.size(); i++) {
                    SpinnerchapterlistModel spinnerchapterlistModel = new SpinnerchapterlistModel();
                    spinnerchapterlistModel.setSurah_no(JuzDetailNewlist.get(i).getSurah_no());
                    spinnerchapterlistModel.setChapter_name(JuzDetailNewlist.get(i).getChapter_name());
                    spinnerchapterlistModels.add(spinnerchapterlistModel);
                }


                mAudioListener.fetchjuzaudio("Verse", spinnerchapterlistModels, verseDetailList.get(0).getAyat_no(), verseDetailList.get(verseDetailList.size() - 1).getAyat_no());

            }
        });

        viewHolder.vykaranakuripImageView.setVisibility(View.GONE);
        ///check if favourites

        if (verseDetailList.get(i).getFavourites()) {
            viewHolder.favouritesImageView.setImageResource(R.drawable.fillfavour);

        } else {
            viewHolder.favouritesImageView.setImageResource(R.drawable.favourite);
        }
// favourites
        viewHolder.favouritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerseListener.addtofavourites(verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getSurah_no(),
                        verseDetailList.get(i).getAyat_no(),verseDetailList.get(i).getMeaning_arabic(),viewHolder.mMeaningMalayalamTextView.getText().toString());
            }
        });

viewHolder.wordmeaningImageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return verseDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  mSuraNameTextview, mChapternoTextView, chapternameTextView, mVersenoTextView, mMeaningArabicTextView, mMeaningMalayalamTextView, mVerseExplanation;
        RecyclerView mWordMeaningRecylerView;
        RecyclerView.LayoutManager layoutManager;
        LinearLayout mDynamicLayout;
        ImageView mOptionMenu,mBismiTextview, makenoteImageView, favouritesImageView, bookmarkImageView, shareImageView, playImageView,
                 vishadheekarnmImageView,wordmeaningImageView,vykaranakuripImageView;
        CardView cardview, cardview1;

        public ViewHolder(View view) {
            super(view);
            cardview = (CardView) view.findViewById(R.id.cardview);
            cardview1 = (CardView) view.findViewById(R.id.cardview1);
            mOptionMenu = (ImageView) view.findViewById(R.id.mOptionMenu);
            //  mDynamicLayout = (LinearLayout)view.findViewById(R.id.mDynamicLayout);
            mChapternoTextView = (TextView) view.findViewById(R.id.mChapternoTextView);
            chapternameTextView = (TextView) view.findViewById(R.id.chapternameTextView);
            mVersenoTextView = (TextView) view.findViewById(R.id.mVersenoTextView);
            mMeaningArabicTextView = (TextView) view.findViewById(R.id.mMeaningArabicTextView);
            mMeaningMalayalamTextView = (TextView) view.findViewById(R.id.mMeaningMalayalamTextView);
            mWordMeaningRecylerView = (RecyclerView) view.findViewById(R.id.mWordMeaningRecylerView);
            mVerseExplanation = (TextView) view.findViewById(R.id.mVerseExplanation);
            mBismiTextview = (ImageView) view.findViewById(R.id.mBismiTextview);
            mSuraNameTextview = (TextView) view.findViewById(R.id.mSuraNameTextview);
            // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
            RtlGridLayoutManager gridLayoutManager = new RtlGridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
            mWordMeaningRecylerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

            vykaranakuripImageView = (ImageView) view.findViewById(R.id.vykaranakuripImageView);

            makenoteImageView = (ImageView) view.findViewById(R.id.makenoteImageView);
            bookmarkImageView = (ImageView) view.findViewById(R.id.bookmarkImageView);
            favouritesImageView = (ImageView) view.findViewById(R.id.favouritesImageView);
            shareImageView = (ImageView) view.findViewById(R.id.shareImageView);
            playImageView = (ImageView) view.findViewById(R.id.playImageView);
            vishadheekarnmImageView = (ImageView) view.findViewById(R.id.vishadheekarnmImageView);
            wordmeaningImageView = (ImageView) view.findViewById(R.id.wordmeaningImageView);



            //arabic font

            if (arabicfont.equalsIgnoreCase("0")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
              //  mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);

            } else if (arabicfont.equalsIgnoreCase("1")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
             //   mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            } else if (arabicfont.equalsIgnoreCase("2")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            //    mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            } else if (arabicfont.equalsIgnoreCase("3")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            //    mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            } else if (arabicfont.equalsIgnoreCase("4")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            //    mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            } else if (arabicfont.equalsIgnoreCase("5")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            } else if (arabicfont.equalsIgnoreCase("6")) {
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
           //     mBismiTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            }

////////
            if (malayalamFont.equalsIgnoreCase("0")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            } else if (malayalamFont.equalsIgnoreCase("1")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            } else if (malayalamFont.equalsIgnoreCase("2")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            } else if (malayalamFont.equalsIgnoreCase("3")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            } else if (malayalamFont.equalsIgnoreCase("4")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            } else if (malayalamFont.equalsIgnoreCase("5")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            } else if (malayalamFont.equalsIgnoreCase("6")) {
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                mSuraNameTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

            }


        }
    }

    public void showpopupDialog(String chapterno,String verseno,String verse, String translationn, String translation_new){
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.share_dialog);
        TextView mSharetextview = (TextView) dialog.findViewById(R.id.mSharetextview);
        TextView wordtextview = (TextView) dialog.findViewById(R.id.wordtextview);
        CheckBox versearabic = (CheckBox) dialog.findViewById(R.id.versearabic);
        CheckBox translation = (CheckBox) dialog.findViewById(R.id.translation);
        CheckBox wordmeaning = (CheckBox) dialog.findViewById(R.id.wordmeaning);
        versearabic.setChecked(true);
        translation.setChecked(true);
        wordmeaning.setVisibility(View.GONE);
        wordtextview.setVisibility(View.GONE);

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


                bookmarkListner.sharestring(ShareString+"\n"+playstorelink);
            }
        });

        dialog.show();
    }
}
