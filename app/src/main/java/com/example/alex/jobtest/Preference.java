package com.example.alex.jobtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by Alex on 28.05.2015.
 */
public class Preference extends PreferenceActivity{
    boolean prafAuto = false;
    boolean prefFavorite = false;
    boolean prefRand = false;
    int prefIntarval = 1;
    int getPrefAnim = 1;
    CheckBoxPreference chbAuto;
    CheckBoxPreference chbFave;
    CheckBoxPreference chbRand;
    ListPreference listAnim;
    EditTextPreference editInterval;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        chbAuto = (CheckBoxPreference) findPreference("auto");
        chbFave = (CheckBoxPreference) findPreference("favorite");
        chbRand = (CheckBoxPreference) findPreference("random");
        editInterval = (EditTextPreference) findPreference("interval");
        listAnim = (ListPreference) findPreference("list");
    }
    public static void getPref(){

    }
}
