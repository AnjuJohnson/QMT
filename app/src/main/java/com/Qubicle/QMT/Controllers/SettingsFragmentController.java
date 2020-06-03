package com.Qubicle.QMT.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.Qubicle.QMT.Listeners.AudioListner;
import com.Qubicle.QMT.Listeners.SettingsListener;
import com.Qubicle.QMT.Listeners.VerseDetailListener;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.OfflineJuzDetailNew;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.Response.AudioResponse;
import com.Qubicle.QMT.Retrofit.Response.VerseDetailsListModelResponse;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddBookmarkTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.AddFavouritesTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteAllJuzDetailTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteAllVerseExplanationTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.DeleteFavouriteTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllFavouriteListTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllJuzActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllJuzDetailActiveTaskNew;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllNewVerseExplanationActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllReciterAudioCategoryActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllVerseDetailActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetBookmarkExistActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetFavouritesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetNotesActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllChapterExplanationTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllJuzDetailsTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllVerseDetailsTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.InsertAllVerseExplanationTask;
import com.Qubicle.QMT.Views.Fragments.VerseDetailfragment;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
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
public class SettingsFragmentController {
    Context mcontext;
    FragmentManager mFragmentManager;
    VerseDetailfragment verseDetailfragment;
    public static String Chapterid, Ayatid;
    private RestApiManager mApiManager;
    private static SettingsListener verseDetailListener;
    private static AudioListner audioListener;
    private static SettingsFragmentController controller;
    public static String sChapterNo, sMinLimit, sMaxLimit, sAudioCategory, sReciterAudioName;
    int i = 0;

    Boolean isColctrRegistered = false;
    int delete;

    public SettingsFragmentController(Context context, SettingsListener verseDetailListener) {
        this.mcontext = context;
        this.verseDetailListener = verseDetailListener;


    }

    public SettingsFragmentController() {
    }

    public static SettingsFragmentController getController() {

        if (controller == null) {
            controller = new SettingsFragmentController();

        }
        return controller;
    }

    public void startfetching() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHA_OFFLINE_ALLVERSEDETAIL, null);
        EventBus.getDefault().post(repoRequestEvent);

    }

    public void startfetchingJuzDetail() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHA_OFFLINE_JUZDETAIL, null);
        EventBus.getDefault().post(repoRequestEvent);

    }

    public void startfetchingVerseExplanation(String min, String max) {
        sMinLimit = min;
        sMaxLimit = max;
        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHA_OFFLINE_VERSEEXPLANATIOM, null);
        EventBus.getDefault().post(repoRequestEvent);

    }

    public void startfetchingChapterExplanation() {

        RepoRequestEvent repoRequestEvent;
        repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                REQUEST_TYPE_FETCHA_OFFLINE_CHAPTEREXPLANATION, null);
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
                verseDetailListener.fetchingfailure();
            }
        });
    }

    public void Servicerequest() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<VerseDetail>> call = service.getAllOfflineVerseList();
        call.enqueue(new Callback<ResultObject<VerseDetail>>() {
            @Override

            public void onResponse(Call<ResultObject<VerseDetail>> call, Response<ResultObject<VerseDetail>> response) {

                Boolean success = insertAllVerseDetails(response.body().getData());

                if (success) {
                    Log.d("successs", "versee detail");
                    verseDetailListener.successVerseDetail();
                } else {
                    Log.d("not successs", "verseeeeee");
                }
                //   verseDetailListener.datapassingVerseDetail(response.body().getData());


            }

            @Override
            public void onFailure(Call<ResultObject<VerseDetail>> call, Throwable t) {
                verseDetailListener.fetchingfailure();
            }
        });
    }

    public void ServicerequestChapterExplanation() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<ChapterExplanation>> call = service.getAllOfflineChapterExplanation();
        call.enqueue(new Callback<ResultObject<ChapterExplanation>>() {
            @Override

            public void onResponse(Call<ResultObject<ChapterExplanation>> call, Response<ResultObject<ChapterExplanation>> response) {

                Boolean success = insertAllChapterExplanation(response.body().getData());

                if (success) {
                    Log.d("successs", "chapter explanation");
                    verseDetailListener.successChapterExplanation();
                } else {
                    Log.d("not successs", "chapter explanation");
                }
                //   verseDetailListener.datapassingVerseDetail(response.body().getData());


            }

            @Override
            public void onFailure(Call<ResultObject<ChapterExplanation>> call, Throwable t) {
                verseDetailListener.fetchingfailure();
            }
        });
    }

    public void ServicerequestVerseExplanation() {
        mApiManager = new RestApiManager();

        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<VerseExplanation>> call = service.getAllOfflineVerseExplanation(sMinLimit, sMaxLimit);
        call.enqueue(new Callback<ResultObject<VerseExplanation>>() {
            @Override

            public void onResponse(Call<ResultObject<VerseExplanation>> call, Response<ResultObject<VerseExplanation>> response) {

                Boolean success = insertAllVerseExplanation(response.body().getData());
                Log.d(" no result", "verse explanation");
                if (success) {
                    Log.d(" successs", "verse explanation");
                    verseDetailListener.successVerseExplanation();
                } else {
                    Log.d("not successs", "verse explanation");
                }






              /*  List<VerseExplanation> verseExplanations = checkVerseexplaantiojndatapresent();
                if (verseExplanations.size() != 0&&verseExplanations!=null) {
                    int delete = deleteallverseexplanation();
                    if (delete == 1) {
                        Log.d(" data delete", "verse explanation delete");

                        Boolean success = insertAllVerseExplanation(response.body().getData());
                        Log.d(" no result", "verse explanation");
                        if (success) {
                            Log.d(" successs", "verse explanation");
                            verseDetailListener.successVerseExplanation();
                        } else {
                            Log.d("not successs", "verse explanation");
                        }
                        //   verseDetailListener.datapassingVerseDetail(response.body().getData());

                    }

                } else {

                    Boolean success = insertAllVerseExplanation(response.body().getData());
                    Log.d(" no result", "verse explanation");
                    if (success) {
                        Log.d(" successs", "verse explanation");
                        verseDetailListener.successVerseExplanation();
                    } else {
                        Log.d("not successs", "verse explanation");
                    }


                }
*/

            }

            @Override
            public void onFailure(Call<ResultObject<VerseExplanation>> call, Throwable t) {
                verseDetailListener.fetchingfailure();
            }
        });
    }

    public Integer deleteallverseexplanation() {


        try {
            return new DeleteAllVerseExplanationTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

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

    public Boolean insertAllVerseExplanation(List<VerseExplanation> models) {
        if (models != null) {

            AsyncTask<List<VerseExplanation>, Void, Boolean> exeRegisColctr = new InsertAllVerseExplanationTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);

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


    public Boolean insertAllChapterExplanation(List<ChapterExplanation> models) {
        if (models != null) {

            AsyncTask<List<ChapterExplanation>, Void, Boolean> exeRegisColctr = new InsertAllChapterExplanationTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);

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


    public Boolean insertAllVerseDetails(List<VerseDetail> models) {
        if (models != null) {

            AsyncTask<List<VerseDetail>, Void, Boolean> exeRegisColctr = new InsertAllVerseDetailsTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, models);

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
            verseDetailListener.successJuzDetail();
        }
        else {


        }



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

    public List<VerseExplanation> checkVerseexplaantiojndatapresent() {


        try {
            return new GetAllNewVerseExplanationActiveTask(mcontext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
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
}
