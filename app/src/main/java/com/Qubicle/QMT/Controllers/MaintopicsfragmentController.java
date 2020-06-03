package com.Qubicle.QMT.Controllers;

import android.app.Dialog;
import android.content.Context;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Listeners.ReciterListner;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.Response.ReciterListModelResponse;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class MaintopicsfragmentController {
    Context mcontext;
    private static MaintopicsfragmentController controller;
    private RestApiManager mApiManager;
    public static QuranIndexListener quranListner;

    public MaintopicsfragmentController(QuranIndexListener quranListner,Context context) {
        this.mcontext = context;
      this.  quranListner = quranListner;
        mApiManager = new RestApiManager();
      /*  if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    public MaintopicsfragmentController() {

    }

    public static MaintopicsfragmentController getController() {

        if (controller == null) {
            controller = new MaintopicsfragmentController();

        }
        return controller;
    }

    public void startfetching() {
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_MAINTOPICS, null);
        EventBus.getDefault().post(repoRequestEvent);


    }

    public void ServiceCall() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<MaintopicIndex>> call = service.getAllMaintopicindex();
        call.enqueue(new Callback<ResultObject<MaintopicIndex>>() {
            @Override
            public void onResponse(Call<ResultObject<MaintopicIndex>> call, Response<ResultObject<MaintopicIndex>> response) {
                System.out.println("parsemainindex" + new Gson().toJson(response.body().getData()));
                quranListner.parsemainindex(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<MaintopicIndex>> call, Throwable t) {

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
