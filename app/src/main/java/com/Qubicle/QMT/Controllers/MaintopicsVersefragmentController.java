package com.Qubicle.QMT.Controllers;

import android.content.Context;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class MaintopicsVersefragmentController {
    Context mcontext;
    private static MaintopicsVersefragmentController controller;
    private RestApiManager mApiManager;
    public static QuranIndexListener quranListner;
    private static String Referenceid;
    private String sTranslationNumber;

    public MaintopicsVersefragmentController(QuranIndexListener quranListner) {

      this.  quranListner = quranListner;
        mApiManager = new RestApiManager();
      /*  if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    public MaintopicsVersefragmentController() {

    }

    public static MaintopicsVersefragmentController getController() {

        if (controller == null) {
            controller = new MaintopicsVersefragmentController();

        }
        return controller;
    }

    public void startfetching(String referenceid,String page) {
        Referenceid=referenceid;
        if(page.equalsIgnoreCase("maintopic")){

            RepoRequestEvent repoRequestEvent;
            repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                    REQUEST_TYPE_FETCH_MAINTOPICS_VERSE, null);
            EventBus.getDefault().post(repoRequestEvent);
        }
        else if(page.equalsIgnoreCase("alltopic")){
            RepoRequestEvent repoRequestEvent;
            repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                    REQUEST_TYPE_FETCH_ALLTOPIC_VERSE, null);
            EventBus.getDefault().post(repoRequestEvent);

        }




    }

    public void ServiceCall() {
        sTranslationNumber = PreferenceManager.getManager().getTranslation();
        if (sTranslationNumber.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
            sTranslationNumber = "meaning_malayalam";
        } else if (sTranslationNumber.equalsIgnoreCase("Amani Thafseer")) {
            sTranslationNumber = "meaning_malayalam_new";
        }
        mApiManager = new RestApiManager();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<IndexVerseList>> call = service.getAllMainVerse(Referenceid,sTranslationNumber);
        call.enqueue(new Callback<ResultObject<IndexVerseList>>() {
            @Override
            public void onResponse(Call<ResultObject<IndexVerseList>> call, Response<ResultObject<IndexVerseList>> response) {
                System.out.println("parsemainindex" + new Gson().toJson(response.body().getData()));
                quranListner.parsemainindexverse(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<IndexVerseList>> call, Throwable t) {
                quranListner.fetchingfailure();
            }
        });

    }


    public void AlltopicServiceCall() {
        sTranslationNumber = PreferenceManager.getManager().getTranslation();
        if (sTranslationNumber.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
            sTranslationNumber = "meaning_malayalam";
        } else if (sTranslationNumber.equalsIgnoreCase("Amani Thafseer")) {
            sTranslationNumber = "meaning_malayalam_new";
        }
        mApiManager = new RestApiManager();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<IndexVerseList>> call = service.getAlltopicVerse(Referenceid,sTranslationNumber);
        call.enqueue(new Callback<ResultObject<IndexVerseList>>() {
            @Override
            public void onResponse(Call<ResultObject<IndexVerseList>> call, Response<ResultObject<IndexVerseList>> response) {
                System.out.println("parsemainindex" + new Gson().toJson(response.body().getData()));
                quranListner.parsemainindexverse(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<IndexVerseList>> call, Throwable t) {
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
