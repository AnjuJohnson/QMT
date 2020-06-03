package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;

import java.util.List;

public class InsertAllVerseExplanationTask extends AsyncTask<List<VerseExplanation>, Void, Boolean> {


    private VerseExplanationDao verseExplanationDao;
     Context ctx;
    public InsertAllVerseExplanationTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        verseExplanationDao=appDatabase.verseExplanationDao();
    }

    @Override
    protected Boolean doInBackground(List<VerseExplanation>... models) {
        try {
            verseExplanationDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }


}
