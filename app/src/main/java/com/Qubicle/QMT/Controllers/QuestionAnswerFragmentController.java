package com.Qubicle.QMT.Controllers;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestEvent;
import com.Qubicle.QMT.Retrofit.Repo.RepoRequestType;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.QuestionAnswerfragment;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.Qubicle.QMT.Views.Fragments.SuraFragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anju on 22-08-2019.
 */
public class QuestionAnswerFragmentController {
    Context mcontext;
    FragmentManager mFragmentManager;
    QuestionAnswerfragment readingfragment;
    private static  QAListener qaListener;
    private RestApiManager mApiManager;
    public static String chapterno;
    private static QuestionAnswerFragmentController controller;
    public QuestionAnswerFragmentController(Context context, QuestionAnswerfragment fragment, QAListener qaListener) {
        this.mcontext=context;
        this.readingfragment=fragment;
        this.qaListener=qaListener;
        mApiManager = new RestApiManager();

    }

    public QuestionAnswerFragmentController() {
        mApiManager = new RestApiManager();
    }

    public static QuestionAnswerFragmentController getController() {

        if (controller == null) {
            controller = new QuestionAnswerFragmentController();

        }
        return controller;
    }
        public void startfetching (String chaptern0o) {
chapterno=chaptern0o;
            RepoRequestEvent repoRequestEvent;
            repoRequestEvent = new RepoRequestEvent<>(RepoRequestType.
                    REQUEST_TYPE_FETCHQA, null);
            EventBus.getDefault().post(repoRequestEvent);
        }
    public void Servicerequest() {


        QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
        Call<ResultObject<QA>> call = service.getQA(chapterno);
        call.enqueue(new Callback<ResultObject<QA>>() {
            @Override
            public void onResponse(Call<ResultObject<QA>> call, Response<ResultObject<QA>> response) {

                qaListener.fetchingsuccess(response.body().getData());


            }

            @Override
            public void onFailure(Call<ResultObject<QA>> call, Throwable t) {
                qaListener.fetchingfailure();
            }
        });
    }
    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
