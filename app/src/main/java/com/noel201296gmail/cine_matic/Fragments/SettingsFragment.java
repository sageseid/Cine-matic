package com.noel201296gmail.cine_matic.Fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.noel201296gmail.cine_matic.R;


/**
 * Created by OMOSEFE NOEL OBASEKI on 06/05/2017.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
               addPreferencesFromResource(R.xml.pref_settings);
    }
}
