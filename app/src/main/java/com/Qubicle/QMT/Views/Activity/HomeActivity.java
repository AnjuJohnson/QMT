package com.Qubicle.QMT.Views.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Qubicle.QMT.Base.BaseActivity;
import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Listeners.AllAudioListner;
import com.Qubicle.QMT.Listeners.BookmarkListner;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Fragments.AmaniPlayerfragment;
import com.Qubicle.QMT.Views.Fragments.AudioNewfragment;
import com.Qubicle.QMT.Views.Fragments.DashBoardFragment;
import com.Qubicle.QMT.Views.Fragments.Favouritesfragment;
import com.Qubicle.QMT.Views.Fragments.Feedbackfragment;
import com.Qubicle.QMT.Views.Fragments.JuzDetailfragment;
import com.Qubicle.QMT.Views.Fragments.JuzFragment;
import com.Qubicle.QMT.Views.Fragments.Memorisationtestfragment;
import com.Qubicle.QMT.Views.Fragments.Notesfragment;
import com.Qubicle.QMT.Views.Fragments.Practisefragment;
import com.Qubicle.QMT.Views.Fragments.QuestionAnswerfragment;
import com.Qubicle.QMT.Views.Fragments.QuranIndexfragment;
import com.Qubicle.QMT.Views.Fragments.Readingfragment;
import com.Qubicle.QMT.Views.Fragments.Searchfragment;
import com.Qubicle.QMT.Views.Fragments.SettingsFragment;
import com.Qubicle.QMT.Views.Fragments.TajweedAudiofragment;
import com.Qubicle.QMT.Views.Fragments.VerseDetailfragment;
import com.Qubicle.QMT.utils.Constants;
import com.Qubicle.QMT.utils.Utility;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

/*
Created by Anju on 21-08-2019.
 
*/
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        Utility.menuIconChange, Utility.OnFragmnetSwitch, AllAudioListner, AudioNewfragment.Autoscroll, BookmarkListner {
    Toolbar toolbar;
    DrawerLayout drawer;
    public static BaseFragment fragment;
    private View navHeader;
    private static final String TAG_HOME = "HomeActivity";
    public static String CURRENT_TAG = TAG_HOME;
    private TextView mNameTextView, mTitleTextView;
    FragmentManager mFragmentManager;
    ImageView mMenuIcon, searchImageView;
    FrameLayout frameplayer;
    AudioNewfragment.Autoscroll autoscroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setuoview();

        if (savedInstanceState == null) {
            loadhomefragment();
        }

        // Change the title back when the fragment is changed
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = getFragment();
                iconchange(fragment);
            }
        });
    }
    private void setTitleFromFragment(Fragment fragmentt) {
        // Set the activity title
        Log.d("homefragment", fragmentt.getClass().getSimpleName());

    }
    public void setuoview() {
        frameplayer = findViewById(R.id.frameplayer);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mTitleTextView = findViewById(R.id.mTitleTextView);
        mMenuIcon = findViewById(R.id.menu_icon);
        searchImageView = findViewById(R.id.searchImageView);
        mMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


    }
    private Fragment getFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        return fragment;
    }
    private void loadhomefragment() {
        CURRENT_TAG = "";

        //   ChangeFragment(new Readingfragment(HomeActivity.this, "surapage"), CURRENT_TAG, true);
        ChangeFragment(new DashBoardFragment(HomeActivity.this), CURRENT_TAG, true);

        drawer.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        BaseFragment baseFragment = null;
        int id = menuItem.getItemId();

        if (id == R.id.mSettings) {

            CURRENT_TAG = "SETTINGS";
            ChangeFragment(new SettingsFragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (id == R.id.mFeedback) {
            CURRENT_TAG = "FEEDBACK";
            ChangeFragment(new Feedbackfragment(HomeActivity.this), CURRENT_TAG, true);
        } else if (id == R.id.mNotes) {
            CURRENT_TAG = "NOTES";
            ChangeFragment(new Notesfragment(HomeActivity.this, "", "", "", "dash"), CURRENT_TAG, true);
        }
        else if (id == R.id.mFav) {
            CURRENT_TAG = "Favourites";
            ChangeFragment(new Favouritesfragment(HomeActivity.this), CURRENT_TAG, true);
        }

        /*else if (id == R.id.mqa) {
           *//* CURRENT_TAG = "QUESTION AND ANSWER";
            ChangeFragment(new QuestionAnswerfragment(HomeActivity.this), CURRENT_TAG, true);*//*
        }*/
        else if (id == R.id.mquranindex) {
            //drawer.closeDrawer(GravityCompat.START);
           /* Handler handler = new Handler();

            Runnable runnable = new Runnable() {

                @Override
                public void run() {


                }
            };

            handler.postDelayed(runnable, 500);


*/
            CURRENT_TAG = "QURAN INDEX";
            ChangeFragment(new QuranIndexfragment(HomeActivity.this), CURRENT_TAG, true);

        }

        else if (id == R.id.mShare) {
           /* String shareBody = "Here is the share content body";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "share via"));*/
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentChange(BaseFragment fragment, String title, boolean isReplace) {
        ChangeFragment(fragment, title, isReplace);
        CURRENT_TAG = title;
    }

    @Override
    public void loadTitle(String Title) {
        mTitleTextView.setText(Title);
    }

    private void ChangeFragment(BaseFragment fragment, String title, boolean isReplace) {
        mTitleTextView.setText(title);
        this.fragment = fragment;
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
        /*fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);*/
        if (isReplace) {
            fragmentTransaction.add(R.id.frame, fragment, CURRENT_TAG);
        } else {
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


/*    private void popstackreadingFragment(BaseFragment fragment, String title, boolean isReplace) {


        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
        *//*fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);*//*
        if (isReplace) {
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        } else {
            fragmentTransaction.add(R.id.frame, fragment, CURRENT_TAG);
        }
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
    */

    @Override
    public void iconchange(Fragment fragment) {
    //  this.  fragment=fragment;
        if (fragment.getClass().getSimpleName().equalsIgnoreCase("Readingfragment")) {
            CURRENT_TAG = "READING";
            loadTitle(CURRENT_TAG);
            searchImageView.setVisibility(View.VISIBLE);
            Constants.SEARCH_ON=false;
            searchImageView.setImageResource(R.drawable.search);
            mMenuIcon.setImageResource(R.drawable.back);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeFragment(new DashBoardFragment(HomeActivity.this), "", true);
                }
            });

            searchImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Constants.SEARCH_ON){
                        searchImageView.setImageResource(R.drawable.search);
                        CURRENT_TAG = "READING";
                        loadTitle(CURRENT_TAG);
                        ChangeFragment(new Readingfragment(HomeActivity.this), CURRENT_TAG, true);

                        Constants.SEARCH_ON=false;
                    }
                    else {
                        searchImageView.setImageResource(R.drawable.closesearch);
                        CURRENT_TAG = "SEARCH";
                        loadTitle(CURRENT_TAG);
                        ChangeFragment(new Searchfragment(HomeActivity.this), CURRENT_TAG, true);
                        Constants.SEARCH_ON=true;
                    }
                }
            });

        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DashBoardFragment")) {

            HomeActivity.fragment= new DashBoardFragment();
            searchImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.menu);
            loadTitle("");
         //   this.fragment = fragment;
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // updateHeader();
                    //   loadNavHeader();
                    drawer.openDrawer(GravityCompat.START);
                }
            });
        }
        if (fragment.getClass().getSimpleName().equalsIgnoreCase("Bookmarksfragment")) {
            loadTitle("BOOKMARKS");
            searchImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.back);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        }  if (fragment.getClass().getSimpleName().equalsIgnoreCase("Favouritesfragment")) {
            loadTitle("FAVOURITES");
            searchImageView.setVisibility(View.GONE);
            mMenuIcon.setImageResource(R.drawable.back);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStackImmediate();
                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ChapterExplanationFragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            CURRENT_TAG = "CHAPTER EXPLANATION";
            loadTitle(CURRENT_TAG);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mFragmentManager.popBackStack();                }
            });

        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("SettingsFragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("SETTINGS");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("AmaniAudioFragment")) {

            loadTitle("AMANI TAFSEER");
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("ReciterChapterfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("RECITATION");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("VerseDetailfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("CHAPTER");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();


                }
            });
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Searchfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            loadTitle("READING");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CURRENT_TAG = "READING";
                    ChangeFragment(new Readingfragment(HomeActivity.this), CURRENT_TAG, true);
                }
            });
        }


        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("JuzDetailfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("READING");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CURRENT_TAG = "READING";
                       mFragmentManager.popBackStack();
                 //   ChangeFragment(new Readingfragment(HomeActivity.this), CURRENT_TAG, true);
                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("VerseExplanationFragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("VERSE EXPLANATION");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();


                }
            });
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MemorizationMainfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("MEMORIZATION");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();


                }
            });
        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("VyakyanakuripFragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("VYAKYANAKURIPPU");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();

                }
            });
        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Feedbackfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("FEEDBACK");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mFragmentManager.popBackStack();

                }
            });
        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("QuestionAnswerfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("QUESTION AND ANSWER");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();

                }
            });
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Memorisationtestfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.VISIBLE);
            searchImageView.setImageResource(R.drawable.filter);

            loadTitle("MEMORIZATION");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Constants.INDEX_PAGE="main";
                    mFragmentManager.popBackStack();

                }
            });
            searchImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //show filter dialog
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                    System.out.println("currentFragment" + currentFragment.getClass().getSimpleName());
                    if (currentFragment.getClass().getSimpleName().equalsIgnoreCase("Memorisationtestfragment")) {
                        Memorisationtestfragment fragment = (Memorisationtestfragment) getSupportFragmentManager().findFragmentById(R.id.frame);
                        fragment.showfilterdialog();
                    }


                }
            });



        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("QuranIndexfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("QURAN INDEX");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Constants.INDEX_PAGE="main";
                    mFragmentManager.popBackStack();

                }
            });
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Practisefragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setImageResource(R.drawable.changeperson);
            searchImageView.setVisibility(View.VISIBLE);
            loadTitle("PRACTICE");

            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.player != null) {
                        Constants.player.stop();
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.release();

                    }
                    mFragmentManager.popBackStack();

                }
            });

            searchImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                    System.out.println("currentFragment" + currentFragment.getClass().getSimpleName());
                    if (currentFragment.getClass().getSimpleName().equalsIgnoreCase("Practisefragment")) {
                        Practisefragment fragment = (Practisefragment) getSupportFragmentManager().findFragmentById(R.id.frame);
                        fragment.recitercalling(view);
                    }


                }
            });
        }


        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("AmaniPlayerfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setImageResource(R.drawable.changeperson);
            searchImageView.setVisibility(View.VISIBLE);
            loadTitle("AMANI TAFSEER");

            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.player != null) {
                        Constants.player.stop();
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.release();

                    }
                    mFragmentManager.popBackStack();

                }
            });

            searchImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                    System.out.println("currentFragment" + currentFragment.getClass().getSimpleName());
                    if (currentFragment.getClass().getSimpleName().equalsIgnoreCase("AmaniPlayerfragment")) {
                        AmaniPlayerfragment fragment = (AmaniPlayerfragment) getSupportFragmentManager().findFragmentById(R.id.frame);
                        fragment.recitercalling(view);
                    }


                }
            });
        }


        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TajweedUlQuranPlayerfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);

            searchImageView.setVisibility(View.GONE);
            loadTitle("TAJWEED UL QURAN AUDIO");

            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.player != null) {
                        Constants.player.stop();
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.release();

                    }
                    mFragmentManager.popBackStack();

                }
            });

        }


        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TajweedClassesTopicsPlayerNewfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);

            searchImageView.setVisibility(View.GONE);
            loadTitle("TAJWEED CLASSES AUDIO");

            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.player != null) {
                        Constants.player.stop();
                        Constants.player.setPlayWhenReady(false);
                        Constants.player.release();

                    }
                    mFragmentManager.popBackStack();

                }
            });

        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("AudioMainfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("AUDIO");

            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Constants.INDEX_PAGE="main";
                    mFragmentManager.popBackStack();

                }
            });
        }


        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Tajweedfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("TAJWEED");

            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Constants.INDEX_PAGE="main";
                    mFragmentManager.popBackStack();

                }
            });
        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TajweedClassesTitlesfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("TAJWEED CLASSES");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Constants.INDEX_PAGE="main";
                    mFragmentManager.popBackStack();

                }
            });
        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("CreateNotefragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);

            loadTitle("NOTE VIEW");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Notesfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            loadTitle("NOTES");
            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("MainttopicsIndexVerseListfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);

            if(Constants.INDEX_PAGE.equalsIgnoreCase("main")){
                loadTitle("TOPICS");
            }
            else if(Constants.INDEX_PAGE.equalsIgnoreCase("all")){

                if(Constants.ALLCHAPTERPAGE.equalsIgnoreCase("index")){
                    loadTitle("ALL CHAPTERS");
                }
                else if(Constants.ALLCHAPTERPAGE.equalsIgnoreCase("sura")){
                    loadTitle("ഉള്ളടക്കം");
                }
            }


            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        }

        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("AlltopicsIndexfragment")) {
            mMenuIcon.setImageResource(R.drawable.back);
            searchImageView.setVisibility(View.GONE);
            if(Constants.ALLCHAPTERPAGE.equalsIgnoreCase("index")){
                loadTitle("ALL CHAPTERS");
            }
            else if(Constants.ALLCHAPTERPAGE.equalsIgnoreCase("sura")){
                loadTitle("ഉള്ളടക്കം");
            }

            mMenuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentManager.popBackStack();
                }
            });
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //  finish();
    }

    @Override
    public void dataaudipassing(List<Audio> audio,String page,String audiocategory) {
        if(audio.size()==0){
            Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
        }
        else {
            frameplayer.setVisibility(View.VISIBLE);
            ShowAudioFragment(audio,page,audiocategory);
        }

    }

    @Override
    public void juzdataaudipassing(List<Audio> audio,String page,String audiocategory) {

        if(audio.size()==0){
            Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
        }
        else {
            frameplayer.setVisibility(View.VISIBLE);
            ShowAudioFragment(audio,page,audiocategory);
        }


    }

    @Override
    public void stoppressed(Boolean stop) {
        if (stop) {
            calldatasetchanged();
            frameplayer.setVisibility(View.GONE);
        }
    }


    private void calldatasetchanged() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        System.out.println("currentFragment" + currentFragment.getClass().getSimpleName());
        if (currentFragment.getClass().getSimpleName().equalsIgnoreCase("VerseDetailfragment")) {
            VerseDetailfragment fragment = (VerseDetailfragment) getSupportFragmentManager().findFragmentById(R.id.frame);
            fragment.datasetchanged();
        } else if (currentFragment.getClass().getSimpleName().equalsIgnoreCase("JuzDetailfragment")) {
            JuzDetailfragment fragment = (JuzDetailfragment) getSupportFragmentManager().findFragmentById(R.id.frame);

            fragment.datasetchanged();
        }

    }


    private void ShowAudioFragment(List<Audio> audio,String page,String audiocategory) {
        Fragment childFragment = new AudioNewfragment(audio, HomeActivity.this,page,audiocategory);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameplayer, childFragment).commit();
    }

    @Override
    public void onBackPressed() {

        System.out.println("backfragment" + fragment.getClass().getSimpleName());
        if (fragment.getClass().getSimpleName().equalsIgnoreCase("Readingfragment")) {

            ChangeFragment(new DashBoardFragment(HomeActivity.this), "", true);
        } else if (fragment.getClass().getSimpleName().equalsIgnoreCase("DashBoardFragment")) {
            exitdialog();
        }


        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("Searchfragment")) {
            ChangeFragment(new Readingfragment(HomeActivity.this), CURRENT_TAG, true);
        }


        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("AmaniPlayerfragment")) {
            if (Constants.player != null) {
                Constants.player.stop();
                Constants.player.setPlayWhenReady(false);
                Constants.player.release();

            }
            mFragmentManager.popBackStack();
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TajweedClassesTopicsPlayerNewfragment")) {
            if (Constants.player != null) {
                Constants.player.stop();
                Constants.player.setPlayWhenReady(false);
                Constants.player.release();

            }
            mFragmentManager.popBackStack();
        }
        else if (fragment.getClass().getSimpleName().equalsIgnoreCase("TajweedUlQuranPlayerfragment")) {
            if (Constants.player != null) {
                Constants.player.stop();
                Constants.player.setPlayWhenReady(false);
                Constants.player.release();

            }
            mFragmentManager.popBackStack();
        }
        else {
            mFragmentManager.popBackStack();
        }






    }

    private void exitdialog() {
        new AlertDialog.Builder(this)
                .setTitle("Close App?")
                .setMessage("Do you really want to close this app?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (Constants.player != null) {

                                    Constants.player.setPlayWhenReady(false);
                                    Constants.player.stop();

                                }

                                finishAffinity();
                                finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    @Override
    public void getVerseNo(String page, String chapterno, String verseno) {
        if (Constants.player != null) {
            frameplayer.setVisibility(View.VISIBLE);
        } else {
            frameplayer.setVisibility(View.GONE);
        }


        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        System.out.println("currentFragment" + currentFragment.getClass().getSimpleName());


        if (currentFragment.getClass().getSimpleName().equalsIgnoreCase("VerseDetailfragment")) {
            VerseDetailfragment fragment = (VerseDetailfragment) getSupportFragmentManager().findFragmentById(R.id.frame);
            frameplayer.setVisibility(View.VISIBLE);

            fragment.getVerseNo("", chapterno, verseno);
        } else if (currentFragment.getClass().getSimpleName().equalsIgnoreCase("JuzDetailfragment")) {
            JuzDetailfragment fragment = (JuzDetailfragment) getSupportFragmentManager().findFragmentById(R.id.frame);
            frameplayer.setVisibility(View.VISIBLE);
            fragment.getVerseNo(chapterno, verseno);
        }


    }

    @Override
    public void callversedetails(String page, String chapterno, String verseno, String juzno) {

    }

    @Override
    public void passVerseDetails(String page, String chaptername, String chapterno, String verseno, String juzno) {

    }

    @Override
    public void sharestring(String sharestring) {

    }

    @Override
    public void passdetailsForNotes(String chaptername, String chapterno, String verseno) {

    }

    @Override
    public void shownotes(String chaptername, String chapterno, String verseno) {

    }

    @Override
    public void popupdetailpage() {
        mFragmentManager.popBackStack();
    }

    @Override
    public void deletenote(int noteid, int position) {

    }
}