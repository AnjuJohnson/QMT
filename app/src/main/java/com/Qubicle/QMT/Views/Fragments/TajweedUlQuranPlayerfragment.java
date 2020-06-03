package com.Qubicle.QMT.Views.Fragments;

import android.app.Dialog;
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
import com.Qubicle.QMT.Controllers.TajweedUlQuranPlayerFragmentController;
import com.Qubicle.QMT.Listeners.AllAudioListner;
import com.Qubicle.QMT.Listeners.TajweedUlListner;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.TajweedUlQuranAudio;
import com.Qubicle.QMT.Models.ULQuranAudio;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;
import com.bumptech.glide.Glide;
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


public class TajweedUlQuranPlayerfragment extends BaseFragment implements ExoPlayer.EventListener, TajweedUlListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TextView subtopicTextview, tajweedContent, repeatcounttextview, meaning, chapternameTextView;
    String sort_order, min_sort_order, max_sort_order;
    String chaptermeaning;
    boolean stop = false;
    int flag = 0;
    Dialog mDialog;
    ImageView playButton;
    ImageView previmageView, stopimageView, nextimageView, repeatimageView,ulquranImage;
    int i = 0;
    List<ULQuranAudio> mAudioList = new ArrayList<>();
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
    TajweedUlQuranPlayerFragmentController tajweedClassesPlayerFragmentController;
    private Integer audiosize;
    private String defaultreciter;
    List<String> AudioContent = new ArrayList<>();
    List<String> AudioImageList = new ArrayList<>();


    public TajweedUlQuranPlayerfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.ul_qatar_player_fragment, container, false);
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
        ulquranImage = (ImageView) mFragmentView.findViewById(R.id.ulquranImage);
        repeatimageView = (ImageView) mFragmentView.findViewById(R.id.repeatimageView);
      //  subtopicTextview = (TextView) mFragmentView.findViewById(R.id.subtopicTextview);
        tajweedContent = (TextView) mFragmentView.findViewById(R.id.inner_secondary);
        repeatcounttextview = (TextView) mFragmentView.findViewById(R.id.repeatcounttextview);

        tajweedClassesPlayerFragmentController = new TajweedUlQuranPlayerFragmentController(TajweedUlQuranPlayerfragment.this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            chapterno = bundle.getString("CHAPTER_NO");

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


    }

    public void callapi() {
        if (isNetworkAvailable()) {
            progress();
            tajweedClassesPlayerFragmentController.startfetching(chapterno);
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
                    } else {
                        playButton.setImageResource(R.drawable.play);
                        isplaying = false;
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.stop();
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
if(AudioImageList.get(currentIndex)!=null){
    Glide.with(context).load(AudioImageList.get(currentIndex)).into(ulquranImage);
}




    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("TAJWEED UL QURAN AUDIO");
        OnMenuIconChange.iconchange(TajweedUlQuranPlayerfragment.this);
        HomeActivity.fragment = TajweedUlQuranPlayerfragment.this;
    }


    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {
        if (mDialog == null) {
            mDialog = Utility.showProgressBar(getActivity());
            mDialog.show();
        }
    }

    @Override
    public void datapassing(List<ChapterList> chapterLists) {

    }

    @Override
    public void passaudioparameter(String chapterno) {

    }


    @Override
    public void dataTajweedClassesAudio(List<TajweedUlQuranAudio> audio) {
        List<ULQuranAudio> tajweedAudios = new ArrayList<>();
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (audio != null && audio.size() != 0) {


            playaudio(audio.get(0).getAudio());

        } else {
            if (Constants.player != null) {
                Constants.player.stop();
                Constants.player.setPlayWhenReady(false);


            }
            stop = true;
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void playaudio(List<ULQuranAudio> tajweedAudios) {
        AudioContent=new ArrayList<>();
        AudioImageList=new ArrayList<>();

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

                AudioContent.add(tajweedAudios.get(i).getTranslation());
                AudioImageList.add(tajweedAudios.get(i).getImage_title());

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




}