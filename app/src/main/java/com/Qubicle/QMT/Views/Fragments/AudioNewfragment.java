package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.ReadingFragmentController;
import com.Qubicle.QMT.Listeners.AllAudioListner;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.AutoscrollVerseListModel;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Retrofit.QmtApi;
import com.Qubicle.QMT.Retrofit.RestApiManager;
import com.Qubicle.QMT.Retrofit.ResultObject;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllReciterActiveTask;
import com.Qubicle.QMT.RoomDb.AsyncTasks.GetAllReciterAudioCategoryActiveTask;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AudioNewfragment extends BaseFragment implements ExoPlayer.EventListener {
    ImageView playButton;
    ImageView previmageView, stopimageView, nextimageView, changeperson;
    int i = 0;
    List<Audio> mAudioList = new ArrayList<>();
    private MediaPlayer mediaplayer;
    private Context context;
    private Handler mainHandler;
    private RenderersFactory renderersFactory;
    private BandwidthMeter bandwidthMeter;
    private LoadControl loadControl;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    public ExtractorMediaSource mediaSource;
    private TrackSelection.Factory trackSelectionFactory;

    private TrackSelector trackSelector;
    private Boolean isplaying = false;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    AllAudioListner allAudioListner;
    MediaSource mediaSource1;
    List<MediaSource> mediaSources = new ArrayList<>();
    private int currentIndex = 0;
    String repeat = "0";
    List<String> versenolist = new ArrayList<>();
    List<AutoscrollVerseListModel> autoscrollVerseListModels = new ArrayList<>();
    Autoscroll autoscroll;
    public static String sPage,sAudioCategory;
    private RestApiManager mApiManager;
    List<AudioCategoryReciterList> reciterAudioCategoryList = new ArrayList<>();
    public AudioNewfragment(List<Audio> mAudioList, Context context, String page,String audiocategory) {
        this.mAudioList = mAudioList;
        this.context = context;
        this.sPage = page;
        this.sAudioCategory = audiocategory;


    }

    public AudioNewfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.player_fragment, container, false);
        mainfunction(mFragmentView, savedInstanceState);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView, Bundle savedInstanceState) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        allAudioListner = (AllAudioListner) getActivity();
        autoscroll = (Autoscroll) getActivity();


        // Retrieve layout elements
        playButton = (ImageView) mFragmentView.findViewById(R.id.playbutton);
        previmageView = (ImageView) mFragmentView.findViewById(R.id.previmageView);
        stopimageView = (ImageView) mFragmentView.findViewById(R.id.stopimageView);
        nextimageView = (ImageView) mFragmentView.findViewById(R.id.nextimageView);
        changeperson = (ImageView) mFragmentView.findViewById(R.id.changeperson);
        initValues();

    }

    public void initValues() {
        renderersFactory = new DefaultRenderersFactory(context);
        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        loadControl = new DefaultLoadControl();

        Constants.player = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl);
        Constants.player.addListener(this);

        dataSourceFactory = new DefaultDataSourceFactory(context, "ExoplayerDemo");
        extractorsFactory = new DefaultExtractorsFactory();
        mainHandler = new Handler();
        /////////new
        if (Constants.ISREPEATSINGLE) {
            repeat = PreferenceManager.getManager().getRepeatSingleValue();
            int repeatsingle = Integer.parseInt(PreferenceManager.getManager().getRepeatSingleValue());
            for (int i = 0; i < mAudioList.size(); i++) {

                for (int r = 0; r <= repeatsingle; r++) {
                    MediaSource source = new ExtractorMediaSource(Uri.parse(mAudioList.get(i).getAudio().replaceAll(" ", "%20")),
                            dataSourceFactory,
                            extractorsFactory,
                            mainHandler,
                            null);
                    mediaSources.add(source);
                    //add verse no to list

                    AutoscrollVerseListModel model = new AutoscrollVerseListModel();
                    model.setSurah_no(mAudioList.get(i).getSurah_no());
                    model.setAyat_no(mAudioList.get(i).getAyat_no());

                    autoscrollVerseListModels.add(model);


                    //  versenolist.add(String.valueOf(i));


                }
            }
            mediaSource1 = new ConcatenatingMediaSource(mediaSources.toArray(new MediaSource[mediaSources.size()]));
            Constants.player.prepare(mediaSource1);
            isplaying = true;
            playButton.setImageResource(R.drawable.pause);
            Constants.player.setPlayWhenReady(true);

        }/* else if (Constants.ISREPEATMULTIPLE) {
            repeat = PreferenceManager.getManager().getRepeatMultipleValue();
            int repeatmultiple = Integer.parseInt(PreferenceManager.getManager().getRepeatMultipleValue());
            for (int i = 0; i < mAudioList.size(); i++) {
                MediaSource source = new ExtractorMediaSource(Uri.parse(mAudioList.get(i).getAudio().replaceAll(" ", "%20")),
                        dataSourceFactory,
                        extractorsFactory,
                        mainHandler,
                        null);
                mediaSources.add(source);

            }
            for (int r = 0; r < repeatmultiple + 1; r++) {
                for (int a = 0; a < mAudioList.size(); a++) {

                    //add verse no to list

                    AutoscrollVerseListModel model = new AutoscrollVerseListModel();
                    model.setSurah_no(mAudioList.get(a).getSurah_no());
                    model.setAyat_no(mAudioList.get(a).getAyat_no());

                    autoscrollVerseListModels.add(model);


                    //   versenolist.add(String.valueOf(a));
                }

            }


            mediaSource1 = new ConcatenatingMediaSource(mediaSources.toArray(new MediaSource[mediaSources.size()]));
            LoopingMediaSource loopingMediaSource = new LoopingMediaSource(mediaSource1, repeatmultiple + 1);
            isplaying = true;
            playButton.setImageResource(R.drawable.pause);
            Constants.player.prepare(loopingMediaSource);
            Constants.player.setPlayWhenReady(true);
            //    Toast.makeText(context, "repeat multiple"+repeat, Toast.LENGTH_SHORT).show();


        }*/ else  {
            repeat = "0";
            for (int i = 0; i < mAudioList.size(); i++) {
                //repeat all off
                MediaSource source = new ExtractorMediaSource(Uri.parse(mAudioList.get(i).getAudio().replaceAll(" ", "%20")),
                        dataSourceFactory,
                        extractorsFactory,
                        mainHandler,
                        null);
                mediaSources.add(source);
                //  versenolist.add(String.valueOf(i));

                AutoscrollVerseListModel model = new AutoscrollVerseListModel();
                model.setSurah_no(mAudioList.get(i).getSurah_no());
                model.setAyat_no(mAudioList.get(i).getAyat_no());

                autoscrollVerseListModels.add(model);

            }
            mediaSource1 = new ConcatenatingMediaSource(mediaSources.toArray(new MediaSource[mediaSources.size()]));
            Constants.player.prepare(mediaSource1);
            isplaying = true;
            playButton.setImageResource(R.drawable.pause);
            Constants.player.setPlayWhenReady(true);
            //  Toast.makeText(context, "none", Toast.LENGTH_SHORT).show();


        }


        ////////////
        playaudio();
    }

    public void playaudio() {

        changeperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          //show reciterlist according to the category

                reciterAudioCategoryList = checkreciterAudioCategorypresent();
                if (reciterAudioCategoryList == null || reciterAudioCategoryList.size() == 0) {

                } else {
                    for (AudioCategoryReciterList audioCategoryReciterList : reciterAudioCategoryList) {
                        if (audioCategoryReciterList.getAudio_category().trim().equalsIgnoreCase(sAudioCategory)) {
                            Constants.RECITER_AUDIO_CATEGORY_LIST = new ArrayList<>();
                            Constants.RECITER_AUDIO_CATEGORY_LIST = audioCategoryReciterList.getReciter_list();


                        }

                    }
                }


//show popupmenu

                PopupMenu menu = new PopupMenu(getActivity(), view);
                if (Constants.RECITER_AUDIO_CATEGORY_LIST != null) {

                    for (int p = 0; p < Constants.RECITER_AUDIO_CATEGORY_LIST.size(); p++) {
                        menu.getMenu().add(Menu.NONE, p, p, Constants.RECITER_AUDIO_CATEGORY_LIST.get(p).getReciter_name());
                    }
                    menu.show();
                } /*else {

                    List<Reciter>   reciterList = checkreciterpresent();
                    if (reciterList == null||reciterList.size()==0) {

                    }
                    else {
                        Constants.RECITER_LIST=reciterList;
                        for (int p = 0; p < Constants.RECITER_LIST.size(); p++) {
                            menu.getMenu().add(Menu.NONE, p, p, Constants.RECITER_LIST.get(p).getName());
                        }
                        menu.show();
                    }
                }*/

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                      //  PreferenceManager.getManager().setReciter(item.getTitle().toString());
                        playButton.setImageResource(R.drawable.play);
                        isplaying = false;
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.stop();
                        allAudioListner.stoppressed(true);
                        callapi(sPage,item.getTitle().toString());

                        return true;
                    }

                });


            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isplaying) {

                    Constants.player.setPlayWhenReady(!Constants.player.getPlayWhenReady());
                    playButton.setImageResource(R.drawable.play);
                    isplaying = false;
                } else {
                    Constants.player.setPlayWhenReady(!Constants.player.getPlayWhenReady());
                    playButton.setImageResource(R.drawable.pause);
                    isplaying = true;
                }


            }

        });
        stopimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.player != null) {
                    i = 0;
                    playButton.setImageResource(R.drawable.play);
                    isplaying = false;
                    Constants.player.setPlayWhenReady(false);
                    Constants.player.stop();
                    allAudioListner.stoppressed(true);

                }
            }
        });
        nextimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.player != null) {
                    int repeatno = 0;
                    System.out.println("audio size" + String.valueOf(mAudioList.size()));
                    repeatno = Integer.parseInt(repeat);
                    repeatno = repeatno + 1;
                   /* if (!repeat.contains("0")) {
                        repeatno = Integer.parseInt(repeat);

                    } else {
                        repeatno = Integer.parseInt(String.valueOf(repeatno));
                        repeatno=repeatno+1;
                    }*/

                    if (currentIndex < (mAudioList.size() * repeatno) - 1) {
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.seekTo(currentIndex + 1, 0);
                        Constants.player.setPlayWhenReady(true);
                    } else {
                        playButton.setImageResource(R.drawable.play);
                        isplaying = false;
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.stop();
                        //      Toast.makeText(context, "end of play", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        previmageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.player != null) {

                    if (currentIndex > 0) {
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.seekTo(currentIndex - 1, 0);
                        Constants.player.setPlayWhenReady(true);
                    } else {
                        playButton.setImageResource(R.drawable.play);
                        isplaying = false;
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.stop();
                        // Toast.makeText(context, "end of play", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        currentIndex = Constants.player.getCurrentWindowIndex();
        //call interface method to autoscroll

        if (PreferenceManager.getManager().getAutoScroll().contains("autoscrollOn")) {

            autoscroll.getVerseNo("juz", autoscrollVerseListModels.get(currentIndex).getSurah_no(), autoscrollVerseListModels.get(currentIndex).getAyat_no());

        }
        else if(PreferenceManager.getManager().getAutoScroll().contains("autoscrollOff")){
            autoscroll.getVerseNo("juz", autoscrollVerseListModels.get(currentIndex).getSurah_no(), autoscrollVerseListModels.get(currentIndex).getAyat_no());

        }

    }


    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        //    Toast.makeText(context, "PLAYER "+Constants. player.getPlaybackState(), Toast.LENGTH_SHORT).show();
        if (playbackState == Player.STATE_ENDED) {


            //     Toast.makeText(context, "end of play", Toast.LENGTH_SHORT).show();
            playButton.setImageResource(R.drawable.play);
            isplaying = false;
            Constants.player.setPlayWhenReady(false);
            Constants.player.stop();
            allAudioListner.stoppressed(true);


        }

    }


    private void callapi(String page,String reciter) {
        if (page.equalsIgnoreCase("sura")) {
            mApiManager = new RestApiManager();
            //change
       //     String recitername = PreferenceManager.getManager().getReciter();
            QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
            Call<ResultObject<Audio>> call = service.getAudio(Constants.sChapterno, Constants.sVerseFrom, Constants.sVerseto, reciter, Constants.sAudioCategory);
            call.enqueue(new Callback<ResultObject<Audio>>() {
                @Override
                public void onResponse(Call<ResultObject<Audio>> call, Response<ResultObject<Audio>> response) {
                    allAudioListner.dataaudipassing(response.body().getData(), "sura",sAudioCategory);
                }

                @Override
                public void onFailure(Call<ResultObject<Audio>> call, Throwable t) {
                    Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    //         audioListener.fetchingaudiofailure();
                }
            });
        } else if (page.equalsIgnoreCase("juz")) {
            mApiManager = new RestApiManager();
           // String recitername = PreferenceManager.getManager().getReciter();
            QmtApi service = mApiManager.getRetrofitInstance().create(QmtApi.class);
            Call<ResultObject<Audio>> call = service.getjuzAudio(Constants.sChapterFrom, Constants.sChapterTo, Constants.sVerseFrom, Constants.sVerseto, reciter, Constants.sAudioCategory);
            call.enqueue(new Callback<ResultObject<Audio>>() {
                @Override
                public void onResponse(Call<ResultObject<Audio>> call, Response<ResultObject<Audio>> response) {

                    allAudioListner.dataaudipassing(response.body().getData(), "juz",sAudioCategory);
                }

                @Override
                public void onFailure(Call<ResultObject<Audio>> call, Throwable t) {
                    Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    /////////
    @Override
    public void onResume() {
        super.onResume();

        HomeActivity.fragment = AudioNewfragment.this;
    }

    public interface Autoscroll {
        void getVerseNo(String page, String chapterno, String verseno);

    }

    public List<Reciter> checkreciterpresent() {
        try {
            return new GetAllReciterActiveTask(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("aaa", "aaaa");
        } catch (ExecutionException e) {
            Log.d("aaa", "aaaa");
            e.printStackTrace();
        }

        return null;

    }
    public List<AudioCategoryReciterList> checkreciterAudioCategorypresent() {

        try {
            return new GetAllReciterAudioCategoryActiveTask(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
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