package com.Qubicle.QMT.Base;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;


public class BaseFragment extends Fragment {
    public BaseFragment() {
        super();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (phoneNumber.equals("")) {
            return true;
        }
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
/*    public void setSharedPreference(String key, String value) {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.SHARED_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getSharedPreference(String key) {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.SHARED_KEY, MODE_PRIVATE);
        return prefs.getString(key, "DEFAULT");
    }*/


}
