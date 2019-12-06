package com.example.krono2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity2 extends AppCompatActivity {
    static Context mycontext;
    static Activity active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
            NightMode.Enabled();
        }else{
            setTheme(R.style.AppTheme);
            NightMode.Disabled();
        }
        mycontext=getApplicationContext();
        active= this;
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //geri butonu ekleme!!!
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public  static void restartApp(){
        Intent tent=new Intent(mycontext,SettingsActivity2.class);
        active.startActivity(tent);
        active.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        Intent tent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(tent);
        // close this activity and return to preview activity (if there is any)

        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        SwitchPreferenceCompat myswitch;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            myswitch=(SwitchPreferenceCompat) findPreference("switcher");
            if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                myswitch.setChecked(true);
                NightMode.Enabled();
            }else{
                myswitch.setChecked(false);
                NightMode.Disabled();
            }
            myswitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean switched = ((SwitchPreferenceCompat) preference)
                            .isChecked();
                    if(!switched){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }else{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    SettingsActivity2.restartApp();
                    return false;
                }
            });
            final ListPreference list_pref=findPreference("millisec");
            list_pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    //String val=newValue.toString();
                    ListPreference pref=(ListPreference)preference;
                    if(newValue.toString().equals(pref.getEntryValues()[0])){
                        Kronometre.setMilli(true);
                    }else{
                        Kronometre.setMilli(false);
                    }
                    Kronometre.setAsıl(null);
                    return false;
                }
            });
        }
    }
}