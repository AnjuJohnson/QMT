package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.FavouritesAdapter;
import com.Qubicle.QMT.Adapter.NotesAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.FavouritesFragmentController;
import com.Qubicle.QMT.Controllers.NotesFragmentController;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;


public class Favouritesfragment extends BaseFragment implements QAListener {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    FavouritesFragmentController notesFragmentController;
    FavouritesAdapter notesAdapter;
    RecyclerView mNotesRecycler;
    RecyclerView.LayoutManager layoutManager;
    TextView bookmarkcount;
    String chaptername,chapterno,verseno,page;
    List<Favorites> notesList;
    public Favouritesfragment(Context context) {
this.context=context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.fragment_notes, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        notesFragmentController = new FavouritesFragmentController(getActivity());
        mNotesRecycler = mFragmentView.findViewById(R.id.mNotesRecycler);

        mNotesRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mNotesRecycler.setLayoutManager(layoutManager);
        mNotesRecycler.addItemDecoration(new DividerItemDecoration(context, 0));
        //get all favourites



            notesList = notesFragmentController.getAllFavourites();
            if (notesList == null||notesList.size()==0) {
                Toast.makeText(getActivity(), "No Favourites", Toast.LENGTH_SHORT).show();
            }
            else {

                databookmarks(notesList);
            }

    }

    public void databookmarks(final List<Favorites> notes) {

        if (notes != null) {

            notesAdapter = new FavouritesAdapter(Favouritesfragment.this,notes);
            mNotesRecycler.setAdapter(notesAdapter);
            }
            else {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
            }

    }


    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("FAVOURITES");
        OnMenuIconChange.iconchange(Favouritesfragment.this);
        HomeActivity.fragment = Favouritesfragment.this;
    }




    @Override
    public void fetchingsuccess(List<QA> qa) {

    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {

    }

    @Override
    public void reference(String chapterno, String verseno) {
//go to sura verse detail
        Constants.VERSEDETAILPAGE_BACKPRESS="not_reading_page";
        mOnFragmnetSwitch.onFragmentChange(new VerseDetailfragment(getActivity(),chapterno,"",verseno),"CHAPTER",true);

    }
}