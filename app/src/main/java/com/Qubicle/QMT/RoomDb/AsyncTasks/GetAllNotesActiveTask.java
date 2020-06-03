package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;

import java.util.List;

public class GetAllNotesActiveTask extends AsyncTask<Void, Void, List<Notes>> {

     NoteDao notesDao;
    private Context ctx;

    public GetAllNotesActiveTask(Context context) {
        this.ctx = context;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        notesDao=appDatabase.noteDao();
    }

    @Override
    protected List<Notes> doInBackground(Void... voids) {
        try {
            return notesDao.getFullNotes();
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
