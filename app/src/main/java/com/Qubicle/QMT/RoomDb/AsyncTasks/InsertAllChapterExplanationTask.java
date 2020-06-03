package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;

import java.util.List;

public class InsertAllChapterExplanationTask extends AsyncTask<List<ChapterExplanation>, Void, Boolean> {


    private ChapterExplanationDao chapterExplanationDao;
     Context ctx;
    public InsertAllChapterExplanationTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        chapterExplanationDao=appDatabase.chapterExplanationDao();
    }

    @Override
    protected Boolean doInBackground(List<ChapterExplanation>... models) {
        try {
            chapterExplanationDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }


}
