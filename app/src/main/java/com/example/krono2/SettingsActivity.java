package com.example.krono2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    Kronometre kro;
    Switch mySwitch;
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
        setContentView(R.layout.settings_activity);
        /*mySwitch=(Switch)findViewById(R.id.switcher);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            mySwitch.setChecked(true);
            NightMode.Enabled();
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //isChecked switch'in check olup olmama hali boolean
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                restartApp();
            }
        });*/
        //Fragment class'ın kullanımı !!!!
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();*/
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


    public  void restartApp(){
        Intent tent=new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(tent);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("resume","Ayarlar devam ediyor.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("stop","Ayarlar durdu.");
    }

    @Override
    protected void onDestroy() {
        Log.d("destroy","Ayarlar kapatıldı.");
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
                Intent tent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(tent);
                // close this activity and return to preview activity (if there is any)

        return super.onOptionsItemSelected(item);
    }

}