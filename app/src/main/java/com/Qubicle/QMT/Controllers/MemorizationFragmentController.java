package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.MemorizationTestListner;
import com.Qubicle.QMT.Listeners.SearchListner;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Practice;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.Response.ChapterListModelResponse;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetChapterDetailTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetChapterNotZeroActiveTask;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
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
public class MemorizationFragmentController {
    Context mcontext;
    public static String sChapterno,sVersefrom,sverseto,sTranslation,sReciter;
    Readingfragment readingfragment;
    private static  MemorizationTestListner searchListner;
    private RestApiManager mApiManager;
    private static MemorizationFragmentController controller;
    public MemorizationFragmentController(Context context, MemorizationTestListner searchListner) {
        this.mcontext=context;
        this.searchListner=searchListner;
     //   this.readingfragment=fragment;

    }

    public MemorizationFragmentController() {

    }

    public static MemorizationFragmentController getController() {

        if (controller == null) {
            controller = new MemorizationFragmentController();

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

    public void startfetching() {



        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_CHAPTERLIST_MEM, null);
        EventBus.getDefault().post(repoRequestEvent);
    }


    public void startfetchingmemorizationtest(String Chapterno,String Versefrom,String verseto,String Translation) {
sChapterno=Chapterno;
sVersefrom=Versefrom;
sverseto=verseto;
sTranslation=Translation;


        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCH_MEMORIZATION_TEST, null);
        EventBus.getDefault().post(repoRequestEvent);
    }

    public void Servicerequest() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<ChapterList>> call = service.getAllChapterListVerse();
        call.enqueue(new Callback<ResultObject<ChapterList>>() {
            @Override
            public void onResponse(Call<ResultObject<ChapterList>> call, Response<ResultObject<ChapterList>> response) {

                System.out.println("menus" + new Gson().toJson(response.body().getData()));
               // insertAll(response.body().getData());

                parsedata(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultObject<ChapterList>> call, Throwable t) {

                searchListner.fetchingfailure();
            }
        });

    }


    public void ServicerequestMemorytest() {
        mApiManager= new RestApiManager();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Verse>> call = service.getmemTestVerse(sChapterno,sVersefrom,sverseto,sTranslation);
        call.enqueue(new Callback<ResultObject<Verse>>() {
            @Override
            public void onResponse(Call<ResultObject<Verse>> call, Response<ResultObject<Verse>> response) {
                searchListner.versedetail(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultObject<Verse>> call, Throwable t) {

                searchListner.fetchingfailure();
            }
        });

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
        searchListner.datapassing(mChapterlist);
    }

    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    public void callReciterAudioCategoryService() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHRECITERAUDIO_PRACTICE, null);
        EventBus.getDefault().post(repoRequestEvent);


    }
    public void ServiceCallReciterAudio() {
        mApiManager= new RestApiManager();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<AudioCategoryReciterList>> call = service.getAllReciterAudioListVerse();
        call.enqueue(new Callback<ResultObject<AudioCategoryReciterList>>() {
            @Override
            public void onResponse(Call<ResultObject<AudioCategoryReciterList>> call, Response<ResultObject<AudioCategoryReciterList>> response) {


                searchListner.dataReciterAudiopassing(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<AudioCategoryReciterList>> call, Throwable t) {

                searchListner.fetchingfailure();
            }
        });

    }

    public void startfetchingpracticeapi(String Chapterno,String Versefrom,String verseto,String Translation,String Reciter) {
        sChapterno=Chapterno;
        sVersefrom=Versefrom;
        sverseto=verseto;
        sTranslation=Translation;
        sReciter=Reciter;



        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHPRACTICE, null);
        EventBus.getDefault().post(repoRequestEvent);
    }
    public void ServicerequestPractice() {
        mApiManager= new RestApiManager();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Practice>> call = service.getPractice(sChapterno,sVersefrom,sverseto,sTranslation,sReciter);
        call.enqueue(new Callback<ResultObject<Practice>>() {
            @Override
            public void onResponse(Call<ResultObject<Practice>> call, Response<ResultObject<Practice>> response) {
                searchListner.Practiceversedetail(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultObject<Practice>> call, Throwable t) {

                searchListner.fetchingfailure();
            }
        });

    }
}
