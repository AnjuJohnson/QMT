package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Response.ChapterExplanationResponse;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObjectSingleData;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetChapterExplanationActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertChapterExplanationTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 27-08-2019.
 */
public class ChapterExplanationController {
    Context mcontext;
    private RestApiManager mApiManager;
    private static SuraListener suraListener;
    public static String Chapterid;
    private static ChapterExplanationController controller;
    public ChapterExplanationController(SuraListener suraListener,Context mcontext) {
        this.mcontext = mcontext;
        this.suraListener=suraListener;
        mApiManager = new RestApiManager();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }



    public ChapterExplanationController() {

    }
    public static ChapterExplanationController getController() {

        if (controller == null) {
            controller = new ChapterExplanationController();

        }
        return controller;
    }
    public void startfetching(String chapterid){
        Chapterid=chapterid;
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHCHAPTER_EXPLANATION, null);
        EventBus.getDefault().post(repoRequestEvent);

    }
    public void fetchchapterid(String chapterid){


    }
    public void Servicerequest() {
        System.out.println("Chapterid" +Chapterid);
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObjectSingleData<ChapterExplanation>> call = service.getexplanation(Chapterid);
        call.enqueue(new Callback<ResultObjectSingleData<ChapterExplanation>>() {
            @Override
            public void onResponse(Call<ResultObjectSingleData<ChapterExplanation>> call, Response<ResultObjectSingleData<ChapterExplanation>> response) {
                System.out.println("explanation" + new Gson().toJson(response.body().getData()));
                insert(response.body().getData());
                EventBus.getDefault().post(new ChapterExplanationResponse(response.body().getData()));
                suraListener.fetchingsuccess();
            }

            @Override
            public void onFailure(Call<ResultObjectSingleData<ChapterExplanation>> call, Throwable t) {
                suraListener.fetchingfailure();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMenuEvent(ChapterExplanationResponse chapterExplanationResponseEvent) {

        suraListener.datapassingChapterExplanation(chapterExplanationResponseEvent.getData());

    }
    public void insert(ChapterExplanation chapterExplanation) {
        if (chapterExplanation != null) {
            //noinspection unchecked
            new InsertChapterExplanationTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, chapterExplanation);
        }

    }

    public ChapterExplanation checkExplanationdatapresent(String chapterid) {


        try {
            return new GetChapterExplanationActiveTask(mcontext,chapterid).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa","aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa","aaaa");
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
