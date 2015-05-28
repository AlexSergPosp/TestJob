package com.example.alex.jobtest;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

/**
 * Created by Alex on 28.05.2015.
 */
public class Preference extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, "Preference.onCreate");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
