package com.Qubicle.QMT.Base;
import android.app.Application;

import com.Qubicle.QMT.Retrofit.Repo.RepoEngine;

import java.lang.ref.WeakReference;


public class BaseApplication extends Application {
    private static WeakReference<BaseApplication> appContextReference;
  //  private AppDatabase db;
    @Override
    public void onCreate() {
        super.onCreate();
        appContextReference = new WeakReference<>(this);
        RepoEngine.getRepoEngine().init();
    }
    public static BaseApplication getApplication() {
        return appContextReference.get();
    }

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }
}
