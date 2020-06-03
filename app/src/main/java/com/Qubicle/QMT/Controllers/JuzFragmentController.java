package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.JuzListener;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.OfflineJuzDetailNew;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Response.JuzListModelResponse;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteAllJuzDetailTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllJuzActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllJuzDetailActiveTaskNew;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllMenuActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllJuzDetailsTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllJuzTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllMenuTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class JuzFragmentController {
    public static  Context mcontext;
    public static  JuzListener mJuzListener;
    private RestApiManager mApiManager;
    private static final String TAG = SuraFragmentController.class.getSimpleName();
    private static JuzFragmentController controller;
    Boolean isColctrRegistered = false;
    public JuzFragmentController(JuzListener juzListener, Context context) {
        mJuzListener = juzListener;
        mApiManager = new RestApiManager();
        this.mcontext = context;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public JuzFragmentController() {

    }

    public static JuzFragmentController getController() {

        if (controller == null) {
            controller = new JuzFragmentController();

        }
        return controller;
    }
    public void startfetching() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHJUZLIST, null);
        EventBus.getDefault().post(repoRequestEvent);
    }

    public void Servicerequest() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<JuzList>> call = service.getAllJuzList();
        call.enqueue(new Callback<ResultObject<JuzList>>() {
            @Override
            public void onResponse(Call<ResultObject<JuzList>> call, Response<ResultObject<JuzList>> response) {
                Log.d(TAG, "success :: ");
                System.out.println("menus" + new Gson().toJson(response.body().getData()));
                insertAll(response.body().getData());

            //    EventBus.getDefault().post(new JuzListModelResponse(response.body().getData()));
                mJuzListener.datapassing(response.body().getData());
                mJuzListener.fetchingsuccess();
            }

            @Override
            public void onFailure(Call<ResultObject<JuzList>> call, Throwable t) {
                Log.d(TAG, "Error :: ");
                mJuzListener.fetchingfailure();
            }
        });

    }
    public void startfetchingJuzDetail() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHA_OFFLINE_JUZ_FROMJUZ, null);
        EventBus.getDefault().post(repoRequestEvent);

    }

    public void ServicerequestJuzDetail() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<OfflineJuzDetailNew>> call = service.getAllOfflineJuzList();
        call.enqueue(new Callback<ResultObject<OfflineJuzDetailNew>>() {
            @Override

            public void onResponse(Call<ResultObject<OfflineJuzDetailNew>> call, Response<ResultObject<OfflineJuzDetailNew>> response) {
                formatingjudata(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<OfflineJuzDetailNew>> call, Throwable t) {
                mJuzListener .fetchingfailure();
            }
        });
    }

    public void formatingjudata(List<OfflineJuzDetailNew> offlineJuzDetaillist) {

        List<JuzDetailNew> juzDetailNewList = new ArrayList<>();
        for (OfflineJuzDetailNew offlineJuzDetailNew : offlineJuzDetaillist) {

            for (int i = 0; i < offlineJuzDetailNew.getJuz_details().size(); i++) {
                JuzDetailNew juzDetailNew = new JuzDetailNew();
                juzDetailNew.setJuzno(offlineJuzDetailNew.getJuzno());
                juzDetailNew.setSurah_no(offlineJuzDetailNew.getJuz_details().get(i).getSurah_no());
                juzDetailNew.setChapter_name(offlineJuzDetailNew.getJuz_details().get(i).getChapter_name());
                juzDetailNew.setChapter_name_meaning(offlineJuzDetailNew.getJuz_details().get(i).getChapter_name_meaning());
                juzDetailNew.setVerse(offlineJuzDetailNew.getJuz_details().get(i).getVerse());
                juzDetailNewList.add(juzDetailNew);
            }

        }

        Boolean successjuz=insertAllJuzDetails(juzDetailNewList);
        if(successjuz){
            Log.d("success juz", "juz offline");
           // verseDetailListener.successJuzDetail();
        }
        else {
        }

    }
    public Integer deletealljuzdetails() {


        try {
            return new DeleteAllJuzDetailTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public List<JuzDetailNew> checkdatapresentAlljuz() {


        try {
            return new GetAllJuzDetailActiveTaskNew(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public Boolean insertAllJuzDetails(List<JuzDetailNew> models) {
        if (models != null) {

            AsyncTask<List<JuzDetailNew>, Void, Boolean> exeRegisColctr = new InsertAllJuzDetailsTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);

            try {
                return isColctrRegistered = exeRegisColctr.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    public void insertAll(List<JuzList> models) {
        if (models != null) {
            //noinspection unchecked
            new InsertAllJuzTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);
        }

    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onJuzEvent(JuzListModelResponse juzListModelResponse) {



    }



    public List<JuzList> checkdatapresent() {


        try {
            return new GetAllJuzActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa","aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa","aaaa");
            e.printStackTrace();
        }

        return null;

    }

}
