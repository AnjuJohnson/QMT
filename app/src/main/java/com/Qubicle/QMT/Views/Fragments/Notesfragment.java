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

import com.Qubicle.QMT.Adapter.BookmarkAdapter;
import com.Qubicle.QMT.Adapter.NotesAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.BookmarkFragmentController;
import com.Qubicle.QMT.Controllers.NotesFragmentController;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;


public class Notesfragment extends BaseFragment implements BookmarkListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    NotesFragmentController notesFragmentController;
    NotesAdapter notesAdapter;
    RecyclerView mNotesRecycler;
    RecyclerView.LayoutManager layoutManager;
    TextView bookmarkcount;
    String chaptername,chapterno,verseno,page;
    List<Notes> notesList;
    public Notesfragment(Context context,String chaptername,String chapterno,String verseno,String page) {
        this.chaptername = chaptername;
        this.chapterno = chapterno;
        this.verseno = verseno;
        this.context=context;
        this.page=page;
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
        notesFragmentController = new NotesFragmentController(getActivity());
        mNotesRecycler = mFragmentView.findViewById(R.id.mNotesRecycler);
        bookmarkcount = mFragmentView.findViewById(R.id.bookmarkcount);

        mNotesRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mNotesRecycler.setLayoutManager(layoutManager);
        mNotesRecycler.addItemDecoration(new DividerItemDecoration(context, 0));
        //get all bookmarks


        if(page.equalsIgnoreCase("verse")){
            notesList = notesFragmentController.checkNotespresent(chapterno,verseno);
            if (notesList == null||notesList.size()==0) {
                Toast.makeText(getActivity(), "No Notes", Toast.LENGTH_SHORT).show();
            }
            else {

                databookmarks(notesList);
            }
        }
        else if(page.equalsIgnoreCase("dash")){
            notesList = notesFragmentController.getAllNotes();
            if (notesList == null||notesList.size()==0) {
                Toast.makeText(getActivity(), "No Notes", Toast.LENGTH_SHORT).show();
            }
            else {

                databookmarks(notesList);
            }
        }
    }

    public void databookmarks(final List<Notes> notes) {

     //   bookmarkcount.setText(Integer.toString(Notes.size()));
        if (notes != null) {

            notesAdapter = new NotesAdapter(Notesfragment.this,notes);
            mNotesRecycler.setAdapter(notesAdapter);
            }
            else {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
            }

    }


    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("NOTES");
        OnMenuIconChange.iconchange(Notesfragment.this);
        HomeActivity.fragment = Notesfragment.this;
    }


    @Override
    public void callversedetails(String page,String chapterno,String verseno,String juzno) {



    }

    @Override
    public void passVerseDetails(String page, String chaptername, String chapterno, String verseno,String juzno) {

    }

    @Override
    public void sharestring(String sharestring) {

    }

    @Override
    public void passdetailsForNotes(String chaptername, String chapterno, String verseno) {

    }

    @Override
    public void shownotes(String chaptername, String chapterno, String verseno) {

    }

    @Override
    public void popupdetailpage() {

    }

    @Override
    public void deletenote(int noteid,int position) {
        Notes notes = notesFragmentController.checkNotespresentId(String.valueOf(noteid));
        if (notes == null) {
            Toast.makeText(getActivity(), "No Notes", Toast.LENGTH_LONG).show();
        }
        else {
            notesFragmentController.delete(notes);
            notesList.remove(position);
            notesAdapter.notifyDataSetChanged();
        }

    }
}