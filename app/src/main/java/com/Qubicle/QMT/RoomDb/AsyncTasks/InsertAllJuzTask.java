package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;
import com.Qubicle.QMT.RoomDb.Dao.JuzListDao;

import java.util.List;

public class InsertAllJuzTask extends AsyncTask<List<JuzList>, Void, Boolean> {


    private JuzListDao juzListDaoDao;
     Context ctx;
    public InsertAllJuzTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        juzListDaoDao=appDatabase.juzlistDao();
    }

    @Override
    protected Boolean doInBackground(List<JuzList>... models) {
        try {
            juzListDaoDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
