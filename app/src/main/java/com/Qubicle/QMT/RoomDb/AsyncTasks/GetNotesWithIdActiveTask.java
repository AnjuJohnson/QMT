package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;

import java.util.List;

public class GetNotesWithIdActiveTask extends AsyncTask<Void, Void, Notes> {

     NoteDao notesDao;
    private Context ctx;
private String noteid;
    public GetNotesWithIdActiveTask(Context context,  String noteid) {
        this.ctx = context;

        this.noteid = noteid;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        notesDao=appDatabase.noteDao();
    }

    @Override
    protected Notes doInBackground(Void... voids) {
        try {
            return notesDao.getSinglenotes(noteid);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
