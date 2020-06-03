package com.Qubicle.QMT.Adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.Models.Reference;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.ViewHolder> {
    private List<Keyword> bookmarkList;
    QuranIndexListener quranIndexListener;
    String highligh = null;
    public KeywordAdapter(List<Keyword> bookmarkList, QuranIndexListener quranIndexListener) {
        this.bookmarkList = bookmarkList;
        this. quranIndexListener=quranIndexListener;
        //highligh=hightlight;
    }


    public void updateList(List<Keyword> list, String highlightword){
        bookmarkList = list;
        highligh=highlightword;
        notifyDataSetChanged();
    }
    @Override
    public KeywordAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.keyword_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KeywordAdapter.ViewHolder viewHolder, int i) {

        String desc = bookmarkList.get(i).getKeyword();
        if(highligh!=null){
            if(desc.contains(highligh)){
                String rep = desc.replace(highligh,    "<b><font color=#2825A6>"+ highligh+ "</font></b>");
                viewHolder.keywordTextView.setText(Html.fromHtml(rep));
            }
            else {
                viewHolder.keywordTextView.setText(desc);
            }
        }
        else {

            viewHolder.keywordTextView.setText(desc);
        }

     //   viewHolder.keywordTextView.setText(bookmarkList.get(i).getKeyword());
viewHolder.mKeywordLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        quranIndexListener.passkeywordreference(bookmarkList.get(i).getId(),bookmarkList.get(i).getKeyword().trim());
    }
});


    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView keywordTextView,notestextview;
private ImageView delete;
private ConstraintLayout mKeywordLayout;
        public ViewHolder(View view) {
            super(view);

            keywordTextView = (TextView) view.findViewById(R.id.keywordTextView);
            mKeywordLayout = (ConstraintLayout) view.findViewById(R.id.mKeywordLayout);


        }
    }

}
