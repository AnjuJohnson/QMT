package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Adapter.BookmarkAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.BookmarkFragmentController;
import com.Qubicle.QMT.Controllers.NotesFragmentController;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;


public class CreateNotefragment extends BaseFragment  {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    BookmarkListner bookmarkListner;
    NotesFragmentController notesFragmentController;
EditText notesEditetext;
TextView mSaveTextView,chapternameTextView;
String chaptername,chapterno,verseno;
    public CreateNotefragment(Context context,String chaptername,String chapterno,String verseno) {
        this.chaptername = chaptername;
        this.chapterno = chapterno;
        this.verseno = verseno;
this.context=context;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.add_notes, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        bookmarkListner = (BookmarkListner) getActivity();
        notesFragmentController = new NotesFragmentController(getActivity());
        mSaveTextView = mFragmentView.findViewById(R.id.mSaveTextView);
        chapternameTextView = mFragmentView.findViewById(R.id.chapternameTextView);
        notesEditetext = mFragmentView.findViewById(R.id.notesEditetext);

        chapternameTextView.setText("chapter "+chapterno+": verse "+verseno);



        //get all bookmarks
        mSaveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesFragmentController.addnotes(chaptername,chapterno,verseno,notesEditetext.getText().toString().trim());
                Toast.makeText(getActivity(), "Note Added Successfully", Toast.LENGTH_SHORT).show();
                bookmarkListner.popupdetailpage();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("NOTE VIEW");
        OnMenuIconChange.iconchange(CreateNotefragment.this);
        HomeActivity.fragment = CreateNotefragment.this;
    }

}