package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ReciterAudioListDao;
import com.Qubicle.QMT.RoomDb.Dao.ReciterListDao;

import java.util.List;

public class GetAllReciterAudioCategoryActiveTask extends AsyncTask<Void, Void, List<AudioCategoryReciterList>> {


    ReciterAudioListDao versedetailListDao;
    private Context ctx;
    private String chapterno;
    public GetAllReciterAudioCategoryActiveTask(Context context) {
        this.ctx = context;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        versedetailListDao=appDatabase.ReciterAudioListDao();
    }

    @Override
    protected List<AudioCategoryReciterList> doInBackground(Void... voids) {
        try {
            return versedetailListDao.getReciterAudiolist();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }

    }
}
