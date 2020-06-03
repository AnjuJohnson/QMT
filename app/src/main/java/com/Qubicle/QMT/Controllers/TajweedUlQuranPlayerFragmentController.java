package com.Qubicle.QMT.Controllers;

import android.content.Context;

import com.Qubicle.QMT.Listeners.TajweedUlListner;
import com.Qubicle.QMT.Models.TajweedClassAudio;
import com.Qubicle.QMT.Models.TajweedUlQuranAudio;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class TajweedUlQuranPlayerFragmentController {
    Context mcontext;
    public static String sChaptername,sVerseno,sAudioCategory,stitleid;
    Readingfragment readingfragment;
    private static TajweedUlListner amaniAudioListner;
    private RestApiManager mApiManager;
    private static TajweedUlQuranPlayerFragmentController controller;
    public TajweedUlQuranPlayerFragmentController(TajweedUlListner amaniAudioListner) {

        this.amaniAudioListner=amaniAudioListner;
     //   this.readingfragment=fragment;

    }

    public TajweedUlQuranPlayerFragmentController() {

    }

    public static TajweedUlQuranPlayerFragmentController getController() {

        if (controller == null) {
            controller = new TajweedUlQuranPlayerFragmentController();

        }
        return controller;
    }




    public void startfetching(String chapterno){
        stitleid=chapterno;
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_TAJWEEDUL_QURAN_AUDIO, null);
        EventBus.getDefault().post(repoRequestEvent);

    }



    public void Servicerequest() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<TajweedUlQuranAudio>> call = service.getTajweedUlQuranAudio(stitleid);
        call.enqueue(new Callback<ResultObject<TajweedUlQuranAudio>>() {
            @Override
            public void onResponse(Call<ResultObject<TajweedUlQuranAudio>> call, Response<ResultObject<TajweedUlQuranAudio>> response) {
                System.out.println("amaniaudio" + new Gson().toJson(response.body().getData()));
                amaniAudioListner.dataTajweedClassesAudio(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<TajweedUlQuranAudio>> call, Throwable t) {
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
