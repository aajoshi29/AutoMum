package com.example.personalassistant;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class SilentModeReceiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(final Context context, Intent intent)
    {
        AudioManager myAudioManager;
        myAudioManager =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Toast.makeText(context,"Silent mode is turned on.",Toast.LENGTH_SHORT).show();
    }

}
