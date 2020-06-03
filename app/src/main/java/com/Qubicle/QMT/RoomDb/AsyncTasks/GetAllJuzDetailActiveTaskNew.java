package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.JuzDetailListDao;

import java.util.List;

public class GetAllJuzDetailActiveTaskNew extends AsyncTask<Void, Void, List<JuzDetailNew>> {


     JuzDetailListDao juzdetailListDao;
    private Context ctx;
    private String juzNo;
    public GetAllJuzDetailActiveTaskNew(Context context) {
        this.ctx = context;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        juzdetailListDao=appDatabase.juzDetailListDao();
    }

    @Override
    protected List<JuzDetailNew> doInBackground(Void... voids) {
        try {
            return juzdetailListDao.getJuzDetailListAll();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
