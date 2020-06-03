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

public class GetAllFavouriteListTask extends AsyncTask<Void, Void, List<Favorites>> {

     FavouritesDao notesDao;
    private Context ctx;
private String chapterno,verseno;
    public GetAllFavouriteListTask(Context context) {
        this.ctx = context;


        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        notesDao=appDatabase.FavouritesDao();
    }

    @Override
    protected List<Favorites> doInBackground(Void... voids) {
        try {
            return notesDao.getallFavourites();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
