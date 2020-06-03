package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.BookMarkDao;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;

import java.util.List;

public class GetNotesActiveTask extends AsyncTask<Void, Void, List<Notes>> {

     NoteDao notesDao;
    private Context ctx;
private String chapterno,verseno;
    public GetNotesActiveTask(Context context,String chapterno,String verseno) {
        this.ctx = context;
        this.chapterno = chapterno;
        this.verseno = verseno;

        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        notesDao=appDatabase.noteDao();
    }

    @Override
    protected List<Notes> doInBackground(Void... voids) {
        try {
            return notesDao.getallnotes(chapterno,verseno);
        } catch (Exception ignored) {
            Log.d("aaa","aaaa");
            ignored.printStackTrace();
            return null;
        }
    }



}
