package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.JuzDetailListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;

import java.util.List;

public class InsertAllJuzDetailsTask extends AsyncTask<List<JuzDetailNew>, Void, Boolean> {


    private JuzDetailListDao juzDetailListDaoDao;
     Context ctx;
    public InsertAllJuzDetailsTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        juzDetailListDaoDao=appDatabase.juzDetailListDao();
    }

    @Override
    protected Boolean doInBackground(List<JuzDetailNew>... models) {
        try {
            juzDetailListDaoDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
