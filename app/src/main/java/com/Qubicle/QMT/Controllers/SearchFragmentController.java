package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Qubicle.QMT.Listeners.SearchListner;
import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllMenuActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetChapterNotZeroActiveTask;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.Qubicle.QMT.Views.Fragments.SuraFragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class SearchFragmentController {
    Context mcontext;
    public static String sChaptername,sVerseno,sKeyword,sTranslation;
    Readingfragment readingfragment;
    private static  SearchListner searchListner;
    private RestApiManager mApiManager;
    private static SearchFragmentController controller;
    public SearchFragmentController(Context context, SearchListner searchListner) {
        this.mcontext=context;
        this.searchListner=searchListner;
     //   this.readingfragment=fragment;

    }

    public SearchFragmentController() {

    }

    public static SearchFragmentController getController() {

        if (controller == null) {
            controller = new SearchFragmentController();

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
    public void startfetching(String chaptername,String verseno,String keyword,String translation){
        sChaptername=chaptername;
        sVerseno=verseno;
        sKeyword=keyword;
        sTranslation=translation;
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_SEARCH, null);
        EventBus.getDefault().post(repoRequestEvent);

    }



    public void Servicerequest() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<VerseDetail>> call = service.getsearchresult(sChaptername, sVerseno,sKeyword,sTranslation);
        call.enqueue(new Callback<ResultObject<VerseDetail>>() {
            @Override
            public void onResponse(Call<ResultObject<VerseDetail>> call, Response<ResultObject<VerseDetail>> response) {
                searchListner.dataSearchpassing(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<VerseDetail>> call, Throwable t) {
                searchListner.fetchingfailure();
            }
        });
    }
    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
