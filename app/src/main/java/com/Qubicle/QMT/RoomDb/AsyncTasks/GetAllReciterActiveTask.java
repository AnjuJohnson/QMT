package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ReciterListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;

import java.util.List;

public class GetAllReciterActiveTask extends AsyncTask<Void, Void, List<Reciter>> {


     ReciterListDao versedetailListDao;
    private Context ctx;
    private String chapterno;
    public GetAllReciterActiveTask(Context context) {
        this.ctx = context;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        versedetailListDao=appDatabase.ReciterListDao();
    }

    @Override
    protected List<Reciter> doInBackground(Void... voids) {
        try {
            return versedetailListDao.getReciterlist();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
