package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddNotesTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteNoteTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllNotesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetBookmarkActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetNotesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetNotesWithIdActiveTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anju on 22-08-2019.
 */
public class NotesFragmentController {
    Context mcontext;

    public NotesFragmentController(Context context) {
        this.mcontext=context;

    }
    public void addnotes(String chaptername, String chapterno, String verseno,String notess){
        Notes notes=new Notes();
        notes.setChapter_name(chaptername);
        notes.setSurah_no(chapterno);
        notes.setAyat_no(verseno);
        notes.setNote(notess);
        notes.setActive("1");
        new AddNotesTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, notes);

    }
    public List<Notes> checkNotespresent(String chapterno, String verseno) {
        try {
            return new GetNotesActiveTask(mcontext,chapterno,verseno).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }


    public List<Notes> getAllNotes() {
        try {
            return new GetAllNotesActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }


    public Notes checkNotespresentId(String noteid) {
        try {
            return new GetNotesWithIdActiveTask(mcontext,noteid).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();
        }

        return null;

    }

    public void delete(Notes note) {
        if (note != null) {
            //noinspection unchecked
            new DeleteNoteTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, note);
        }

    }

}
