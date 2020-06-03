package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.Response.VerseExplanationResponse;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObjectSingleData;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetVerseExplanationActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertVerseExplanationTask;
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
public class VerseExplanationController {
    Context mcontext;
    private RestApiManager mApiManager;
    private static VerseDetailListener verseDetailListener;
    public static String sChapterid,sVerseno;
    private static VerseExplanationController controller;
    public VerseExplanationController(VerseDetailListener verseDetailListener, Context mcontext) {
        this.mcontext = mcontext;
        this.verseDetailListener=verseDetailListener;
        mApiManager = new RestApiManager();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }



    public VerseExplanationController() {

    }
    public static VerseExplanationController getController() {

        if (controller == null) {
            controller = new VerseExplanationController();

        }
        return controller;
    }
    public void startfetching(String chapterid,String verseno){
        sChapterid=chapterid;
        sVerseno=verseno;

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHVERSE_EXPLANATION, null);
        EventBus.getDefault().post(repoRequestEvent);

    }

    public void Servicerequest() {
        System.out.println("Chapterid" +sChapterid);
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObjectSingleData<VerseExplanation>> call = service.getVerseexplanation(sChapterid,sVerseno);
        call.enqueue(new Callback<ResultObjectSingleData<VerseExplanation>>() {
            @Override
            public void onResponse(Call<ResultObjectSingleData<VerseExplanation>> call, Response<ResultObjectSingleData<VerseExplanation>> response) {
                System.out.println("VERSEexplanation" + new Gson().toJson(response.body().getData()));
                insert(response.body().getData());
                EventBus.getDefault().post(new VerseExplanationResponse(response.body().getData()));
                verseDetailListener.fetchingsuccess();
            }

            @Override
            public void onFailure(Call<ResultObjectSingleData<VerseExplanation>> call, Throwable t) {
                verseDetailListener.fetchingfailure();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onVerseExplanationEvent(VerseExplanationResponse verseExplanationResponseEvent) {

        verseDetailListener.datapassingChapterExplanation(verseExplanationResponseEvent.getData());

    }
    public void insert(VerseExplanation verseExplanation) {
        if (verseExplanation != null) {
            //noinspection unchecked
            new InsertVerseExplanationTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, verseExplanation);
        }

    }

    public VerseExplanation checkExplanationdatapresent(String chapterNo,String verseNo) {
        try {
            return new GetVerseExplanationActiveTask(mcontext,chapterNo,verseNo).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
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
