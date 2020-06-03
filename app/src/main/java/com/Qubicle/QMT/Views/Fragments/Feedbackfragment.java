package com.Qubicle.QMT.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.Qubicle.QMT.Base.BaseFragment;
import com.Qubicle.QMT.Controllers.ReadingFragmentController;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.Views.Activity.HomeActivity;
import com.Qubicle.QMT.utils.Utility;


public class Feedbackfragment extends BaseFragment implements View.OnClickListener {
    Context context;
    Utility.OnFragmnetSwitch mOnFragmnetSwitch;
    Utility.menuIconChange OnMenuIconChange;
TextView mSendTextView;
EditText mMailIdEditTextView,mFeedbackEditText;
    public Feedbackfragment(Context context) {
        this.context = context;

    }

    public Feedbackfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.feedback, container, false);
        mainfunction(mFragmentView);
        return mFragmentView;

    }

    public void mainfunction(View mFragmentView) {
        OnMenuIconChange = (Utility.menuIconChange) getActivity();
        mOnFragmnetSwitch = (Utility.OnFragmnetSwitch) getActivity();


        mSendTextView = mFragmentView.findViewById(R.id.mSendTextView);

        mFeedbackEditText = mFragmentView.findViewById(R.id.mFeedbackEditText);
        mSendTextView.setOnClickListener(this);
        // mReadingController=new ReadingFragmentController(getActivity(), Feedbackfragment.this);


    }

    @Override
    public void onResume() {
        super.onResume();
        mOnFragmnetSwitch.loadTitle("FEEDBACK");
        OnMenuIconChange.iconchange(Feedbackfragment.this);
        HomeActivity.fragment = Feedbackfragment.this;
    }

    @Override
    public void onClick(View view) {
        if(view == mSendTextView) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","anjujohnson4@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
            emailIntent.putExtra(Intent.EXTRA_TEXT, mFeedbackEditText.getText().toString().trim());
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
        /*else if(view==mJuzTextView){

        }*/
    }
}