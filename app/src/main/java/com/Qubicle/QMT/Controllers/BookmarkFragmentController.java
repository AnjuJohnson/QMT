package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllVerseDetailActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetBookmarkActiveTask;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.Qubicle.QMT.Views.Fragments.SuraFragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anju on 22-08-2019.
 */
public class BookmarkFragmentController {
    Context mcontext;

    public BookmarkFragmentController(Context context) {
        this.mcontext=context;

    }
    public List<Bookmark> checkBookmarkpresent() {
        try {
            return new GetBookmarkActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
}
