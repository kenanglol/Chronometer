package com.example.krono2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    Thread t1;
    CoordinatorLayout cdr;
    Button btn_start,btn_stop,btn_reset;
    TextView chrn1,chrn2,fark_view;
    Boolean stop=true;
    long last=0;
    long ilkstart;
    Kronometre krono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(NightMode.getIsNightModeEnabled()){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        MenuItem item=(MenuItem)findViewById(R.id.action_settings);
        if(NightMode.getIsNightModeEnabled()){
            toolbar.setBackgroundColor(getResources().getColor(R.color.Background_Dark));
            toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Dark));
        }else{
            toolbar.setBackgroundColor(getResources().getColor(R.color.Background));
            toolbar.setTitleTextColor(getResources().getColor(R.color.Text));
        }

        setSupportActionBar(toolbar);
        if(Kronometre.asıl==null){
            Kronometre.setAsıl(new Kronometre());
            krono=Kronometre.getAsıl();
        }else{
            krono=Kronometre.getAsıl();
        }
        fark_view = (TextView) findViewById(R.id.listeleme);
        fark_view.setText(SetListe(krono.durmalar));
        chrn1 = (TextView) findViewById(R.id.chronometer1);
        chrn2 = (TextView) findViewById(R.id.chronometer2);
        btn_start = findViewById(R.id.baslat);
        btn_stop = findViewById(R.id.durdur);
        btn_reset = findViewById(R.id.sıfırla);
        fark_view.setText(SetListe(krono.durmalar));
        if(Kronometre.getMilli()){
            chrn1.setText(Kronometre.format_duzenleme_milli(krono.toplam_sayilan));
            chrn2.setText(Kronometre.ekleme_milli(krono.toplam_sayilan));
        }else{
            chrn1.setText(Kronometre.format_duzenleme_second(krono.toplam_sayilan));
        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_stop.setEnabled(true);
                if(krono.isStarted){
                    krono.Continue();
                }else{
                    krono.Start();
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                krono.Stop();
                btn_stop.setEnabled(false);
                fark_view.setText(SetListe(krono.durmalar));
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                krono.Reset();
                btn_stop.setEnabled(false);
                if(Kronometre.getMilli()){
                    chrn1.setText(Kronometre.format_duzenleme_milli(krono.toplam_sayilan));
                    chrn2.setText(Kronometre.ekleme_milli(krono.toplam_sayilan));
                }else{
                    chrn1.setText(Kronometre.format_duzenleme_second(krono.toplam_sayilan));
                }
                fark_view.setText("");
            }
        });
        Log.d("create","Ana uygulama yaratıldı");
    }

    protected String SetListe(LinkedList<duraklama> durdur){
        String liste="";
        for(int i=0;i<durdur.size();i++){
            liste+="\t";
            liste+=durdur.get(i).toString();
            Log.d("FARKLAR :   ",String.valueOf(durdur.get(i).fark));
            liste+="\n\n\n";
        }
        Log.d(".","___________________");
        return liste;

    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d("start","Ana uygulama başladı");
        if(Kronometre.getMilli()){
            chrn1.setText(Kronometre.format_duzenleme_milli(krono.toplam_sayilan));
            chrn2.setText(Kronometre.ekleme_milli(krono.toplam_sayilan));
        }else{
            chrn1.setText(Kronometre.format_duzenleme_second(krono.toplam_sayilan));
        }
        t1=new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){

                    try {
                        Thread.sleep(10);  //1000ms = 1 se
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                krono.CountUp();
                                if(Kronometre.getMilli()){
                                    chrn1.setText(Kronometre.format_duzenleme_milli(krono.toplam_sayilan));
                                    chrn2.setText(Kronometre.ekleme_milli(krono.toplam_sayilan));
                                }else{
                                    chrn1.setText(Kronometre.format_duzenleme_second(krono.toplam_sayilan));
                                }
                            }
                        });

                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t1.start();
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
            Intent set_intent=new Intent(this,SettingsActivity2.class);
            startActivity(set_intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
