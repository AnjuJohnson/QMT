package com.Qubicle.QMT.Views.Fragments;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.MemorizationFragmentController;
import com.Qubicle.QMT.Listeners.MemorizationTestListner;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Practice;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.Preference.PreferenceManager;
import com.Qubicle.QMT.utils.Utility;

import java.util.ArrayList;
import java.util.List;


public class Memorisationtestfragment extends BaseFragment implements MemorizationTestListner {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
    TextView tvDescription, chapternameTextView, next, previous;
    TextView showmore, versefromtextview, versetotextview, mChapternoTextView, mMeaningMalayalamTextView;
    boolean expand = false;
    RelativeLayout chapterlayout, fromlayout, tolayout;
    MemorizationFragmentController memorizationFragmentController;
    String[] chapterlist;
    List<ChapterList> chapterFullList = new ArrayList<>();
    private String[] verselist;
    String mTranslation, mChapterno, mVerseFrom, mVerseTo;
    List<Verse> VerseList = new ArrayList<>();
    LinearLayout versedetaillayout, versemeaninglayout;
    int max = 0, min = 0, current;
    RadioButton filterradiobutton;
    String filtertext = "Show Both";

    public Memorisationtestfragment(Context context) {
        this.context = context;

    }

    public Memorisationtestfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.memorisation_fragment, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();
        mTranslation = PreferenceManager.getManager().getTranslation();

        if (mTranslation.equalsIgnoreCase("Cheriya Mundam/Parappoor")) {
            mTranslation = "meaning_malayalam";
        } else if (mTranslation.equalsIgnoreCase("Amani Thafseer")) {
            mTranslation = "meaning_malayalam_new";

        }


        memorizationFragmentController = new MemorizationFragmentController(getActivity(), Memorisationtestfragment.this);
        tvDescription = mFragmentView.findViewById(R.id.mMeaningArabicTextView);
        chapterlayout = mFragmentView.findViewById(R.id.chapterlayout);
        chapternameTextView = mFragmentView.findViewById(R.id.chapternameTextView);
        mChapternoTextView = mFragmentView.findViewById(R.id.mChapternoTextView);
        mMeaningMalayalamTextView = mFragmentView.findViewById(R.id.mMeaningMalayalamTextView);
        fromlayout = mFragmentView.findViewById(R.id.fromlayout);
        tolayout = mFragmentView.findViewById(R.id.tolayout);
        versemeaninglayout = mFragmentView.findViewById(R.id.versemeaninglayout);
        versedetaillayout = mFragmentView.findViewById(R.id.versedetaillayout);
        previous = mFragmentView.findViewById(R.id.previous);
        next = mFragmentView.findViewById(R.id.next);
        previous.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        versemeaninglayout.setVisibility(View.GONE);
        versedetaillayout.setVisibility(View.GONE);
        versefromtextview = mFragmentView.findViewById(R.id.versefromtextview);
        versetotextview = mFragmentView.findViewById(R.id.versetotextview);
        showmore = mFragmentView.findViewById(R.id.showmore);


        showmore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!expand) {
                    ObjectAnimator animation = ObjectAnimator.ofInt(tvDescription, "maxLines", 40);
                    animation.setDuration(100).start();
                    //   btnSeeMore.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_collapse));
                    showmore.setText("Show less");
                    expand = true;
                } else {
                    expand = false;
                    ObjectAnimator animation = ObjectAnimator.ofInt(tvDescription, "maxLines", 1);
                    animation.setDuration(100).start();
                    // showmore.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_expand));
                    showmore.setText("Show more");
                }

            }
        });


        List<ChapterList> chapterLists = memorizationFragmentController.checkdatapresent();
        if (chapterLists == null || chapterLists.isEmpty()) {
            if (isNetworkAvailable()) {
                progress();
                memorizationFragmentController.startfetching();
            }
        } else {
            memorizationFragmentController.parsedata(chapterLists);
        }

        chapterlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chapterlist != null) {
                    PopupMenu menu = new PopupMenu(getActivity(), view);

                    for (int p = 0; p < chapterlist.length; p++) {
                        menu.getMenu().add(Menu.NONE, p, p, chapterlist[p]);
                    }
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();
                            chapternameTextView.setText(item.getTitle());


                            for (ChapterList chapterList : chapterFullList) {
                                if (chapterList.getChapter_name().equalsIgnoreCase(item.getTitle().toString())) {
                                    setverse(chapterList.getSurah_no());
                                    mChapterno = chapterList.getSurah_no();
                                }
                            }
                            memorizationFragmentController.startfetchingmemorizationtest(mChapterno, mVerseFrom, mVerseTo, mTranslation);


                            return true;
                        }

                    });
                }


            }
        });
        fromlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verselist != null) {
                    PopupMenu menu = new PopupMenu(getActivity(), view);

                    for (int p = 0; p < verselist.length; p++) {
                        menu.getMenu().add(Menu.NONE, p, p, verselist[p]);
                    }
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();
                            versefromtextview.setText(item.getTitle());

                            mVerseFrom = item.getTitle().toString();

                            memorizationFragmentController.startfetchingmemorizationtest(mChapterno, mVerseFrom, mVerseTo, mTranslation);

                            return true;

                        }

                    });
                }

            }
        });

        tolayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verselist != null) {

                    PopupMenu menu = new PopupMenu(getActivity(), view);

                    for (int p = 0; p < verselist.length; p++) {
                        menu.getMenu().add(Menu.NONE, p, p, verselist[p]);
                    }
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();
                            versetotextview.setText(item.getTitle());
                            mVerseTo = item.getTitle().toString();
                            memorizationFragmentController.startfetchingmemorizationtest(mChapterno, mVerseFrom, mVerseTo, mTranslation);
                            return true;
                        }

                    });

                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current < max - 1) {
                    System.out.println("current" + current);
                    System.out.println("maxcurrent" + max);
                    current++;
                   viewvisiblility();

                   previous.setBackground(getResources().getDrawable(R.drawable.mailsend_bg));
                    next.setBackground(getResources().getDrawable(R.drawable.mailsend_bg));

                    mChapternoTextView.setText(VerseList.get(current).getSurah_no() + " : " + VerseList.get(current).getAyat_no());
                    tvDescription.setText(VerseList.get(current).getMeaning_arabic());
                    if (mTranslation.equalsIgnoreCase("meaning_malayalam")) {
                        mMeaningMalayalamTextView.setText(VerseList.get(current).getMeaning_malayalam());

                    } else if (mTranslation.equalsIgnoreCase("meaning_malayalam_new")) {

                        mMeaningMalayalamTextView.setText(VerseList.get(current).getMeaning_malayalam_new());

                    }
                    if (current == max - 1) {
                        next.setBackground(getResources().getDrawable(R.drawable.dim_prev_bg));
                    }


                } else {
                    next.setBackground(getResources().getDrawable(R.drawable.dim_prev_bg));
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current > 0) {
                    System.out.println("current" + current);
                    System.out.println("maxcurrent" + max);
                    current--;
                  viewvisiblility();
                    previous.setBackground(getResources().getDrawable(R.drawable.mailsend_bg));
                    next.setBackground(getResources().getDrawable(R.drawable.mailsend_bg));

                    mChapternoTextView.setText(VerseList.get(current).getSurah_no() + " : " + VerseList.get(current).getAyat_no());
                    tvDescription.setText(VerseList.get(current).getMeaning_arabic());

                    if (mTranslation.equalsIgnoreCase("meaning_malayalam")) {
                        mMeaningMalayalamTextView.setText(VerseList.get(current).getMeaning_malayalam());

                    } else if (mTranslation.equalsIgnoreCase("meaning_malayalam_new")) {

                        mMeaningMalayalamTextView.setText(VerseList.get(current).getMeaning_malayalam_new());

                    }
                    if (current == 0) {
                        previous.setBackground(getResources().getDrawable(R.drawable.dim_prev_bg));

                    }
                } else {
                    previous.setBackground(getResources().getDrawable(R.drawable.dim_prev_bg));

                }
            }
        });
    }

    public void setverse(String chapterno) {
        List<ChapterList> lists = memorizationFragmentController.checkchapterdetail(chapterno);
        if (lists != null) {

            verselist = new String[lists.get(0).getAyat().size()];
            for (int i = 0; i < lists.get(0).getAyat().size(); i++) {
                verselist[i] = lists.get(0).getAyat().get(i).getAyat_no();
            }
        }
        // setversetopopup(verselist);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void fetchingfailure() {

    }

    @Override
    public void progress() {

    }

    @Override
    public void datapassing(List<ChapterList> chapterLists) {

        if (chapterLists != null) {
            chapterFullList = chapterLists;
            chapterlist = new String[chapterLists.size()];
            for (int i = 0; i < chapterLists.size(); i++) {
                chapterlist[i] = chapterLists.get(i).getChapter_name();
            }
        }

    }

    @Override
    public void versedetail(List<Verse> verseList) {
        if (verseList != null) {
            VerseList = new ArrayList<>();
            VerseList = verseList;


            if (VerseList.size() == 1) {
                current = 0;
                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

viewvisiblility();


                previous.setBackground(getResources().getDrawable(R.drawable.dim_prev_bg));
                next.setBackground(getResources().getDrawable(R.drawable.dim_prev_bg));

                mChapternoTextView.setText(VerseList.get(0).getSurah_no() + " : " + VerseList.get(0).getAyat_no());
                tvDescription.setText(VerseList.get(0).getMeaning_arabic());

                if (mTranslation.equalsIgnoreCase("meaning_malayalam")) {
                    mMeaningMalayalamTextView.setText(VerseList.get(0).getMeaning_malayalam());

                } else if (mTranslation.equalsIgnoreCase("meaning_malayalam_new")) {

                    mMeaningMalayalamTextView.setText(VerseList.get(0).getMeaning_malayalam_new());

                }


            } else {
                current = 0;
               viewvisiblility();

                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                min = 0;
                max = VerseList.size();

                previous.setBackground(getResources().getDrawable(R.drawable.mailsend_bg));
                next.setBackground(getResources().getDrawable(R.drawable.mailsend_bg));

                mChapternoTextView.setText(VerseList.get(0).getSurah_no() + " : " + VerseList.get(0).getAyat_no());
                tvDescription.setText(VerseList.get(0).getMeaning_arabic());

                if (mTranslation.equalsIgnoreCase("meaning_malayalam")) {
                    mMeaningMalayalamTextView.setText(VerseList.get(0).getMeaning_malayalam());

                } else if (mTranslation.equalsIgnoreCase("meaning_malayalam_new")) {

                    mMeaningMalayalamTextView.setText(VerseList.get(0).getMeaning_malayalam_new());

                }


            }

        } else {
            versedetaillayout.setVisibility(View.GONE);
            versemeaninglayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void Practiceversedetail(List<Practice> verseList) {

    }

    @Override
    public void dataReciterAudiopassing(List<AudioCategoryReciterList> audio) {

    }

    public void showfilterdialog() {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.filter_dialog);
        TextView mFiltertextview = (TextView) dialog.findViewById(R.id.mSharetextview);
        RadioGroup filter = (RadioGroup) dialog.findViewById(R.id.filter);
        if (filtertext.equalsIgnoreCase("Show Both")) {
            filter.check(((RadioButton)filter.getChildAt(0)).getId());

        }
        else
        if (filtertext.equalsIgnoreCase("Verse")) {
            filter.check(((RadioButton)filter.getChildAt(1)).getId());

        }
        else
        if (filtertext.equalsIgnoreCase("Translation")) {
            filter.check(((RadioButton)filter.getChildAt(2)).getId());

        }

        mFiltertextview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int selectedId = filter.getCheckedRadioButtonId();
                filterradiobutton = (RadioButton) dialog.findViewById(selectedId);
                if (filterradiobutton.getText().toString().equalsIgnoreCase("Show Both")) {
                    filtertext = "Show Both";

                } else if (filterradiobutton.getText().toString().equalsIgnoreCase("Verse")) {
                    filtertext = "Verse";
                } else if (filterradiobutton.getText().toString().equalsIgnoreCase("Translation")) {
                    filtertext = "Translation";
                }
                dialog.dismiss();
                viewvisiblility();
            }

        });
        dialog.show();
    }

    public void viewvisiblility() {
        if (filtertext.equalsIgnoreCase("Show Both")) {
            versedetaillayout.setVisibility(View.VISIBLE);
            versemeaninglayout.setVisibility(View.VISIBLE);
        } else if (filtertext.equalsIgnoreCase("Verse")) {
            versedetaillayout.setVisibility(View.VISIBLE);
            versemeaninglayout.setVisibility(View.GONE);
        }
        if (filtertext.equalsIgnoreCase("Translation")) {
            versedetaillayout.setVisibility(View.GONE);
            versemeaninglayout.setVisibility(View.VISIBLE);
        }
    }
}
