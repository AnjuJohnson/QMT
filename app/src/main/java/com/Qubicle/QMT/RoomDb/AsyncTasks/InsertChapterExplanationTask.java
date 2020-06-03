package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;

import java.util.List;

public class InsertChapterExplanationTask extends AsyncTask<ChapterExplanation, Void, Boolean> {

    private ChapterExplanationDao chapterExplanationDao;
     Context ctx;
    public InsertChapterExplanationTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        chapterExplanationDao=appDatabase.chapterExplanationDao();
    }


    @Override
    protected Boolean doInBackground(ChapterExplanation... chapterExplanations) {
        try {
            chapterExplanationDao.insert(chapterExplanations[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
