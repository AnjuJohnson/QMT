package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;
import com.Qubicle.QMT.RoomDb.Dao.JuzListDao;

import java.util.List;

public class GetAllJuzActiveTask extends AsyncTask<Void, Void, List<JuzList>> {


     JuzListDao juzListDao;
    private Context ctx;
    public GetAllJuzActiveTask(Context context) {
        this.ctx = context;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        juzListDao=appDatabase.juzlistDao();
    }

    @Override
    protected List<JuzList> doInBackground(Void... voids) {
        try {
            return juzListDao.getJuzList();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
