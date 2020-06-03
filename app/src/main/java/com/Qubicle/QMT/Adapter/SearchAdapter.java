package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.SearchListner;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.RtlGridLayoutManager;

import java.util.List;

/**
 * Created by Anju on 03-09-2019.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<VerseDetail> verseDetailList;
    Context context;
    public SearchListner mVerseListener;
    public String mVerseRadioButton, sTranslationRadioButton, sWordMeaningRadioButton, malayalamFont, arabicfont, sTranslationNumber;
    public AudioListner mAudioListener;
    public BookmarkListner bookmarkListner;
    String highlightword;

    public SearchAdapter(SearchListner verseDetailListener, List<VerseDetail> verseDetailList, Context context,
                         String malayalamFont, String arabicfont,String highlightword) {
        this.verseDetailList = verseDetailList;
        this.context = context;
        this.mVerseListener = verseDetailListener;

        this.malayalamFont = malayalamFont;
        this.arabicfont = arabicfont;
        this.highlightword = highlightword;


    }


    public void setItems(List<VerseDetail> newVerselist) {
        this.verseDetailList = newVerselist;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.verselist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mChapternoTextView.setText(verseDetailList.get(i).getSurah_no() + ":" + verseDetailList.get(i).getAyat_no());
        viewHolder.mMeaningArabicTextView.setText(verseDetailList.get(i).getMeaning_arabic());

if(verseDetailList.get(i).getMeaning_malayalam()!=null){

    if(highlightword!=null){
        String desc = verseDetailList.get(i).getMeaning_malayalam();
        String rep = desc.replace(highlightword,    "<b><font color=#2825A6>"+ highlightword+ "</font></b>");
        viewHolder.mMeaningMalayalamTextView.setText(Html.fromHtml(rep));

    }
    else {
        viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam());
    }

}
     else if(verseDetailList.get(i).getMeaning_malayalam_new()!=null)   {

    if(highlightword!=null){
        String desc = verseDetailList.get(i).getMeaning_malayalam_new();
        String rep = desc.replace(highlightword,   "<b><font color=#2825A6>"+ highlightword+ "</font></b>");
        viewHolder.mMeaningMalayalamTextView.setText(Html.fromHtml(rep));

    }
else {
        viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMeaning_malayalam_new());
    }


}
     viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             mVerseListener.openversedetail(verseDetailList.get(i).getSurah_no(),verseDetailList.get(i).getAyat_no());
         }
     });



     /*   WordMeaningHorizontalNewAdapter horizontalImageAdapter = new WordMeaningHorizontalNewAdapter(verseDetailList.get(i).getWord_meaning(), sWordMeaningRadioButton, malayalamFont,
                arabicfont);
        viewHolder.mWordMeaningRecylerView.setAdapter(horizontalImageAdapter);*/


        viewHolder.mOptionMenu.setVisibility(View.GONE);
        viewHolder.cardview1.setVisibility(View.GONE);
        viewHolder.menulayout.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return verseDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSuraNameTextview, mVyakyanakuripTextView, mChapternoTextView, mVersenoTextView, mMeaningArabicTextView, mMeaningMalayalamTextView, mVerseExplanation;
        RecyclerView mWordMeaningRecylerView;
        RecyclerView.LayoutManager layoutManager;
        RelativeLayout menulayout;
        ImageView mOptionMenu, mBismiTextview;
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
            mSuraNameTextview = (TextView) view.findViewById(R.id.mSuraNameTextview);
            menulayout = (RelativeLayout) view.findViewById(R.id.menulayout);
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
