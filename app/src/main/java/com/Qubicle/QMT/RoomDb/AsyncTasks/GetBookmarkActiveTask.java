package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.BookMarkDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;

import java.util.List;

public class GetBookmarkActiveTask extends AsyncTask<Void, Void, List<Bookmark>> {

     BookMarkDao bookMarkDao;
    private Context ctx;

    public GetBookmarkActiveTask(Context context) {
        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        bookMarkDao=appDatabase.bookMarkDao();
    }

    @Override
    protected List<Bookmark> doInBackground(Void... voids) {
        try {
            return bookMarkDao.getAllBookmark();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
