package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.RtlGridLayoutManager;

import java.util.List;

/**
 * Created by Anju on 03-09-2019.
 */
public class IndexVerseDetailAdapter extends RecyclerView.Adapter<IndexVerseDetailAdapter.ViewHolder> {
    private List<VerseDetail> verseDetailList;
    Context context;
    public VerseDetailListener mVerseListener;
    public String mVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, malayalamFont, arabicfont, sTranslationNumber;
public AudioListner mAudioListener;
public BookmarkListner bookmarkListner;
String ShareString;
    public IndexVerseDetailAdapter( VerseDetailListener verseDetailListener, List<VerseDetail> verseDetailList, Context context, String mVerseRadioButton,
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

    }


    public void setItems(List<VerseDetail> newVerselist) {
        this.verseDetailList = newVerselist;
    }

    @Override
    public IndexVerseDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.verselist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IndexVerseDetailAdapter.ViewHolder viewHolder, final int i) {


if(verseDetailList.get(i).getSelected().toString().equalsIgnoreCase("true")){
    System.out.println("selection  "+i+verseDetailList.get(i).getSelected().toString());

    viewHolder.cardview.setBackgroundColor(Color.parseColor("#33000000"));
}
else {
    System.out.println("unselection  "+i+verseDetailList.get(i).getSelected().toString());

    viewHolder.cardview.setBackgroundColor(Color.parseColor("#ffffff"));
}
        viewHolder.mChapternoTextView.setText(verseDetailList.get(i).getChapter_no() + ":" + verseDetailList.get(i).getVerse_no());
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
        WordMeaningHorizontalNewAdapter horizontalImageAdapter = new WordMeaningHorizontalNewAdapter(verseDetailList.get(i).getWord_meaning(), sWordMeaningRadioButton, malayalamFont,
                arabicfont);
        viewHolder.mWordMeaningRecylerView.setAdapter(horizontalImageAdapter);

/*
        if(verseDetailList.get(i).getDetailed_definition().isEmpty()){
            viewHolder.mVyakyanakuripTextView.setVisibility(View.GONE);
        }
        else {
            viewHolder.mVyakyanakuripTextView.setVisibility(View.VISIBLE);

        }

        viewHolder.mVyakyanakuripTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mVerseListener.verse_vyakyanakurip(verseDetailList.get(i).getDetailed_definition(),verseDetailList.get(i).getChapter_name(), verseDetailList.get(i).getChapter_name_meaning());
            }
        });

        viewHolder.mVerseExplanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   mVerseListener.verse_explanation(verseDetailList.get(i).getSurah_no(), verseDetailList.get(i).getAyat_no());
            }
        });*/
//suraname and bismi

     /*   if(i==0){

            if(verseDetailList.get(i).getChapter_no().equalsIgnoreCase("9")){

                viewHolder.cardview1.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setText(verseDetailList.get(i).getChapter_name());
                viewHolder.mBismiTextview.setVisibility(View.GONE);

            }
            else {
                viewHolder.cardview1.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setVisibility(View.VISIBLE);
                viewHolder.mSuraNameTextview.setText(verseDetailList.get(i).getChapter_name());
                viewHolder.mBismiTextview.setVisibility(View.VISIBLE);
            }

        }
        else {
            viewHolder.cardview1.setVisibility(View.GONE);
            viewHolder.mSuraNameTextview.setVisibility(View.GONE);
            viewHolder.mBismiTextview.setVisibility(View.GONE);
        }

*/
viewHolder.mOptionMenu.setVisibility(View.GONE);
viewHolder.cardview1.setVisibility(View.GONE);
viewHolder.mVerseExplanation.setVisibility(View.GONE);



    }

    @Override
    public int getItemCount() {
        return verseDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSuraNameTextview,mVyakyanakuripTextView,mChapternoTextView, mVersenoTextView, mMeaningArabicTextView, mMeaningMalayalamTextView, mVerseExplanation;
        RecyclerView mWordMeaningRecylerView;
        RecyclerView.LayoutManager layoutManager;
        LinearLayout mDynamicLayout;
        ImageView mOptionMenu,mBismiTextview;
        CardView cardview,cardview1;

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


}
