package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class AllTopicIndexAdapter extends RecyclerView.Adapter<AllTopicIndexAdapter.ViewHolder> {
    private List<Keyword> chapterLists;
    QuranIndexListener quranIndexListener;
    String malayamfont;
    Context context;
    String highligh = null;

    public AllTopicIndexAdapter(QuranIndexListener quranIndexListener, Context context, List<Keyword> chapterLists, String malayamfont) {
        this.chapterLists = chapterLists;
        this.quranIndexListener = quranIndexListener;
        this.malayamfont = malayamfont;
        this.context = context;

    }

    public void updateList(List<Keyword> list,String highlightword) {
        chapterLists = list;
           highligh=highlightword;
        notifyDataSetChanged();
    }

    @Override
    public AllTopicIndexAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.keyword_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllTopicIndexAdapter.ViewHolder viewHolder, int i) {
        Keyword maintopicIndex = chapterLists.get(i);

        String desc = maintopicIndex.getKeyword().trim();

        if(highligh!=null){
            if(desc.contains(highligh)){
                String rep = desc.replace(highligh,    "<b><font color=#2825A6>"+ highligh+ "</font></b>");
                // viewHolder.JuzNameTextView.setText("\u200E"+Html.fromHtml(rep));

                String newword="\u200E"+rep;
                viewHolder.keywordTextView.setText(Html.fromHtml(newword));
            }
        }
        else {
            String newword="\u200E"+desc;
            // viewHolder.JuzNameTextView.setText("\u200E"+desc);
            viewHolder.keywordTextView.setText(newword);
        }

    //    viewHolder.keywordTextView.setText(maintopicIndex.getKeyword());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quranIndexListener.passkeywordreference(maintopicIndex.getId(),maintopicIndex.getKeyword().trim());
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView alphabettextview, keywordTextView;
        private ImageView delete;
        private RecyclerView mKeywordRecycler;
CardView cardView;
        public ViewHolder(View view) {
            super(view);

            keywordTextView = (TextView) view.findViewById(R.id.keywordTextView);
            cardView = (CardView) view.findViewById(R.id.cardView);


        }
    }

}
