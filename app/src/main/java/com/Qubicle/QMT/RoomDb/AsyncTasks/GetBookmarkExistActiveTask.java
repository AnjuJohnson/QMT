package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.BookMarkDao;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;

public class GetBookmarkExistActiveTask extends AsyncTask<Void, Void, Bookmark> {


    BookMarkDao bookMarkDao;
    private Context ctx;
    String chaptername,verseno,page;
    public GetBookmarkExistActiveTask(Context context, String chaptername,String verseno,String page ) {
        this.ctx = context;
        this.chaptername=chaptername;
        this.verseno=verseno;
        this.page=page;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        bookMarkDao=appDatabase.bookMarkDao();
    }

    @Override
    protected Bookmark doInBackground(Void... voids) {
        try {
            return bookMarkDao.getBookmark(chaptername,verseno,page);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
