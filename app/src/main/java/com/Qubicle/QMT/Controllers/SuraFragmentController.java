package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.QuranIndexListener;
import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Response.ChapterListModelResponse;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllMenuActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllMenuTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class SuraFragmentController {
    public static Context mcontext;
    private RestApiManager mApiManager;
    public static SuraListener mSuraListener;
    private static SuraFragmentController controller;
    private static final String TAG = SuraFragmentController.class.getSimpleName();
    List<String> listDataHeader;
    HashMap<List<String>, List<String>> listDataChild;
    QuranIndexListener suraListener1;
    public SuraFragmentController(SuraListener suraListener, Context context) {
        mSuraListener = suraListener;
        mApiManager = new RestApiManager();
        this.mcontext = context;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    public SuraFragmentController() {

    }

    public static SuraFragmentController getController() {

        if (controller == null) {
            controller = new SuraFragmentController();

        }
        return controller;
    }

    public void startfetching() {
        //  Servicerequest();
        // mMaincallbacklistener.progress();


        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHCHAPTERLIST, null);
        EventBus.getDefault().post(repoRequestEvent);
    }

    public void Servicerequest() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<ChapterList>> call = service.getAllChapterListVerse();
        call.enqueue(new Callback<ResultObject<ChapterList>>() {
            @Override
            public void onResponse(Call<ResultObject<ChapterList>> call, Response<ResultObject<ChapterList>> response) {
                Log.d(TAG, "success :: ");
                System.out.println("menus" + new Gson().toJson(response.body().getData()));
                   insertAll(response.body().getData());

                EventBus.getDefault().post(new ChapterListModelResponse(response.body().getData()));
                   mSuraListener.fetchingsuccess();
            }

            @Override
            public void onFailure(Call<ResultObject<ChapterList>> call, Throwable t) {
                Log.d(TAG, "Error :: ");
                mSuraListener.fetchingfailure();
            }
        });

    }

    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMenuEvent(ChapterListModelResponse chapterResponseEvent) {
        parsedata(chapterResponseEvent.getDatas());

    }

    public void parsedata(List<ChapterList> allcountries) {

        List<ChapterList> mChapterlist = new ArrayList<>();

        for (int c = 0; c < allcountries.size(); c++) {
            ChapterList chapterList = new ChapterList();
            chapterList.setChapter_id(allcountries.get(c).getChapter_id());
            chapterList.setSurah_no(allcountries.get(c).getSurah_no());
            chapterList.setChapter_name(allcountries.get(c).getChapter_name());
            chapterList.setChapter_name_meaning(allcountries.get(c).getChapter_name_meaning());
            chapterList.setArabic_title(allcountries.get(c).getArabic_title());
            List<AyatList> ayatListList=new ArrayList<>();
            for (int d = 0; d < allcountries.get(c).getAyat().size(); d++) {
                AyatList ayat=new AyatList();
                ayat.setAyat_id(allcountries.get(c).getAyat().get(d).getAyat_id());
                ayat.setAyat_no(allcountries.get(c).getAyat().get(d).getAyat_no());
                ayatListList.add(ayat);

            }
            chapterList.setAyat(ayatListList);
            mChapterlist.add(chapterList);

        }


//pass data to mainactivity for setting in adapter
        mSuraListener.datapassing(mChapterlist);
    }


    public List<ChapterList> checkdatapresent() {


        try {
            return new GetAllMenuActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa","aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa","aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public void insertAll(List<ChapterList> models) {
        if (models != null) {
            //noinspection unchecked
            new InsertAllMenuTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);
        }

    }
}
