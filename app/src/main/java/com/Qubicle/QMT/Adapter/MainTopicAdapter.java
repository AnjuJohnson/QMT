package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class MainTopicAdapter extends RecyclerView.Adapter<MainTopicAdapter.ViewHolder> {
    private List<MaintopicIndex> chapterLists;
    QuranIndexListener quranIndexListener;
    String malayamfont;
    Context context;
    String highligh = null;

    public MainTopicAdapter(QuranIndexListener quranIndexListener, Context context, List<MaintopicIndex> chapterLists, String malayamfont) {
        this.chapterLists = chapterLists;
        this.quranIndexListener = quranIndexListener;
        this.malayamfont = malayamfont;
        this.context = context;

    }

    public void updateList(List<MaintopicIndex> list,String highlightword) {
        chapterLists = list;
           highligh=highlightword;
        notifyDataSetChanged();
    }

    @Override
    public MainTopicAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mainindex_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainTopicAdapter.ViewHolder viewHolder, int i) {
        MaintopicIndex maintopicIndex = chapterLists.get(i);

        String desc = chapterLists.get(i).getAlphabet();
        if(highligh!=null){
            if(desc.contains(highligh)){
                String rep = desc.replace(highligh,    "<b><font color=#2825A6>"+ highligh+ "</font></b>");
                viewHolder.alphabettextview.setText(Html.fromHtml(rep));
            }
            else {
                viewHolder.alphabettextview.setText(desc);
            }
        }
        else {
            viewHolder.alphabettextview.setText(desc);
        }



    //    viewHolder.alphabettextview.setText(maintopicIndex.getAlphabet());


        KeywordAdapter keywordAdapter=new KeywordAdapter(maintopicIndex.getKeyword(),quranIndexListener);

        viewHolder.mKeywordRecycler.setAdapter(keywordAdapter);
        keywordAdapter.updateList(maintopicIndex.getKeyword(),highligh);

    }

    @Override
    public int getItemCount() {
        return chapterLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView alphabettextview, chapterMeaningTextView;
        private ImageView delete;
        private RecyclerView mKeywordRecycler;

        public ViewHolder(View view) {
            super(view);

            alphabettextview = (TextView) view.findViewById(R.id.alphabettextview);
            mKeywordRecycler = (RecyclerView) view.findViewById(R.id.mKeywordRecycler);
            RecyclerView.LayoutManager layoutManager;
            mKeywordRecycler.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(context);
            mKeywordRecycler.setLayoutManager(layoutManager);

        }
    }

}
