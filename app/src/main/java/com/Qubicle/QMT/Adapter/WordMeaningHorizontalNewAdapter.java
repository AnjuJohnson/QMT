package com.Qubicle.QMT.Adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Models.WordMeaningList;
import com.Qubicle.QMT.Models.WordMeaningListNew;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class WordMeaningHorizontalNewAdapter extends RecyclerView.Adapter<WordMeaningHorizontalNewAdapter.ViewHolder> {
    private List<WordMeaningListNew> verseDetailList;
String sWordMeaningRadioButton,sMalayalamFont,sArabicFont;
    public WordMeaningHorizontalNewAdapter(List<WordMeaningListNew> verseDetailList, String sWordMeaningRadioButton, String sMalayalamFont,
                                           String sArabicFont) {
        this.verseDetailList = verseDetailList;
        this.sWordMeaningRadioButton = sWordMeaningRadioButton;
        this.sArabicFont = sArabicFont;
        this.sMalayalamFont = sMalayalamFont;

    }

    @Override
    public WordMeaningHorizontalNewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wordmeaning_horizontal_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordMeaningHorizontalNewAdapter.ViewHolder viewHolder, int i) {
        if(sWordMeaningRadioButton.equalsIgnoreCase("wordmeaningOn")){
            viewHolder.mMeaningMalayalamTextView.setText(verseDetailList.get(i).getMalayalam_word());
            viewHolder.mMeaningArabicTextView.setText(verseDetailList.get(i).getArabic_word());
        }
        else if(sWordMeaningRadioButton.equalsIgnoreCase("wordmeaningOff")){
            viewHolder.mMeaningMalayalamTextView.setVisibility(View.GONE);
            viewHolder.mMeaningArabicTextView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return verseDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mMeaningArabicTextView,mMeaningMalayalamTextView;

        public ViewHolder(View view) {
            super(view);

            mMeaningArabicTextView = (TextView)view.findViewById(R.id.mMeaningArabicTextView);
            mMeaningMalayalamTextView = (TextView)view.findViewById(R.id.mMeaningMalayalamTextView);

            //arabic font

            if(sArabicFont.equalsIgnoreCase("0")){
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);

            }
            else if(sArabicFont.equalsIgnoreCase("1")){
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            }
            else if(sArabicFont.equalsIgnoreCase("2")){
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            }
            else if(sArabicFont.equalsIgnoreCase("3")){
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            }
            else if(sArabicFont.equalsIgnoreCase("4")){
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            }
            else if(sArabicFont.equalsIgnoreCase("5")){
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            }
            else if(sArabicFont.equalsIgnoreCase("6")){
                mMeaningArabicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            }

////////
            if(sMalayalamFont.equalsIgnoreCase("0")){
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);

            }
            else if(sMalayalamFont.equalsIgnoreCase("1")){
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            }
            else if(sMalayalamFont.equalsIgnoreCase("2")){
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            }
            else if(sMalayalamFont.equalsIgnoreCase("3")){
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            }
            else if(sMalayalamFont.equalsIgnoreCase("4")){
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            }
            else if(sMalayalamFont.equalsIgnoreCase("5")){
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            }
            else if(sMalayalamFont.equalsIgnoreCase("6")){
                mMeaningMalayalamTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            }

        }
    }

}
