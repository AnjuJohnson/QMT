package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.FavouritesDao;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;

import java.util.List;

public class GetFavouritesActiveTask extends AsyncTask<Void, Void, Favorites> {

     FavouritesDao notesDao;
    private Context ctx;
private String chapterno,verseno;
    public GetFavouritesActiveTask(Context context, String chapterno, String verseno) {
        this.ctx = context;
        this.chapterno = chapterno;
        this.verseno = verseno;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        notesDao=appDatabase.FavouritesDao();
    }

    @Override
    protected Favorites  doInBackground(Void... voids) {
        try {
            return notesDao.getIsFavourite(chapterno,verseno);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
