package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddNotesTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteNoteTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllFavouriteListTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllNotesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetNotesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetNotesWithIdActiveTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anju on 22-08-2019.
 */
public class FavouritesFragmentController {
    Context mcontext;

    public FavouritesFragmentController(Context context) {
        this.mcontext=context;

    }

  /*  public List<Notes> checkNotespresent(String chapterno, String verseno) {
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

    }*/


    public List<Favorites>  getAllFavourites() {
        try {
            return new GetAllFavouriteListTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }


}
