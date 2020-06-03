package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.AmaniAudioListner;
import com.Qubicle.QMT.Listeners.TajweedListner;
import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.TajweedClassAudio;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetChapterDetailTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetChapterNotZeroActiveTask;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class TajweedClassesPlayerFragmentController {
    Context mcontext;
    public static String sChaptername,sVerseno,sAudioCategory,stitleid;
    Readingfragment readingfragment;
    private static TajweedListner amaniAudioListner;
    private RestApiManager mApiManager;
    private static TajweedClassesPlayerFragmentController controller;
    public TajweedClassesPlayerFragmentController(TajweedListner amaniAudioListner) {

        this.amaniAudioListner=amaniAudioListner;
     //   this.readingfragment=fragment;

    }

    public TajweedClassesPlayerFragmentController() {

    }

    public static TajweedClassesPlayerFragmentController getController() {

        if (controller == null) {
            controller = new TajweedClassesPlayerFragmentController();

        }
        return controller;
    }




    public void startfetching(String sortorder){
        stitleid=sortorder;
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_TAJWEEDCLASS_AUDIO, null);
        EventBus.getDefault().post(repoRequestEvent);

    }



    public void Servicerequest() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<TajweedClassAudio>> call = service.getTajweedClassesAudio(stitleid);
        call.enqueue(new Callback<ResultObject<TajweedClassAudio>>() {
            @Override
            public void onResponse(Call<ResultObject<TajweedClassAudio>> call, Response<ResultObject<TajweedClassAudio>> response) {
                System.out.println("amaniaudio" + new Gson().toJson(response.body().getData()));
                amaniAudioListner.dataTajweedClassesAudio(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<TajweedClassAudio>> call, Throwable t) {
                amaniAudioListner.fetchingfailure();
            }
        });
    }
    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
