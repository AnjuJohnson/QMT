package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;

import java.util.List;

public class GetChapterExplanationActiveTask extends AsyncTask<Void, Void, ChapterExplanation> {

     ChapterExplanationDao chapterExplanationDao;
    private Context ctx;
    String chapterid;
    public GetChapterExplanationActiveTask(Context context,String chapterid) {
        this.ctx = context;
        this.chapterid=chapterid;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        chapterExplanationDao=appDatabase.chapterExplanationDao();
    }

    @Override
    protected ChapterExplanation doInBackground(Void... voids) {
        try {
            return chapterExplanationDao.getchapterexplanation(chapterid);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
