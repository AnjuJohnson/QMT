package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.ReciterListner;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.Response.AudioResponse;
import com.Qubicle.QMT.Retrofit.Response.VerseDetailsListModelResponse;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.Retrofit.ResultObjectSingleData;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddBookmarkTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddFavouritesTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteFavouriteTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllFavouriteListTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllJuzActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllReciterAudioCategoryActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllVerseDetailActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetBookmarkActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetBookmarkExistActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetFavouritesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetNotesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllJuzTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllVerseDetailsTask;
import com.Qubicle.QMT.Views.Fragments.VerseDetailfragment;
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
public class VerseDetailFragmentController {
    Context mcontext;
    FragmentManager mFragmentManager;
    VerseDetailfragment verseDetailfragment;
    public static String Chapterid,Ayatid;
    private RestApiManager mApiManager;
    private static VerseDetailListener verseDetailListener;
    private static AudioListner audioListener;
    private static ReciterListner reciterListner;
    private static VerseDetailFragmentController controller;
    public static String sChapterNo,sVerseMinLimit,mVerseMaxLimit,sAudioCategory,sReciterAudioName;
    int i=0;
    public VerseDetailFragmentController(Context context, VerseDetailListener verseDetailListener, AudioListner audioListner, ReciterListner reciterListner) {
        this.mcontext=context;
        this.verseDetailListener=verseDetailListener;
        this.audioListener=audioListner;
        this.reciterListner=reciterListner;

        mApiManager = new RestApiManager();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public VerseDetailFragmentController(){}

    public static VerseDetailFragmentController getController() {

        if (controller == null) {
            controller = new VerseDetailFragmentController();

        }
        return controller;
    }
    public void startfetching(String chapterid,String ayatid){
        Chapterid=chapterid;
        Ayatid=ayatid;
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHVERSEDETAIL, null);
        EventBus.getDefault().post(repoRequestEvent);

    }
    public void Servicerequest() {

        System.out.println("Chapterid" +Chapterid);
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<VerseDetail>> call = service.getVerseDetail(Chapterid, Ayatid);
        call.enqueue(new Callback<ResultObject<VerseDetail>>() {
            @Override

            public void onResponse(Call<ResultObject<VerseDetail>> call, Response<ResultObject<VerseDetail>> response) {

                insertAllVerseDetails(response.body().getData());
                verseDetailListener.datapassingVerseDetail(response.body().getData());

              //  EventBus.getDefault().post(new VerseDetailsListModelResponse(response.body().getData()));
                verseDetailListener.fetchingsuccess();
            }

            @Override
            public void onFailure(Call<ResultObject<VerseDetail>> call, Throwable t) {
                verseDetailListener.fetchingfailure();
            }
        });
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

    public void addbookmark(String page, String chaptername, String chapterno, String verseno){
        Bookmark bookmark=new Bookmark();
        bookmark.setChapter_name(chaptername);
        bookmark.setSurah_no(chapterno);
        bookmark.setAyat_no(verseno);
        bookmark.setPage(page);
        bookmark.setActive("1");
        new AddBookmarkTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bookmark);

    }



    public void insertAllVerseDetails(List<VerseDetail> models) {
        if (models != null) {
            //noinspection unchecked
            new InsertAllVerseDetailsTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);
        }

    }
    public List<VerseDetail> checkdatapresent(String chapterno) {


        try {
            return new GetAllVerseDetailActiveTask(mcontext,chapterno).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
        @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onVerseDetailEvent(VerseDetailsListModelResponse verseDetailsListModelResponseEvent) {


     //   verseDetailListener.datapassingVerseDetail(verseDetailsListModelResponseEvent.getDatas());

    }
    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    public void AudioServiceCall(String AudioCategory,String Reciter,String ChapterNo, String VerseMinLimit, String VerseMaxLimit){
        this.sChapterNo=ChapterNo;
        this.sVerseMinLimit=VerseMinLimit;
        this.mVerseMaxLimit=VerseMaxLimit;
        this.sAudioCategory=AudioCategory;
        this.sReciterAudioName=Reciter;

        if(AudioCategory.equalsIgnoreCase("Verse")){
            RepoRequestEvent repoRequestEvent;
            repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                    REQUEST_TYPE_FETCHAUDIO, null);
            EventBus.getDefault().post(repoRequestEvent);
        }
        else if(AudioCategory.equalsIgnoreCase("Translation")) {
            RepoRequestEvent repoRequestEvent;
            repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                    REQUEST_TYPE_FETCHAUDIO, null);
            EventBus.getDefault().post(repoRequestEvent);
        }
        else  if(AudioCategory.equalsIgnoreCase("Tafsir")){
            RepoRequestEvent repoRequestEvent;
            repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                    REQUEST_TYPE_FETCHAUDIO, null);
            EventBus.getDefault().post(repoRequestEvent);
        }
        else  if(AudioCategory.equalsIgnoreCase("Tajweed")){
            RepoRequestEvent repoRequestEvent;
            repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                    REQUEST_TYPE_FETCHAUDIO, null);
            EventBus.getDefault().post(repoRequestEvent);
        }

    }
    public void AudioServicerequest() {
//change
        String recitername=PreferenceManager.getManager().getReciter();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Audio>> call = service.getAudio(sChapterNo, sVerseMinLimit,mVerseMaxLimit,sReciterAudioName,sAudioCategory);
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


    public void AudioTranslationServicerequest() {


        String recitername=PreferenceManager.getManager().getReciter();
        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<Audio>> call = service.getAudioTranslation(sChapterNo,sReciterAudioName,sAudioCategory);
        call.enqueue(new Callback<ResultObject<Audio>>() {
            @Override
            public void onResponse(Call<ResultObject<Audio>> call, Response<ResultObject<Audio>> response) {
                System.out.println("Audio translation" + new Gson().toJson(response.body().getData()));

                //     EventBus.getDefault().post(new AudioResponse(response.body().getData()));
                audioListener.dataaudipassing(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultObject<Audio>> call, Throwable t) {
                audioListener.fetchingaudiofailure();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onAudioEvent(AudioResponse AudioResponseEvent) {

    //    audioListener.dataaudipassing(AudioResponseEvent.getDatas());

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
    public void deletefav(Favorites favorites) {
        if (favorites != null) {
            //noinspection unchecked
            new DeleteFavouriteTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, favorites);
        }

    }
    public void callReciterAudioCategoryService() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHRECITERAUDIO, null);
        EventBus.getDefault().post(repoRequestEvent);


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
