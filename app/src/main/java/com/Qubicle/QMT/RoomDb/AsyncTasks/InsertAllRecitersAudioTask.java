package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ReciterAudioListDao;
import com.Qubicle.QMT.RoomDb.Dao.ReciterListDao;

import java.util.List;

public class InsertAllRecitersAudioTask extends AsyncTask<List<AudioCategoryReciterList>, Void, Boolean> {


    private ReciterAudioListDao VerseDetailListDaoDao;
     Context ctx;
    public InsertAllRecitersAudioTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        VerseDetailListDaoDao=appDatabase.ReciterAudioListDao();
    }

    @Override
    protected Boolean doInBackground(List<AudioCategoryReciterList>... models) {
        try {
            VerseDetailListDaoDao.insertAll(models[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
