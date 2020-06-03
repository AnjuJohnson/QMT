package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;

public class GetVerseExplanationActiveTask extends AsyncTask<Void, Void, VerseExplanation> {

     VerseExplanationDao verseExplanationDao;
    private Context ctx;
    String chapterNo;
    String verseno;
    public GetVerseExplanationActiveTask(Context context, String chapterno,String verseno) {
        this.ctx = context;
        this.chapterNo=chapterno;
        this.verseno=verseno;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        verseExplanationDao=appDatabase.verseExplanationDao();
    }

    @Override
    protected VerseExplanation doInBackground(Void... voids) {
        try {
            return verseExplanationDao.getverseexplanation(chapterNo,verseno);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
