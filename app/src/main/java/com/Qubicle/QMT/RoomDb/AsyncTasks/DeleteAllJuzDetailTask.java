package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.JuzDetailListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;

public class DeleteAllJuzDetailTask extends AsyncTask<Void, Void, Integer> {


    private JuzDetailListDao juzDetailListDaoDao;
     Context ctx;
    public DeleteAllJuzDetailTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        juzDetailListDaoDao=appDatabase.juzDetailListDao();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            juzDetailListDaoDao.deleteall();
            return 1;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return 0;
        }
    }


}
