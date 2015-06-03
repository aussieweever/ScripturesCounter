package com.skynet.scripturescounter;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by nick on 26/05/15.
 *
 * Setting Fragment to use the Preferences.xml
 *
 *
 */
public class PreferencesFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
