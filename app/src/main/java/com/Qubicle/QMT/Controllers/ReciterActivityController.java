package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Qubicle.QMT.Listeners.ReciterListner;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.Response.ChapterListModelResponse;
import com.Qubicle.QMT.Retrofit.Response.ReciterListModelResponse;
import com.Qubicle.QMT.Retrofit.Response.VerseExplanationResponse;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllReciterActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllReciterAudioCategoryActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllVerseDetailActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllRecitersAudioTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllRecitersTask;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.Qubicle.QMT.Views.Fragments.SuraFragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class ReciterActivityController {
    public static  Context mcontext;
    private static ReciterActivityController controller;
    private RestApiManager mApiManager;
    public static ReciterListner reciterListner;

    public ReciterActivityController(Context context, ReciterListner reciterListner) {
        this.mcontext = context;
      this. reciterListner = reciterListner;
        mApiManager = new RestApiManager();
        /*if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    public ReciterActivityController() {

    }

    public static ReciterActivityController getController() {

        if (controller == null) {
            controller = new ReciterActivityController();

        }
        return controller;
    }

    public void callReciterService() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHRECITER, null);
        EventBus.getDefault().post(repoRequestEvent);


    }


    public void callReciterAudioCategoryService() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHRECITERAUDIO, null);
        EventBus.getDefault().post(repoRequestEvent);


    }

    public void ServiceCall() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Reciter>> call = service.getAllReciterListVerse();
        call.enqueue(new Callback<ResultObject<Reciter>>() {
            @Override
            public void onResponse(Call<ResultObject<Reciter>> call, Response<ResultObject<Reciter>> response) {

                 insertAll(response.body().getData());
                reciterListner.dataReciterpassing(response.body().getData());
             //   EventBus.getDefault().post(new ReciterListModelResponse(response.body().getData()));

            }

            @Override
            public void onFailure(Call<ResultObject<Reciter>> call, Throwable t) {

                reciterListner.fetchingfailure();
            }
        });

    }


    public void ServiceCallReciterAudio() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<AudioCategoryReciterList>> call = service.getAllReciterAudioListVerse();
        call.enqueue(new Callback<ResultObject<AudioCategoryReciterList>>() {
            @Override
            public void onResponse(Call<ResultObject<AudioCategoryReciterList>> call, Response<ResultObject<AudioCategoryReciterList>> response) {

                insertAllReciterAudio(response.body().getData());
                reciterListner.dataReciterAudiopassing(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<AudioCategoryReciterList>> call, Throwable t) {

                reciterListner.fetchingfailure();
            }
        });

    }


    public void insertAll(List<Reciter> models) {
        if (models != null) {
            //noinspection unchecked
            new InsertAllRecitersTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);
        }

    }
 public void insertAllReciterAudio(List<AudioCategoryReciterList> models) {
        if (models != null) {
            //noinspection unchecked
            new InsertAllRecitersAudioTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);
        }

    }



    public List<AudioCategoryReciterList> checkreciterAudioCategorypresent() {

        try {
            return new GetAllReciterAudioCategoryActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }



    public List<Reciter> checkreciterpresent() {

        try {
            return new GetAllReciterActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }

 public void destroy() {
     if (EventBus.getDefault().isRegistered(this)) {
         EventBus.getDefault().unregister(this);
     }
 }
}
