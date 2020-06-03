package com.Qubicle.QMT.RoomDb.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AppDatabase;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;

public class DeleteNoteTask extends AsyncTask<Notes, Void, Boolean> {

    private NoteDao noteDao;
     Context ctx;
    public DeleteNoteTask(Context context) {

        this.ctx = context;
        AppDatabase appDatabase =  AppDatabase.getDatabase(context);
        noteDao=appDatabase.noteDao();
    }


    @Override
    protected Boolean doInBackground(Notes... chapterExplanations) {
        try {
            noteDao.delete(chapterExplanations[0]);
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }
}
