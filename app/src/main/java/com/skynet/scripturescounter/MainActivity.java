package com.skynet.scripturescounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.security.Key;


public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

        private int _currentCount = 0;
    private long _totalCount = 0;
    private String _currentName = null;
    private boolean _vibration = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateInformation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(home);
        }

        return true;
    }

    @Override

    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            increaseCount();
        }

        return true;
    }

    private void increaseCount(){
        if(_currentCount == 9999)
            _currentCount = 0;
        else
            ++_currentCount;

        ++_totalCount;

        //Vibrate every 1000 times
        if(_currentCount%1000 == 0 && _vibration)
            vibrate();

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                .putString(getString(R.string.pref_current_count), String.valueOf(_currentCount))
                .putString(getString(R.string.pref_total_count), String.valueOf(_totalCount))
                .commit();

        updateCount(_currentCount, _totalCount);
    }

    private void showName(String name){

        TextView txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText(String.valueOf(name));
    }

    private void updateCount(int currentCount, long totalCount){
        TextView current = (TextView)findViewById(R.id.txtCurrentCounter);
        TextView total = (TextView)findViewById(R.id.txtTotalCounter);

        current.setText(String.valueOf(currentCount));
        total.setText(String.valueOf(totalCount));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if(key.equals(getString(R.string.pref_name))){
            showName(sharedPreferences.getString(key, getString(R.string.title_default_name)));
        }else if(key.equals(getString(R.string.pref_vibration))){
            _vibration = Boolean.valueOf(sharedPreferences.getString(key, "1"));
        }else {
            if (key.equals(getString(R.string.pref_current_count)))
                _currentCount = Integer.valueOf(sharedPreferences.getString(key, "0"));
            else if (key.equals(getString(R.string.pref_total_count)))
                _totalCount = Long.valueOf(sharedPreferences.getString(key, "0"));

            updateCount(_currentCount, _totalCount);
        }
    }

    private void updateInformation(){
        SharedPreferences pres = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        _currentCount = Integer.valueOf(
                pres.getString(getString(R.string.pref_current_count), "0"));
        _totalCount = Long.valueOf(
                pres.getString(getString(R.string.pref_total_count), "0"));
        _currentName = pres.getString(getString(R.string.pref_name), getString(R.string.title_default_name));

        _vibration = pres.getBoolean(getString(R.string.pref_vibration), true);

        showName(_currentName);
        updateCount(_currentCount, _totalCount);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateInformation();
    }

    private void vibrate(){
        Vibrator v = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        v.vibrate(500);
    }
}
