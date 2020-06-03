package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class AlltopicsAdapter extends RecyclerView.Adapter<AlltopicsAdapter.ViewHolder> {
    private List<ChapterList> chapterLists;
    QuranIndexListener quranIndexListener;
    String malayalamFont;
    Context context;
    String highligh = null;

    public AlltopicsAdapter(QuranIndexListener quranIndexListener, Context context, List<ChapterList> chapterLists, String malayamfont) {
        this.chapterLists = chapterLists;
        this.quranIndexListener = quranIndexListener;
        this.malayalamFont = malayamfont;
        this.context = context;

    }

    public void updateList(List<ChapterList> list, String highlightword) {
        this.  chapterLists = list;
        this.  highligh = highlightword;
        notifyDataSetChanged();
    }

    @Override
    public AlltopicsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alltopic_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlltopicsAdapter.ViewHolder viewHolder, int i) {

        //  System.out.println("malayalmfont"+malayalamFont);
        if (malayalamFont.equalsIgnoreCase("0")) {
            viewHolder.chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            viewHolder.chapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);
        } else if (malayalamFont.equalsIgnoreCase("1")) {
            viewHolder.chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            viewHolder.chapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
        } else if (malayalamFont.equalsIgnoreCase("2")) {
            viewHolder.chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            viewHolder.chapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        } else if (malayalamFont.equalsIgnoreCase("3")) {
            viewHolder.chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            viewHolder.chapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else if (malayalamFont.equalsIgnoreCase("4")) {
            viewHolder.chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            viewHolder.chapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        } else if (malayalamFont.equalsIgnoreCase("5")) {
            viewHolder.chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            viewHolder.chapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else if (malayalamFont.equalsIgnoreCase("6")) {
            viewHolder.chapternameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            viewHolder.chapterMeaningTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }

        String chaptername = chapterLists.get(i).getChapter_name().replaceFirst("^ *", "");

        String desc = chapterLists.get(i).getChapter_name().replaceFirst("^ *", "");

        if (highligh != null) {
            if (desc.contains(highligh)) {
                String rep = desc.replace(highligh, "<b><font color=#2825A6>" + highligh + "</font></b>");
                // viewHolder.JuzNameTextView.setText("\u200E"+Html.fromHtml(rep));

                if (chapterLists.get(i).getSurah_no().equalsIgnoreCase("0")) {
                    viewHolder.chapternameTextView.setText(Html.fromHtml(rep));

                } else {

                    if (chapterLists.get(i).getSurah_no().equalsIgnoreCase("0")) {
                        viewHolder.chapternameTextView.setText(Html.fromHtml(rep));

                    } else {
                        viewHolder.chapternameTextView.setText(chapterLists.get(i).getSurah_no() + ". " + Html.fromHtml(rep));

                    }

                }
            } else {
                if (chapterLists.get(i).getSurah_no().equalsIgnoreCase("0")) {
                    viewHolder.chapternameTextView.setText(desc);

                }
                else {
                    viewHolder.chapternameTextView.setText(chapterLists.get(i).getSurah_no() + ". " + desc);

                }


            }
        } else {
            if (chapterLists.get(i).getSurah_no().equalsIgnoreCase("0")) {
                viewHolder.chapternameTextView.setText(desc);

            }
            else {
                viewHolder.chapternameTextView.setText(chapterLists.get(i).getSurah_no() + ". " + desc);

            }
        }

//for highlighting multiple words


        //   viewHolder.chapternameTextView.setText(chaptername);
        if (chapterLists.get(i).getChapter_name_meaning().isEmpty()) {
            viewHolder.chapterMeaningTextView.setVisibility(View.GONE);
        } else {
            viewHolder.chapterMeaningTextView.setVisibility(View.VISIBLE);
            //  viewHolder.chapterMeaningTextView.setText(chapterLists.get(i).getChapter_name_meaning().trim());
            String meaning = chapterLists.get(i).getChapter_name_meaning();
            if (highligh != null) {
                if (meaning.contains(highligh)) {
                    String rep = meaning.replace(highligh, "<b><font color=#2825A6>" + highligh + "</font></b>");
                    viewHolder.chapterMeaningTextView.setText(Html.fromHtml(rep));
                } else {
                    viewHolder.chapterMeaningTextView.setText(meaning.trim());
                }

            } else {

                viewHolder.chapterMeaningTextView.setText(meaning.trim());
            }


        }

        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quranIndexListener.passdatatiIndex(chaptername, chapterLists.get(i).getSurah_no());
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chapternameTextView, chapterMeaningTextView;
        private ImageView delete;
        private CardView cardview;

        public ViewHolder(View view) {
            super(view);

            chapternameTextView = (TextView) view.findViewById(R.id.chapternameTextView);
            chapterMeaningTextView = (TextView) view.findViewById(R.id.chapterMeaningTextView);
            cardview = (CardView) view.findViewById(R.id.cardview);


        }
    }

}
