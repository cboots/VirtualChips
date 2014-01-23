package com.cfms.virtualpot.lib.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.cfms.virtualpot.lib.R;

public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
