package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.AmaniPlayerFragmentController;
import com.Qubicle.QMT.Controllers.TajweedClassesPlayerFragmentController;
import com.Qubicle.QMT.Listeners.AllAudioListner;
import com.Qubicle.QMT.Listeners.AmaniAudioListner;
import com.Qubicle.QMT.Listeners.TajweedListner;
import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Models.TajweedAudio;
import com.Qubicle.QMT.Models.TajweedClassAudio;
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


public class TajweedClassesPlayerfragment extends BaseFragment implements ExoPlayer.EventListener, TajweedListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TextView subtopicTextview, tajweedContent, repeatcounttextview, meaning, chapternameTextView;
    String sort_order, min_sort_order, max_sort_order;
    String chaptermeaning;
    boolean stop = false;
    int flag = 0;
    ImageView playButton;
    ImageView previmageView, stopimageView, nextimageView, repeatimageView;
    int i = 0;
    List<TajweedAudio> mAudioList = new ArrayList<>();
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
    TajweedClassesPlayerFragmentController tajweedClassesPlayerFragmentController;
    String[] mnewAudioList = {"http://qubicle.co.in/qmt/assets/audio/001001.mp3", "http://qubicle.co.in/qmt/assets/audio/001002.mp3",
            "http://qubicle.co.in/qmt/assets/audio/001003.mp3", "http://qubicle.co.in/qmt/assets/audio/001004.mp3"};
    private Integer audiosize;
    private String defaultreciter;
    List<String> AudioContent = new ArrayList<>();


    public TajweedClassesPlayerfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.tajweedclasses_player_fragment, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        allAudioListner = (AllAudioListner) getActivity();

        progress = (SeekBar) mFragmentView.findViewById(R.id.progress);
        playButton = (ImageView) mFragmentView.findViewById(R.id.playbutton);
        previmageView = (ImageView) mFragmentView.findViewById(R.id.previmageView);
        stopimageView = (ImageView) mFragmentView.findViewById(R.id.stopimageView);
        nextimageView = (ImageView) mFragmentView.findViewById(R.id.nextimageView);
        repeatimageView = (ImageView) mFragmentView.findViewById(R.id.repeatimageView);
        subtopicTextview = (TextView) mFragmentView.findViewById(R.id.subtopicTextview);
        tajweedContent = (TextView) mFragmentView.findViewById(R.id.tajweedContent);
        repeatcounttextview = (TextView) mFragmentView.findViewById(R.id.repeatcounttextview);

        tajweedClassesPlayerFragmentController = new TajweedClassesPlayerFragmentController(TajweedClassesPlayerfragment.this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            sort_order = bundle.getString("SORTORDER");
            min_sort_order = bundle.getString("MIN_SORTORDER");
            max_sort_order = bundle.getString("MAX_SORTORDER");
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

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });


        // playaudio();
    }

    public void callapi() {
        if (isNetworkAvailable()) {
            tajweedClassesPlayerFragmentController.startfetching(sort_order);
        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void playaudiobuttons() {
        repeatimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(getActivity(), view);

                for (int p = 0; p < 6; p++) {
                    menu.getMenu().add(Menu.NONE, p, p, String.valueOf(p));
                }
                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        repeatcounttextview.setText(item.getTitle());
                        repeat = (String) item.getTitle();

                        playaudio(mAudioList);


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

        previmageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.player != null) {

                    if (currentIndex > 0) {
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.seekTo(currentIndex - 1, 0);
                        Constants.player.setPlayWhenReady(true);
                    }
                    //call prev title according to sort order

                    else if(Integer.parseInt(sort_order)>Integer.parseInt(min_sort_order)){
                        int sortorderint = Integer.parseInt(sort_order);
                        sortorderint--;
                        sort_order = String.valueOf(sortorderint);
                        callapi();
                    }
                    else {

                        playButton.setImageResource(R.drawable.play);
                        isplaying = false;
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.stop();
                        progress.setProgress(0);
                         Toast.makeText(context, "end of play", Toast.LENGTH_SHORT).show();
                    }

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

                    if (currentIndex < (mAudioList.size() * repeatno) - 1) {
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.seekTo(currentIndex + 1, 0);
                        Constants.player.setPlayWhenReady(true);
                    }
                    //call next title according to sort order
                    else if (Integer.parseInt(sort_order) < Integer.parseInt(max_sort_order)) {
                        int sortorderint = Integer.parseInt(sort_order);
                        sortorderint++;
                        sort_order = String.valueOf(sortorderint);
                        callapi();
                    } //stop if end of playlist
                    else {
                        playButton.setImageResource(R.drawable.play);
                        isplaying = false;
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.stop();
                        progress.setProgress(0);
                        Toast.makeText(context, "end of play", Toast.LENGTH_SHORT).show();


                    }

                }
            }
        });
        stopimageView.setOnClickListener(new View.OnClickListener() {
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
        });
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if (playbackState == Player.STATE_ENDED) {

            stop = true;
            playButton.setImageResource(R.drawable.play);
            isplaying = false;
            Constants.player.setPlayWhenReady(false);
            Constants.player.stop();
          /*  repeat = "0";
            repeatcounttextview.setText("0");*/

        }


    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        currentIndex = Constants.player.getCurrentWindowIndex();
        //change content
        tajweedContent.setText(AudioContent.get(currentIndex));

    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("TAJWEED CLASSES AUDIO");
        OnMenuIconChange.iconchange(TajweedClassesPlayerfragment.this);
        HomeActivity.fragment = TajweedClassesPlayerfragment.this;
    }


    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {

    }

    @Override
    public void fetchingsuccess(List<Tajweed> TajweedTitles) {

    }

    @Override
    public void passaudioparameter(String title, String minSortOrder, String maxSortOrder) {

    }


    @Override
    public void dataTajweedClassesAudio(List<TajweedClassAudio> audio) {

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
                    subtopicTextview.setText(audio.get(r).getTopic());

                    playaudio(audio.get(r).getAudio());
                    flag = 1;
                } else {
                    flag = 0;
                }

            }
            if (flag == 0) {
                subtopicTextview.setText(audio.get(0).getTopic());
                playaudio(audio.get(0).getAudio());
            }

        } else {
            if (Constants.player != null) {
                Constants.player.stop();
                Constants.player.setPlayWhenReady(false);


            }
            stop = true;

            progress.setProgress(0);
            Constants.CHPATER_RECITER_LIST = null;
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void playaudio(List<TajweedAudio> tajweedAudios) {
        AudioContent=new ArrayList<>();
        mediaSources = new ArrayList<>();
        stop = false;
        mAudioList = tajweedAudios;
        int repeatsingle = Integer.parseInt(repeat);
        for (int r = 0; r <= repeatsingle; r++) {
            for (int i = 0; i < tajweedAudios.size(); i++) {

                MediaSource source = new ExtractorMediaSource(Uri.parse(tajweedAudios.get(i).getAudio_title().replaceAll(" ", "%20")),
                        dataSourceFactory,
                        extractorsFactory,
                        mainHandler,
                        null);
                mediaSources.add(source);

                //insert audio content

                AudioContent.add(tajweedAudios.get(i).getContent());

            }
        }

        mediaSource1 = new ConcatenatingMediaSource(mediaSources.toArray(new MediaSource[mediaSources.size()]));
        Constants.player.prepare(mediaSource1);
        playButton.setImageResource(R.drawable.pause);
        isplaying = true;
        Constants.player.setPlayWhenReady(true);


//progreebar change
        Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                progress.setMax((int) Constants.player.getDuration() / 1000);
                progress.setProgress((int) (Constants.player.getCurrentPosition() / 1000));

                if (!stop) {
                    handler.postDelayed(runnable, 1000);
                } else {

                    progress.setProgress(0);
                    playButton.setImageResource(R.drawable.play);
                    isplaying = false;
                    Constants.player.setPlayWhenReady(false);
                    Constants.player.stop();
                }


            }
        };
        handler.postDelayed(runnable, 0);

///////////
        playaudiobuttons();
    }

   /* public void recitercalling(View view) {
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
    }*/


}