package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.JuzListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;

import java.util.List;

public class InsertAllVerseDetailsTask extends AsyncTask<List<VerseDetail>, Void, Boolean> {


    private VerseDetailListDao VerseDetailListDaoDao;
     Context ctx;
    public InsertAllVerseDetailsTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        VerseDetailListDaoDao=appDatabase.verseDetailListDao();
    }

    @Override
    protected Boolean doInBackground(List<VerseDetail>... models) {
        try {
            VerseDetailListDaoDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }


}
