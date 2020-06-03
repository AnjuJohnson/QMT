package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.BookMarkDao;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;

public class AddBookmarkTask extends AsyncTask<Bookmark, Void, Boolean> {

    private BookMarkDao bookMarkDao;
     Context ctx;
    public AddBookmarkTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        bookMarkDao=appDatabase.bookMarkDao();
    }


    @Override
    protected Boolean doInBackground(Bookmark... chapterExplanations) {
        try {
            bookMarkDao.insert(chapterExplanations[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
