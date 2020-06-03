package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.JuzListener;
import com.Qubicle.QMT.Listeners.ReciterListner;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Response.JuzDetailsListModelResponse;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddBookmarkTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddFavouritesTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteAllJuzDetailTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteFavouriteTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllFavouriteListTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllJuzDetailActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllJuzDetailActiveTaskNew;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllReciterAudioCategoryActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllVerseDetailActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetBookmarkExistActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetFavouritesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllJuzDetailsTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllVerseDetailsTask;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class JuzDetailFragmentController {
    Context mcontext;
    public static String sJuzno;
    private RestApiManager mApiManager;
    private static JuzListener verseDetailListener;
    private static JuzDetailFragmentController controller;
    private static AudioListner audioListener;
    private static ReciterListner reciterListner;

    public static String sChapterfrom,sChapterto,sVerseMinLimit,mVerseMaxLimit,sAudio_category,sReciterAudioName;
    public JuzDetailFragmentController(Context context, JuzListener verseDetailListener, AudioListner audioListner, ReciterListner reciterListner) {
        this.mcontext=context;
        this.verseDetailListener=verseDetailListener;
        this.audioListener=audioListner;
        this.reciterListner=reciterListner;
        mApiManager = new RestApiManager();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public JuzDetailFragmentController(){

    }

    public static JuzDetailFragmentController getController() {

        if (controller == null) {
            controller = new JuzDetailFragmentController();

        }
        return controller;
    }
    public void startfetching(String juzno){
        sJuzno=juzno;

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHJUZDETAIL, null);
        EventBus.getDefault().post(repoRequestEvent);

    }
    public void Servicerequest() {

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<JuzDetailNew>> call = service.getJuzDetail(sJuzno);
        call.enqueue(new Callback<ResultObject<JuzDetailNew>>() {
            @Override
            public void onResponse(Call<ResultObject<JuzDetailNew>> call, Response<ResultObject<JuzDetailNew>> response) {

               // insertAllJuzDetails(response.body().getData());

              //  EventBus.getDefault().post(new JuzDetailsListModelResponse(response.body().getData()));
                verseDetailListener.fetchingsuccess();
                verseDetailListener.datapassingJuzDetail(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultObject<JuzDetailNew>> call, Throwable t) {
                verseDetailListener.fetchingfailure();
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onJuzDetailEvent(JuzDetailsListModelResponse juzDetailsListModelResponseEvent) {

     //   verseDetailListener.datapassingJuzDetail(juzDetailsListModelResponseEvent.getDatas());

    }
    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    public void AudioServiceCall(String audio_category,String Reciter,String chapterfrom,String chapterto, String VerseMinLimit, String VerseMaxLimit){
        this.sChapterfrom=chapterfrom;
        this.sChapterto=chapterto;
        this.sVerseMinLimit=VerseMinLimit;
        this.mVerseMaxLimit=VerseMaxLimit;
        this.sAudio_category=audio_category;
        this.sReciterAudioName=Reciter;

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHJUZAUDIO, null);
        EventBus.getDefault().post(repoRequestEvent);
    }

    public void AudioServicerequest() {
        String recitername= PreferenceManager.getManager().getReciter();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Audio>> call = service.getjuzAudio(sChapterfrom,sChapterto, sVerseMinLimit,mVerseMaxLimit,sReciterAudioName,sAudio_category);
        call.enqueue(new Callback<ResultObject<Audio>>() {
            @Override
            public void onResponse(Call<ResultObject<Audio>> call, Response<ResultObject<Audio>> response) {
                System.out.println("Audio details" + new Gson().toJson(response.body().getData()));

                //     EventBus.getDefault().post(new AudioResponse(response.body().getData()));
                audioListener.dataaudipassing(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultObject<Audio>> call, Throwable t) {
                audioListener.fetchingaudiofailure();
            }
        });
    }
    public List<JuzDetailNew> checkdatapresent(String juzno) {


        try {
            return new GetAllJuzDetailActiveTask(mcontext,juzno).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }

    public void insertAllJuzDetails(List<JuzDetailNew> models) {
        if (models != null) {
            //noinspection unchecked

            for(JuzDetailNew juzDetailNew:models){
                juzDetailNew.setJuzno(sJuzno);
            }

            new InsertAllJuzDetailsTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);
        }

    }
    public void addbookmark(String page, String chaptername, String chapterno, String verseno,String juzno){
        Bookmark bookmark=new Bookmark();
        bookmark.setChapter_name(chaptername);
        bookmark.setSurah_no(chapterno);
        bookmark.setAyat_no(verseno);
        bookmark.setPage(page);
        bookmark.setActive("1");
        bookmark.setJuz_no(juzno);
        new AddBookmarkTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookmark);

    }
    public Bookmark checkBookmarkAlreadypresent( String chaptername, String verseno,String page) {
        try {
            return new GetBookmarkExistActiveTask(mcontext,chaptername,verseno,page).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public List<Favorites>  getAllFavourites() {
        try {
            return new GetAllFavouriteListTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public void addFavourites(String chaptername, String chapterno, String ayatno, String verse, String translation){
        Favorites notes=new Favorites();
        notes.setChapter_name(chaptername);
        notes.setSurah_no(chapterno);
        notes.setAyat_no(ayatno);
        notes.setMeaning_arabic(verse);
        notes.setMeaning_malayalam(translation);

        new AddFavouritesTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, notes);
    }
    public Favorites  checkFavouritespresent(String chapterno, String verseno) {
        try {
            return new GetFavouritesActiveTask(mcontext,chapterno,verseno).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public void deletefav(Favorites favorites) {
        if (favorites != null) {
            //noinspection unchecked
            new DeleteFavouriteTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, favorites);
        }

    }
    public List<AudioCategoryReciterList> checkreciterAudioCategorypresent() {

        try {
            return new GetAllReciterAudioCategoryActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public void callReciterAudioCategoryService() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHRECITERAUDIO_JUZ, null);
        EventBus.getDefault().post(repoRequestEvent);


    }
    public void ServiceCallReciterAudio() {
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<AudioCategoryReciterList>> call = service.getAllReciterAudioListVerse();
        call.enqueue(new Callback<ResultObject<AudioCategoryReciterList>>() {
            @Override
            public void onResponse(Call<ResultObject<AudioCategoryReciterList>> call, Response<ResultObject<AudioCategoryReciterList>> response) {


                reciterListner.dataReciterAudiopassing(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResultObject<AudioCategoryReciterList>> call, Throwable t) {

                reciterListner.fetchingfailure();
            }
        });

    }

}
