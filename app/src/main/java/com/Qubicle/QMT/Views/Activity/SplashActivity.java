package com.Qubicle.QMT.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseActivity;
import com.Qubicle.QMT.Controllers.ReciterActivityController;
import com.Qubicle.QMT.Listeners.ReciterListner;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/*
Created by Anju on 21-08-2019.

*/
public class SplashActivity extends BaseActivity implements ReciterListner {
    ReciterActivityController reciterActivityController;
    List<Reciter> reciterList=new ArrayList<>();
    List<AudioCategoryReciterList> reciterAudioCategoryList=new ArrayList<>();
    TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("countdash=1");
        setContentView(R.layout.activity_splash);
        version=(TextView)findViewById(R.id.version);
        version.setText("Version  "+"11");
        reciterActivityController = new ReciterActivityController(getApplicationContext(),this);

        if(isNetworkAvailable()){
            System.out.println("countsplash=2");
            reciterActivityController.callReciterService();

        }
        else {
            reciterList = reciterActivityController.checkreciterpresent();
            if (reciterList == null||reciterList.size()==0) {

            }
            else {

                dataReciterpassing(reciterList);
            }
        }

    }


    @Override
    public void fetchingfailure() {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progress() {

    }

    @Override
    public void dataReciterpassing(List<Reciter> reciterList) {

        Constants.RECITER_LIST=reciterList;
        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
       /*  if(isNetworkAvailable()){

            reciterActivityController.callReciterAudioCategoryService();

        }
       else {
            reciterAudioCategoryList = reciterActivityController.checkreciterAudioCategorypresent();
            if (reciterAudioCategoryList == null||reciterAudioCategoryList.size()==0) {

            }
            else {

                dataReciterAudiopassing(reciterAudioCategoryList);
            }
        }
*/

    }

    @Override
    public void dataReciterAudiopassing(List<AudioCategoryReciterList> audio) {
       //Constants.RECITER_AUDIO_CATEGORY_LIST=audio;


    }

    @Override
    public void onResume(){
        super.onResume();
        if(isNetworkAvailable()){
            System.out.println("countsplash=3");
            reciterActivityController = new ReciterActivityController(getApplicationContext(),this);
            reciterActivityController. callReciterService();

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}