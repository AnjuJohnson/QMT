package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Listeners.TajweedListner;
import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllMenuActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllMenuTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class TajweedClassesTitlesFragmentController {
    public static Context mcontext;
    private static TajweedClassesTitlesFragmentController controller;
    public static TajweedListner quranIndexListener;
    private RestApiManager mApiManager;

    public TajweedClassesTitlesFragmentController(TajweedListner quranIndexListener, Context mcontext) {
        this.quranIndexListener = quranIndexListener;
        this.mcontext = mcontext;


    }

    public TajweedClassesTitlesFragmentController() {

    }

    public static TajweedClassesTitlesFragmentController getController() {

        if (controller == null) {
            controller = new TajweedClassesTitlesFragmentController();

        }
        return controller;
    }

    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void startfetching() {


        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_TAJWEED_TITLES, null);
        EventBus.getDefault().post(repoRequestEvent);
    }

    public void Servicerequest() {
        mApiManager = new RestApiManager();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Tajweed>> call = service.getAllTitles();
        call.enqueue(new Callback<ResultObject<Tajweed>>() {
            @Override
            public void onResponse(Call<ResultObject<Tajweed>> call, Response<ResultObject<Tajweed>> response) {

                quranIndexListener.fetchingsuccess(response.body().getData());


            }

            @Override
            public void onFailure(Call<ResultObject<Tajweed>> call, Throwable t) {

                quranIndexListener.fetchingfailure();
            }
        });

    }

}
