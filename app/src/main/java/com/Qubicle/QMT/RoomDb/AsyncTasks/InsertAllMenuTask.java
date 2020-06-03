package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;


import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;

import java.util.List;

public class InsertAllMenuTask extends AsyncTask<List<ChapterList>, Void, Boolean> {

    private ChapterListDao assetDao;
     Context ctx;
    public InsertAllMenuTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        assetDao=appDatabase.chapterlistDao();
    }

    @Override
    protected Boolean doInBackground(List<ChapterList>... models) {
        try {
            assetDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
