package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.Qubicle.QMT.Adapter.VerseDetailAdapter;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.BookmarkFragmentController;
import com.Qubicle.QMT.Controllers.VerseDetailFragmentController;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Utility;

import java.util.List;


public class Bookmarksfragment extends BaseFragment implements BookmarkListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    BookmarkFragmentController bookmarkFragmentController;
    BookmarkAdapter bookmarkAdapter;
    RecyclerView bookmarkrecycler;
    RecyclerView.LayoutManager layoutManager;
    TextView bookmarkcount,mSendTextView;
    public Bookmarksfragment(Context context) {
        this.context = context;

    }
    public Bookmarksfragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.bookmarks, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        bookmarkcount = mFragmentView.findViewById(R.id.bookmarkcount);
        bookmarkFragmentController = new BookmarkFragmentController(getActivity());
        bookmarkrecycler = mFragmentView.findViewById(R.id.bookmarkrecycler);


        bookmarkrecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        bookmarkrecycler.setLayoutManager(layoutManager);
        bookmarkrecycler.addItemDecoration(new DividerItemDecoration(context, 0));
        //get all bookmarks


        List<Bookmark> bookmark = bookmarkFragmentController.checkBookmarkpresent();
        if (bookmark == null) {
            Toast.makeText(getActivity(), "No Bookmarks", Toast.LENGTH_SHORT).show();
        }
        else {

            databookmarks(bookmark);
        }

    }

    public void databookmarks(final List<Bookmark> bookmark) {

        bookmarkcount.setText(Integer.toString(bookmark.size()));
        if (bookmark != null||bookmark.size()==0) {

            bookmarkAdapter = new BookmarkAdapter(Bookmarksfragment.this,bookmark);
            bookmarkrecycler.setAdapter(bookmarkAdapter);
            }
            else {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
            }

    }


    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("BOOKMARKS");
        OnMenuIconChange.iconchange(Bookmarksfragment.this);
        HomeActivity.fragment = Bookmarksfragment.this;
    }


    @Override
    public void callversedetails(String page,String chapterno,String verseno,String juzno) {

        if(page.equalsIgnoreCase("Sura")){
            Constants.VERSEDETAILPAGE_BACKPRESS="not_reading_page";

            mOnFragmnetSwitch.onFragmentChange(new VerseDetailfragment(getActivity(),chapterno,"",verseno),"CHAPTER",true);
        }
        else {
            mOnFragmnetSwitch.onFragmentChange(new JuzDetailfragment(getActivity(),juzno,"bookmark",chapterno,verseno),"JUZ",true);
        }


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

    }
}