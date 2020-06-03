package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;

import java.util.List;

public class GetAllNewVerseExplanationActiveTask extends AsyncTask<Void, Void, List<VerseExplanation>> {


    VerseExplanationDao verseExplanationDao;
    private Context ctx;
    private String chapterno;
    public GetAllNewVerseExplanationActiveTask(Context context) {
        this.ctx = context;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        verseExplanationDao=appDatabase.verseExplanationDao();
    }

    @Override
    protected List<VerseExplanation> doInBackground(Void... voids) {
        try {
            return verseExplanationDao.getAllverseexplanation();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
