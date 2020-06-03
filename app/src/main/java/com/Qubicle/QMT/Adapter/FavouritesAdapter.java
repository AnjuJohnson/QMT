package com.Qubicle.QMT.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.R;

import java.util.List;

/**
 * Created by Anju on 04-09-2019.
 */
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private List<Favorites> bookmarkList;
    QAListener qaListener;

    public FavouritesAdapter(QAListener bookmarkListner, List<Favorites> bookmarkList) {
        this.bookmarkList = bookmarkList;
       this. qaListener=bookmarkListner;

    }

    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouritesAdapter.ViewHolder viewHolder, int i) {

        viewHolder.chaptername.setText("Chapter "+bookmarkList.get(i).getSurah_no() + " :" +"Verse "+ bookmarkList.get(i).getAyat_no());
viewHolder.notestextview.setText(bookmarkList.get(i).getMeaning_arabic());
viewHolder.translationtextview.setText(bookmarkList.get(i).getMeaning_malayalam());
viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        qaListener.reference(bookmarkList.get(i).getSurah_no(),bookmarkList.get(i).getAyat_no());
    }
});


    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chaptername,notestextview,translationtextview;
private CardView cardView;
        public ViewHolder(View view) {
            super(view);

            chaptername = (TextView) view.findViewById(R.id.chaptername);
            notestextview = (TextView) view.findViewById(R.id.notestextview);
            translationtextview = (TextView) view.findViewById(R.id.translationtextview);
            cardView = (CardView) view.findViewById(R.id.cardView);


        }
    }

}
