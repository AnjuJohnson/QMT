package com.Qubicle.QMT.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.Models.Reference;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.ViewHolder> {
    private List<Reference> bookmarkList;
    QAListener bookmarkListner;

    public ReferenceAdapter(List<Reference> bookmarkList,QAListener bookmarkListner) {
        this.bookmarkList = bookmarkList;
       this. bookmarkListner=bookmarkListner;

    }

    @Override
    public ReferenceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reference_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReferenceAdapter.ViewHolder viewHolder, int i) {

        viewHolder.chaptername.setText(bookmarkList.get(i).getSurah_no() + " : " + bookmarkList.get(i).getVerse_no());
viewHolder.chaptername.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        bookmarkListner.reference(bookmarkList.get(i).getSurah_no(),bookmarkList.get(i).getVerse_no());
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

            chaptername = (TextView) view.findViewById(R.id.chapternameTextView);


        }
    }

}
