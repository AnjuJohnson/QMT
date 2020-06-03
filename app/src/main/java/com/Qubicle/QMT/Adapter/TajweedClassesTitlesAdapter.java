package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Listeners.TajweedListner;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class TajweedClassesTitlesAdapter extends RecyclerView.Adapter<TajweedClassesTitlesAdapter.ViewHolder> {
    private List<Tajweed> tajweedList;
    TajweedListner tajweedListner;
    String malayalamFont;
    Context context;
    String highligh=null;
    public TajweedClassesTitlesAdapter(TajweedListner tajweedListner, Context context, List<Tajweed> tajweedList) {
        this.tajweedList = tajweedList;
        this.tajweedListner = tajweedListner;

        this.context = context;

    }
    public void updateList(List<Tajweed> list){
        tajweedList = list;
     //   highligh=highlightword;
        notifyDataSetChanged();
    }
    @Override
    public TajweedClassesTitlesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tajweedtitles_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TajweedClassesTitlesAdapter.ViewHolder viewHolder, int i) {






//for highlighting multiple words
        String chaptername = tajweedList.get(i).getTitle();

        viewHolder.chapternameTextView.setText(chaptername);



        viewHolder.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tajweedListner.passaudioparameter(tajweedList.get(i).getOrder_by(),tajweedList.get(0).getOrder_by(),
                        tajweedList.get(tajweedList.size()-1).getOrder_by());
            }
        });

    }

    @Override
    public int getItemCount() {
        return tajweedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chapternameTextView, chapterMeaningTextView;
        private ImageView delete;
private ConstraintLayout constraintlayout;
        public ViewHolder(View view) {
            super(view);

            chapternameTextView = (TextView) view.findViewById(R.id.chapternameTextView);
        //    chapterMeaningTextView = (TextView) view.findViewById(R.id.chapterMeaningTextView);
            constraintlayout = (ConstraintLayout) view.findViewById(R.id.constraintlayout);


        }
    }

}
