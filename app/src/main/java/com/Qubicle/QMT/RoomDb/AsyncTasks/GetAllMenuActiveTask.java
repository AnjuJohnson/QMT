package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;

import java.util.List;

public class GetAllMenuActiveTask extends AsyncTask<Void, Void, List<ChapterList>> {

     ChapterListDao ChapterListDao;
    private Context ctx;
    public GetAllMenuActiveTask(Context context) {
        this.ctx = context;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        ChapterListDao=appDatabase.chapterlistDao();
    }

    @Override
    protected List<ChapterList> doInBackground(Void... voids) {
        try {
            return ChapterListDao.getChapterList();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
