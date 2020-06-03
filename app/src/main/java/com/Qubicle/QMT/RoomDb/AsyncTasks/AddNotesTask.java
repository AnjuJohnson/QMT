package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.BookMarkDao;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;

public class AddNotesTask extends AsyncTask<Notes, Void, Boolean> {

    private NoteDao noteDao;
     Context ctx;
    public AddNotesTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        noteDao=appDatabase.noteDao();
    }


    @Override
    protected Boolean doInBackground(Notes... chapterExplanations) {
        try {
            noteDao.insert(chapterExplanations[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
