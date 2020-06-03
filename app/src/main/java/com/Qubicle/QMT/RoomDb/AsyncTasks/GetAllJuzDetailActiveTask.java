package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.JuzDetailListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;

import java.util.List;

public class GetAllJuzDetailActiveTask extends AsyncTask<Void, Void, List<JuzDetailNew>> {


     JuzDetailListDao juzdetailListDao;
    private Context ctx;
    private String juzNo;
    public GetAllJuzDetailActiveTask(Context context, String juzNo) {
        this.ctx = context;
this.juzNo=juzNo;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        juzdetailListDao=appDatabase.juzDetailListDao();
    }

    @Override
    protected List<JuzDetailNew> doInBackground(Void... voids) {
        try {
            return juzdetailListDao.getJuzDetailList(juzNo);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
