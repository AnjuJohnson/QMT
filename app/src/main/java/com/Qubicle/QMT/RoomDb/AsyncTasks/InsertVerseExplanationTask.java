package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;

public class InsertVerseExplanationTask extends AsyncTask<VerseExplanation, Void, Boolean> {

    private VerseExplanationDao verseExplanationDao;
     Context ctx;
    public InsertVerseExplanationTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        verseExplanationDao=appDatabase.verseExplanationDao();
    }


    @Override
    protected Boolean doInBackground(VerseExplanation... verseExplanations) {
        try {
            verseExplanationDao.insert(verseExplanations[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
