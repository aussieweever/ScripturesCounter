package com.skynet.scripturescounter;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by nick on 26/05/15.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment()).commit();
    }
}
