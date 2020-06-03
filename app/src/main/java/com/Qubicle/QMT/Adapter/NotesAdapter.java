package com.Qubicle.QMT.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Notes> bookmarkList;
    BookmarkListner bookmarkListner;

    public NotesAdapter(BookmarkListner bookmarkListner, List<Notes> bookmarkList) {
        this.bookmarkList = bookmarkList;
       this. bookmarkListner=bookmarkListner;

    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder viewHolder, int i) {

        viewHolder.chaptername.setText("Chapter "+bookmarkList.get(i).getSurah_no() + " :" +"Verse "+ bookmarkList.get(i).getAyat_no());
viewHolder.notestextview.setText(bookmarkList.get(i).getNote());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               bookmarkListner.deletenote(bookmarkList.get(i).getNote_id(),i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chaptername,notestextview;
private ImageView delete;
        public ViewHolder(View view) {
            super(view);

            chaptername = (TextView) view.findViewById(R.id.chaptername);
            notestextview = (TextView) view.findViewById(R.id.notestextview);
            delete = (ImageView) view.findViewById(R.id.delete);
            //   mMeaningMalayalamTextView = (TextView)view.findViewById(R.id.mMeaningMalayalamTextView);


        }
    }

}
