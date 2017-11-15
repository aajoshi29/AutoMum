package com.example.personalassistant;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity
{
        TextView textView4,textView5,textView9,textView10;
        private static final int REQUEST_PERMISSIONS = 100;
        boolean boolean_permission;
        Double latitude,longitude;
        Button btn_start,btn_stop;
        CheckBox checkBox;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.location_activity);
            btn_start = (Button) findViewById(R.id.button3);
            btn_stop = (Button) findViewById(R.id.button4);
            btn_start.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    textView4=(TextView)findViewById(R.id.textView4);
                    textView5=(TextView)findViewById(R.id.textView5);
                    textView9=(TextView)findViewById(R.id.textView9);
                    textView10=(TextView)findViewById(R.id.textView10);
                    checkBox=(CheckBox)findViewById(R.id.checkBox);
                    if(textView4.getText().toString().matches(textView9.getText().toString()) && textView5.getText().toString().matches(textView10.getText().toString()) && !checkBox.isChecked())
                    {
                        AudioManager myAudioManager;
                        myAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        Toast.makeText(getApplicationContext(), "Silent mode is turned on.", Toast.LENGTH_SHORT).show();
                    }
                    else if(textView4.getText().toString().matches(textView9.getText().toString()) && textView5.getText().toString().matches(textView10.getText().toString()) && checkBox.isChecked())
                    {
                        AudioManager myAudioManager;
                        myAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        Toast.makeText(getApplicationContext(), "Silent mode is turned on.", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), GoogleService.class);
                    startService(intent);
                }
            });
            btn_stop.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AudioManager myAudioManager;
                    myAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    if(myAudioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT || myAudioManager.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE)
                    {
                        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        Toast.makeText(getApplicationContext(), "Silent mode is turned off.", Toast.LENGTH_SHORT).show();
                    }
                    if(broadcastReceiver!=null)
                    {
                        unregisterReceiver(broadcastReceiver);
                    }
                    Intent intent = new Intent(getApplicationContext(), GoogleService.class);
                    stopService(intent);
                }
            });
        }

        public void onSetLocation(View v)
        {
            startActivityForResult(new Intent(LocationActivity.this,MapsActivity.class),999);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode==999 && resultCode==RESULT_OK)
            {
                textView4=(TextView)findViewById(R.id.textView4);
                textView5=(TextView)findViewById(R.id.textView5);
                textView4.setText(String.format("%.2f",data.getDoubleExtra("latitude",0))/*data.getStringExtra("latitude")*/);
                textView5.setText(String.format("%.2f",data.getDoubleExtra("longitude",0)));
            }
        }
        private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                latitude = Double.valueOf(intent.getStringExtra("latutide"));
                longitude = Double.valueOf(intent.getStringExtra("longitude"));
                textView9=(TextView)findViewById(R.id.textView9);
                textView10=(TextView)findViewById(R.id.textView10);
                checkBox=(CheckBox)findViewById(R.id.checkBox);
                textView9.setText(String.format("%.2f",latitude)/*latitude+""*/);
                textView10.setText(String.format("%.2f",longitude)/*+""*/);
                if(textView4.getText().toString().matches(textView9.getText().toString()) && textView5.getText().toString().matches(textView10.getText().toString()) && !checkBox.isChecked())
                {
                    AudioManager myAudioManager;
                    myAudioManager =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Toast.makeText(getApplicationContext(), "Silent mode is turned on.", Toast.LENGTH_SHORT).show();
                }
                else if(textView4.getText().toString().matches(textView9.getText().toString()) && textView5.getText().toString().matches(textView10.getText().toString()) && checkBox.isChecked())
                {
                    AudioManager myAudioManager;
                    myAudioManager =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(getApplicationContext(), "Silent mode is turned on.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AudioManager myAudioManager;
                    myAudioManager =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        };

        @Override
        protected void onResume()
        {
            super.onResume();
            registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));
        }
        @Override
        protected void onPause()
        {
            super.onPause();
            unregisterReceiver(broadcastReceiver);
        }

        @Override
        protected void onDestroy()
        {
            super.onDestroy();
            if(broadcastReceiver!=null)
            {
                unregisterReceiver(broadcastReceiver);
            }
        }
    }
