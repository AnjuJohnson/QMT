package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.AmaniPlayerFragmentController;
import com.Qubicle.QMT.Listeners.AllAudioListner;
import com.Qubicle.QMT.Listeners.AmaniAudioListner;
import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AutoscrollVerseListModel;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
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

import java.util.ArrayList;
import java.util.List;


public class AmaniPlayerfragment extends BaseFragment implements ExoPlayer.EventListener, AmaniAudioListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TextView mSendTextView, time,chapternametitile,meaning,chapternameTextView;
    String chaptername;
    String chaptermeaning;
    boolean stop = false;
    int flag = 0;
    ImageView playButton;
    ImageView previmageView, stopimageView, nextimageView;
    int i = 0;
    List<Audio> mAudioList = new ArrayList<>();
    private MediaPlayer mediaplayer;

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

    AllAudioListner allAudioListner;
    MediaSource mediaSource1;
    List<MediaSource> mediaSources = new ArrayList<>();
    private int currentIndex = 0;
    String repeat = "0";
    Boolean durationSet;
    Runnable runnable;
    SeekBar progress;
    String page, chapterno, verseno;
    AmaniPlayerFragmentController amaniPlayerFragmentController;
    String[] mnewAudioList = {"http://qubicle.co.in/qmt/assets/audio/001001.mp3", "http://qubicle.co.in/qmt/assets/audio/001002.mp3",
            "http://qubicle.co.in/qmt/assets/audio/001003.mp3", "http://qubicle.co.in/qmt/assets/audio/001004.mp3"};
    private Integer audiosize;
    private String defaultreciter;

    public AmaniPlayerfragment(Context context, String chaptername, String chaptermeaning) {
        this.context = context;
        this.chaptername = chaptername;
        this.chaptermeaning = chaptermeaning;

    }

    public AmaniPlayerfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.amani_player_fragment, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        allAudioListner = (AllAudioListner) getActivity();
        time = (TextView) mFragmentView.findViewById(R.id.time);
        progress = (SeekBar) mFragmentView.findViewById(R.id.progress);
        playButton = (ImageView) mFragmentView.findViewById(R.id.playbutton);
        previmageView = (ImageView) mFragmentView.findViewById(R.id.previmageView);
        //    stopimageView = (ImageView) mFragmentView.findViewById(R.id.stopimageView);
        nextimageView = (ImageView) mFragmentView.findViewById(R.id.nextimageView);
        chapternametitile = (TextView) mFragmentView.findViewById(R.id.chapternametitile);
        meaning = (TextView) mFragmentView.findViewById(R.id.meaning);
        chapternameTextView = (TextView) mFragmentView.findViewById(R.id.chapternameTextView);
        amaniPlayerFragmentController = new AmaniPlayerFragmentController(AmaniPlayerfragment.this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            chaptername = bundle.getString("CHAPTERNAME");
            page = bundle.getString("PAGE");
            chapterno = bundle.getString("CHAPTERNO");
            verseno = bundle.getString("VERSENO");
            context = getActivity();
        }
        initValues();

    }


    public void initValues() {
        //fetch reciterlist and audio
        callapi();

        defaultreciter = PreferenceManager.getManager().getReciter();
        renderersFactory = new DefaultRenderersFactory(context);
        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        loadControl = new DefaultLoadControl();
        if (Constants.player != null) {
            Constants.player.stop();
            Constants.player.setPlayWhenReady(false);
            Constants.player.release();
            allAudioListner.stoppressed(true);
        }

        Constants.player = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector, loadControl);
        Constants.player.addListener(this);

        dataSourceFactory = new DefaultDataSourceFactory(context, "ExoplayerDemo");
        extractorsFactory = new DefaultExtractorsFactory();
        mainHandler = new Handler();

        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {


                progress.setMax((int) Constants.player.getDuration() / 1000);

                if (b) {
                    if (progresss * 1000 < Constants.player.getDuration()) {
                        Constants.player.seekTo(progresss * 1000);
                    }
                    System.out.println("progress" + progresss);
                    // System.out.println("progress"+Constants.player.getDuration());

                    // Constants.player.seekTo(progress*1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });


/*

//////////

        for (int i = 0; i < mnewAudioList.length; i++) {
            //repeat all off
            MediaSource source = new ExtractorMediaSource(Uri.parse(mnewAudioList[i].replaceAll(" ", "%20")),
                    dataSourceFactory,
                    extractorsFactory,
                    mainHandler,
                    null);
            mediaSources.add(source);
        }


        mediaSource1 = new ConcatenatingMediaSource(mediaSources.toArray(new MediaSource[mediaSources.size()]));
        Constants.player.prepare(mediaSource1);
        isplaying = true;
        playButton.setImageResource(R.drawable.pause);
        Constants.player.setPlayWhenReady(true);


   //////////

*/


        // playaudio();
    }

    public void callapi() {
        if (isNetworkAvailable()) {
            amaniPlayerFragmentController.startfetching(chapterno, page, verseno);
        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void playaudiobuttons() {
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

        previmageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.player != null) {
                    int chapternoo = Integer.valueOf(chapterno);
                    if (chapternoo > 1) {
                        chapternoo = chapternoo - 1;
                        chapterno = String.valueOf(chapternoo);

                        getchapterdetail(chapterno);
                        callapi();
                    } else {
                        playButton.setImageResource(R.drawable.play);
                        isplaying = false;
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.stop();
                    }


                }
            }
        });
        nextimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.player != null) {

                    int chapternoo = Integer.valueOf(chapterno);
                    if (audiosize != null)
                        if (chapternoo < 114) {
                            chapternoo = chapternoo + 1;
                            chapterno = String.valueOf(chapternoo);
                            //take mchaptername and meaning from db
                            getchapterdetail(chapterno);
                            callapi();
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
        /*stopimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.player != null) {

                    playButton.setImageResource(R.drawable.play);
                    isplaying = false;
                    Constants.player.setPlayWhenReady(false);
                    Constants.player.stop();
                    //  allAudioListner.stoppressed(true);

                }
            }
        });*/
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if (playbackState == Player.STATE_ENDED) {

            stop = true;
            playButton.setImageResource(R.drawable.play);
            isplaying = false;
            Constants.player.setPlayWhenReady(false);
            Constants.player.stop();


        }


    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        currentIndex = Constants.player.getCurrentWindowIndex();
        //call interface method to autoscroll

    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle(page);
        OnMenuIconChange.iconchange(AmaniPlayerfragment.this);
        HomeActivity.fragment = AmaniPlayerfragment.this;
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long ms = seconds / 1000;
        long s = ms % 60;
        long m = (ms / 60) % 60;
        long h = (ms / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {

    }

    @Override
    public void dataAmaniAudio(List<AmaniAudio> audio) {

        getchapterdetail(chapterno);
        if (audio != null && audio.size() != 0) {
            audiosize = audio.size();
            flag = 0;
            Constants.CHPATER_RECITER_LIST = new String[audio.size()];
            for (int i = 0; i < audio.size(); i++) {
                Constants.CHPATER_RECITER_LIST[i] = audio.get(i).getReciter_name();
            }
//check default selected reciter present in the datalist


            for (int r = 0; r < Constants.CHPATER_RECITER_LIST.length; r++) {

                if (defaultreciter.equalsIgnoreCase(Constants.CHPATER_RECITER_LIST[r])) {
                    playaudio(audio.get(r).getAudio_list().get(0).getAudio_title().trim());
                    flag = 1;
                } else {
                    flag = 0;
                }

            }
            if (flag == 0) {
                playaudio(audio.get(0).getAudio_list().get(0).getAudio_title().trim());
            }

        } else {
            if (Constants.player != null) {
                Constants.player.stop();
                Constants.player.setPlayWhenReady(false);


            }
            stop = true;
            time.setText("0:00:00");
            progress.setProgress(0);
            Constants.CHPATER_RECITER_LIST = null;
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void playaudio(String audioList) {

        getchapterdetail(chapterno);
        stop = false;
        MediaSource source = new ExtractorMediaSource(Uri.parse(audioList),
                dataSourceFactory,
                extractorsFactory,
                mainHandler,
                null);

        Constants.player.prepare(source);
        playButton.setImageResource(R.drawable.pause);
        isplaying = true;
        Constants.player.setPlayWhenReady(true);


        Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                progress.setMax((int) Constants.player.getDuration() / 1000);
                progress.setProgress((int) (Constants.player.getCurrentPosition() / 1000));
                time.setText(convertSecondsToHMmSs(Constants.player.getCurrentPosition()));

                if (!stop) {
                    handler.postDelayed(runnable, 1000);
                } else {
                    time.setText("0:00:00");
                    progress.setProgress(0);
                    playButton.setImageResource(R.drawable.play);
                    isplaying = false;
                    Constants.player.setPlayWhenReady(false);
                    Constants.player.stop();
                }


            }
        };
        handler.postDelayed(runnable, 0);


        playaudiobuttons();
    }

    public void recitercalling(View view) {
//show popupmenu
        PopupMenu menu = new PopupMenu(getActivity(), view);
        if (Constants.CHPATER_RECITER_LIST != null) {

            for (int p = 0; p < Constants.CHPATER_RECITER_LIST.length; p++) {
                menu.getMenu().add(Menu.NONE, p, p, Constants.CHPATER_RECITER_LIST[p]);
            }
            menu.show();
        }


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                defaultreciter = item.getTitle().toString();
                callapi();
                return true;
            }

        });
    }


    private void getchapterdetail(String chapternoo){
        List<ChapterList> verseDetailList = amaniPlayerFragmentController.checkchapterdetail(chapternoo);
        if (verseDetailList == null || verseDetailList.isEmpty()) {

        } else {
            chapternametitile.setText(verseDetailList.get(0).getChapter_name());
            meaning.setText(verseDetailList.get(0).getChapter_name_meaning());
            chapternameTextView.setText(verseDetailList.get(0).getChapter_name());
            //  datapassingVerseDetail(verseDetailList);
        }

    }
}