package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;

import java.util.List;

public class GetChapterDetailTask extends AsyncTask<Void, Void, List<ChapterList>> {
    private String chapterno;
     ChapterListDao ChapterListDao;
    private Context ctx;
    public GetChapterDetailTask(Context context,String chapterNo) {
        this.ctx = context;
        this.chapterno=chapterNo;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        ChapterListDao=appDatabase.chapterlistDao();
    }

    @Override
    protected List<ChapterList> doInBackground(Void... voids) {
        try {
            return ChapterListDao.getChapterdetail(chapterno);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
