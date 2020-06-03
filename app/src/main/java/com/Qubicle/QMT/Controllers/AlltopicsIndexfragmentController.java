package com.Qubicle.QMT.Controllers;

import android.content.Context;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class AlltopicsIndexfragmentController {
    Context mcontext;
    private static AlltopicsIndexfragmentController controller;
    private RestApiManager mApiManager;
    public static QuranIndexListener quranListner;
public  static  String chapterno;
    public AlltopicsIndexfragmentController(QuranIndexListener quranListner, Context context) {
        this.mcontext = context;
      this.  quranListner = quranListner;
        mApiManager = new RestApiManager();
      /*  if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    public AlltopicsIndexfragmentController() {

    }

    public static AlltopicsIndexfragmentController getController() {

        if (controller == null) {
            controller = new AlltopicsIndexfragmentController();

        }
        return controller;
    }

    public void startfetching(String chapternoo) {
        chapterno=chapternoo;

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_ALLTOPIC_INDEX, null);
        EventBus.getDefault().post(repoRequestEvent);


    }

    public void ServiceCall() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Keyword>> call = service.getAlltopicIndex(chapterno);
        call.enqueue(new Callback<ResultObject<Keyword>>() {
            @Override
            public void onResponse(Call<ResultObject<Keyword>> call, Response<ResultObject<Keyword>> response) {
                System.out.println("parsemainindex" + new Gson().toJson(response.body().getData()));
                quranListner.parsealltopicindex(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<Keyword>> call, Throwable t) {

                quranListner.fetchingfailure();
            }
        });

    }

    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
