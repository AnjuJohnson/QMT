package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.AmaniAudioListner;
import com.Qubicle.QMT.Listeners.SearchListner;
import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.VerseDetail;
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
public class AmaniPlayerFragmentController {
    Context mcontext;
    public static String sChaptername,sVerseno,sAudioCategory,sChapterno;
    Readingfragment readingfragment;
    private static  AmaniAudioListner amaniAudioListner;
    private RestApiManager mApiManager;
    private static AmaniPlayerFragmentController controller;
    public AmaniPlayerFragmentController(AmaniAudioListner amaniAudioListner) {

        this.amaniAudioListner=amaniAudioListner;
     //   this.readingfragment=fragment;

    }

    public AmaniPlayerFragmentController() {

    }

    public static AmaniPlayerFragmentController getController() {

        if (controller == null) {
            controller = new AmaniPlayerFragmentController();

        }
        return controller;
    }


    public List<ChapterList> checkdatapresent() {

        try {
            return new GetChapterNotZeroActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa","aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa","aaaa");
            e.printStackTrace();
        }

        return null;

    }


    public List<ChapterList> checkchapterdetail(String chapterno) {

        try {
            return new GetChapterDetailTask(mcontext,chapterno).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa","aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa","aaaa");
            e.printStackTrace();
        }

        return null;

    }


    public void startfetching(String chapterno,String page,String verseno){
        if(page.equalsIgnoreCase("RECITATION")){
            sAudioCategory="recitation";
        }
else if (page.equalsIgnoreCase("DIRECT TRANSLATION")){
            sAudioCategory="direct translation";
        }

        else if (page.equalsIgnoreCase("AMANI TAFSEER")){
            sAudioCategory="Amani Thafseer";
        }


        sChapterno=chapterno;
        sVerseno=verseno;


        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_AMANIAUDIO, null);
        EventBus.getDefault().post(repoRequestEvent);

    }



    public void Servicerequest() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<AmaniAudio>> call = service.getAmaniAudio(sChapterno, sVerseno,sAudioCategory);
        call.enqueue(new Callback<ResultObject<AmaniAudio>>() {
            @Override
            public void onResponse(Call<ResultObject<AmaniAudio>> call, Response<ResultObject<AmaniAudio>> response) {
                System.out.println("amaniaudio" + new Gson().toJson(response.body().getData()));
                amaniAudioListner.dataAmaniAudio(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<AmaniAudio>> call, Throwable t) {
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
