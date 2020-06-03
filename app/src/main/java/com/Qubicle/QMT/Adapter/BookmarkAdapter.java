package com.Qubicle.QMT.Adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.WordMeaningList;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private List<Bookmark> bookmarkList;
    BookmarkListner bookmarkListner;

    public BookmarkAdapter(BookmarkListner bookmarkListner, List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
       this. bookmarkListner=bookmarkListner;

    }

    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookmark_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookmarkAdapter.ViewHolder viewHolder, int i) {

        viewHolder.chaptername.setText(bookmarkList.get(i).getChapter_name() + " :" + bookmarkList.get(i).getAyat_no() +
                " (" + bookmarkList.get(i).getPage() + ")");


        viewHolder.chaptername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkListner.callversedetails(bookmarkList.get(i).getPage(),bookmarkList.get(i).getSurah_no(),bookmarkList.get(i).getAyat_no()
                ,bookmarkList.get(i).getJuz_no());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chaptername, mMeaningMalayalamTextView;

        public ViewHolder(View view) {
            super(view);

            chaptername = (TextView) view.findViewById(R.id.chaptername);
            //   mMeaningMalayalamTextView = (TextView)view.findViewById(R.id.mMeaningMalayalamTextView);


        }
    }

}
