package com.Qubicle.QMT.Controllers;

import android.content.Context;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Models.VerseDetail;
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
public class IndexVersefragmentController {
    Context mcontext;
    private static IndexVersefragmentController controller;
    private RestApiManager mApiManager;
    public static VerseDetailListener quranListner;
  public static   String keyid,page;
    public IndexVersefragmentController(VerseDetailListener quranListner, Context context) {
        this.mcontext = context;
      this.  quranListner = quranListner;
        mApiManager = new RestApiManager();
      /*  if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    public IndexVersefragmentController() {

    }

    public static IndexVersefragmentController getController() {

        if (controller == null) {
            controller = new IndexVersefragmentController();

        }
        return controller;
    }

    public void startfetching( String chapternoo,String versenoo) {
        keyid=chapternoo;
        page=versenoo;
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_INDEXVERSEDETAILS, null);
        EventBus.getDefault().post(repoRequestEvent);


    }

    public void ServiceCall() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<VerseDetail>> call = service.getIndexVerseDetail(keyid,page);
        call.enqueue(new Callback<ResultObject<VerseDetail>>() {
            @Override
            public void onResponse(Call<ResultObject<VerseDetail>> call, Response<ResultObject<VerseDetail>> response) {
                System.out.println("parsemainindex" + new Gson().toJson(response.body().getData()));
                quranListner.datapassingVerseDetail(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<VerseDetail>> call, Throwable t) {

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
