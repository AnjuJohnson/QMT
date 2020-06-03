package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;

import java.util.List;

public class DeleteAllVerseExplanationTask extends AsyncTask<Void, Void, Integer> {


    private VerseExplanationDao verseExplanationDao;
     Context ctx;
    public DeleteAllVerseExplanationTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        verseExplanationDao=appDatabase.verseExplanationDao();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            verseExplanationDao.deleteall();
            return 1;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return 0;
        }
    }


}
