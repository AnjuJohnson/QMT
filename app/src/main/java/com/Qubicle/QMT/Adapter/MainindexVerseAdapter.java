package com.Qubicle.QMT.Adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.Reference;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class MainindexVerseAdapter extends RecyclerView.Adapter<MainindexVerseAdapter.ViewHolder> {
    private List<IndexVerseList> bookmarkList;
    QuranIndexListener bookmarkListner;
    String page,keyword;

    public MainindexVerseAdapter(List<IndexVerseList> bookmarkList, QuranIndexListener bookmarkListner, String page,String keyword) {
        this.bookmarkList = bookmarkList;
        this.bookmarkListner = bookmarkListner;
        this.page = page;
        this.keyword = keyword;

    }

    @Override
    public MainindexVerseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.index_verselist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainindexVerseAdapter.ViewHolder viewHolder, int i) {

        viewHolder.mChapternoTextView.setText(bookmarkList.get(i).getTitle() + " : " + bookmarkList.get(i).getVerse_no());
        viewHolder.mMeaningArabicTextView.setText(bookmarkList.get(i).getMeaning_arabic());
        if (bookmarkList.get(i).getMeaning_malayalam() != null) {


            String desc = bookmarkList.get(i).getMeaning_malayalam();

            if(keyword!=null){
                if(desc.contains(keyword)){
                    String rep = desc.replace(keyword,    "<b><font color=#2825A6>"+ keyword+ "</font></b>");
                    // viewHolder.JuzNameTextView.setText("\u200E"+Html.fromHtml(rep));
                    viewHolder.mTranslation.setText(Html.fromHtml(rep));
                }
                else {
                    viewHolder.mTranslation.setText(desc);
                }

            }
            else {
                viewHolder.mTranslation.setText(desc);
            }


          //  viewHolder.mTranslation.setText(bookmarkList.get(i).getMeaning_malayalam());

        } else if (bookmarkList.get(i).getMeaning_malayalam_new() != null) {

            String desc = bookmarkList.get(i).getMeaning_malayalam_new();

            if(keyword!=null){
                if(desc.contains(keyword)){
                    String rep = desc.replace(keyword,    "<b><font color=#2825A6>"+ keyword+ "</font></b>");
                    // viewHolder.JuzNameTextView.setText("\u200E"+Html.fromHtml(rep));
                    viewHolder.mTranslation.setText(Html.fromHtml(rep));
                }
else {
                    viewHolder.mTranslation.setText(desc);
                }
            }
            else {

                viewHolder.mTranslation.setText(desc);
            }


           // viewHolder.mTranslation.setText(bookmarkList.get(i).getMeaning_malayalam_new());
        }


        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.passdatatiIndex(bookmarkList.get(i).getChapter_no(), bookmarkList.get(i).getVerse_no());
            }
        });
        viewHolder.mTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.passdatatiIndex(bookmarkList.get(i).getChapter_no(), bookmarkList.get(i).getVerse_no());
            }
        });

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.passdatatiIndex(bookmarkList.get(i).getChapter_no(), bookmarkList.get(i).getVerse_no());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mChapternoTextView, mMeaningArabicTextView, mTranslation;
        CardView cardview;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);

            mChapternoTextView = (TextView) view.findViewById(R.id.mChapternoTextView);
            mTranslation = (TextView) view.findViewById(R.id.mTranslation);
            mMeaningArabicTextView = (TextView) view.findViewById(R.id.mMeaningArabicTextView);
            cardview = (CardView) view.findViewById(R.id.cardview);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);


        }
    }

}
