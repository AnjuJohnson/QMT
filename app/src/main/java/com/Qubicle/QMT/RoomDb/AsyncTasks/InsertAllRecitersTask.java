package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ReciterListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;

import java.util.List;

public class InsertAllRecitersTask extends AsyncTask<List<Reciter>, Void, Boolean> {


    private ReciterListDao VerseDetailListDaoDao;
     Context ctx;
    public InsertAllRecitersTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        VerseDetailListDaoDao=appDatabase.ReciterListDao();
    }

    @Override
    protected Boolean doInBackground(List<Reciter>... models) {
        try {
            VerseDetailListDaoDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
