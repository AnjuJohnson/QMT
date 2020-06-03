package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.JuzListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;

import java.util.List;

public class GetAllVerseDetailActiveTask extends AsyncTask<Void, Void, List<VerseDetail>> {


     VerseDetailListDao versedetailListDao;
    private Context ctx;
    private String chapterno;
    public GetAllVerseDetailActiveTask(Context context,String chapterNo) {
        this.ctx = context;
this.chapterno=chapterNo;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        versedetailListDao=appDatabase.verseDetailListDao();
    }

    @Override
    protected List<VerseDetail> doInBackground(Void... voids) {
        try {
            return versedetailListDao.getVerseDetailList(chapterno);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
