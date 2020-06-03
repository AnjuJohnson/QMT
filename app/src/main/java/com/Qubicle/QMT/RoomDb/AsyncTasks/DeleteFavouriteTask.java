package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.FavouritesDao;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;

public class DeleteFavouriteTask extends AsyncTask<Favorites, Void, Boolean> {

    private FavouritesDao noteDao;
     Context ctx;
    public DeleteFavouriteTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        noteDao=appDatabase.FavouritesDao();
    }


    @Override
    protected Boolean doInBackground(Favorites... chapterExplanations) {
        try {
            noteDao.delete(chapterExplanations[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
