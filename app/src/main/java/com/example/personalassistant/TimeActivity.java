package com.example.personalassistant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeActivity extends Activity
{
    CheckBox c1;
    AudioManager audio;
    ListView listv,listView;
    TextView t1,t2;
    String[] ad = {"Start Time","End Time"};
    String[] model = {"Time","Time"};
    static int pHour,pHour2;
    static int pMinute,pMinute2;
    Calendar cal,cal2;
    AlarmManager alarmManager,alarmManager2;
    PendingIntent pendingIntent,pendingIntent2;
    static final int TIME_DIALOG_ID = 0,TIME_DIALOG_ID2 = 1;
    View v;
    int a=0,b=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_activity);
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager2=(AlarmManager)getSystemService(ALARM_SERVICE);
        cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);
        model[0]=""+pHour+":"+pMinute;
        cal2 = Calendar.getInstance();
        pHour2 = cal2.get(Calendar.HOUR_OF_DAY);
        pMinute2 = cal2.get(Calendar.MINUTE);
        model[1]=""+pHour2+":"+pMinute2;
        listv = (ListView) findViewById(R.id.mainListView);
        c1=(CheckBox)findViewById(R.id.checkBox1);
        Adapterim adapter = new Adapterim(getApplicationContext(), ad, model);
        listv.setAdapter(adapter);
        a=0;
        b=0;
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                if(ad[position].equals("Start Time"))
                {
                    showDialog(TIME_DIALOG_ID);
                    v=view;
                }
                else if(ad[position].equals("End Time"))
                {
                    showDialog(TIME_DIALOG_ID2);
                    v=view;
                }
            }
        });
    }
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener()
    {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            a++;
            pHour = hourOfDay;
            pMinute = minute;
            cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, pHour);
            cal.set(Calendar.MINUTE, pMinute);
            t2=(TextView)v.findViewById(R.id.custom_textView2);
            t2.setText(""+pHour+":"+pMinute);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener()
    {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            b++;
            pHour2 = hourOfDay;
            pMinute2 = minute;
            cal2 = Calendar.getInstance();
            cal2.set(Calendar.HOUR_OF_DAY, pHour2);
            cal2.set(Calendar.MINUTE, pMinute2);
            t2=(TextView)v.findViewById(R.id.custom_textView2);
            t2.setText(""+pHour2+":"+pMinute2);
        }
    };
    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,mTimeSetListener, pHour, pMinute, false);
            case TIME_DIALOG_ID2:
                return new TimePickerDialog(this,mTimeSetListener2, pHour, pMinute, false);
        }
        return null;
    }
    public void TurnOn(View v)
    {
        if(a==0 && b!=0)
        {
            Toast.makeText(TimeActivity.this,"Please set start time.", Toast.LENGTH_SHORT).show();
        }
        else if(a!=0 && b==0)
        {
            Toast.makeText(TimeActivity.this,"Please set end time.", Toast.LENGTH_SHORT).show();
        }
        else if (a!=0 && b!=0)
        {
            if(c1.isChecked())
            {
                Intent intent=new Intent(TimeActivity.this,VibrateModeReceiver.class);
                pendingIntent=PendingIntent.getBroadcast(TimeActivity.this, 0, intent,0);
                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(),pendingIntent);
                Intent intent2=new Intent(TimeActivity.this,NormalModeReceiver.class);
                pendingIntent2=PendingIntent.getBroadcast(TimeActivity.this, 0, intent2,0);
                alarmManager2.setExact(AlarmManager.RTC, cal2.getTimeInMillis(),pendingIntent2);
                Toast.makeText(TimeActivity.this,"Silent mode will be turned on from "+pHour+":"+pMinute+" to "+pHour2+":"+pMinute2, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent intent=new Intent(TimeActivity.this,SilentModeReceiver.class);
                pendingIntent=PendingIntent.getBroadcast(TimeActivity.this, 0, intent,0);
                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(),pendingIntent);
                Intent intent2=new Intent(TimeActivity.this,NormalModeReceiver.class);
                pendingIntent2=PendingIntent.getBroadcast(TimeActivity.this, 0, intent2,0);
                alarmManager2.setExact(AlarmManager.RTC, cal2.getTimeInMillis(),pendingIntent2);
                Toast.makeText(TimeActivity.this,"Silent mode will be turned on from "+pHour+":"+pMinute+" to "+pHour2+":"+pMinute2, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(TimeActivity.this,"Please set start time and end time.", Toast.LENGTH_SHORT).show();
        }
    }
    public void TurnOff(View v)
    {
        audio =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(audio.getRingerMode()==AudioManager.RINGER_MODE_SILENT || audio.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE)
        {
            audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else
        {
            if(c1.isChecked())
            {
                Intent intent=new Intent(TimeActivity.this,VibrateModeReceiver.class);
                pendingIntent=PendingIntent.getBroadcast(TimeActivity.this, 0, intent, 0);
                alarmManager.cancel(pendingIntent);
                Intent intent2=new Intent(TimeActivity.this,NormalModeReceiver.class);
                pendingIntent2=PendingIntent.getBroadcast(TimeActivity.this, 0, intent2, 0);
                alarmManager2.cancel(pendingIntent2);
            }
            else
            {
                Intent intent=new Intent(TimeActivity.this,SilentModeReceiver.class);
                pendingIntent=PendingIntent.getBroadcast(TimeActivity.this, 0, intent, 0);
                alarmManager.cancel(pendingIntent);
                Intent intent2=new Intent(TimeActivity.this,NormalModeReceiver.class);
                pendingIntent2=PendingIntent.getBroadcast(TimeActivity.this, 0, intent2, 0);
                alarmManager2.cancel(pendingIntent2);
            }
        }
        Toast.makeText(TimeActivity.this,"Silent mode is turned off.", Toast.LENGTH_SHORT).show();
    }
}
class Adapterim extends ArrayAdapter<String>
{
    String[] ad = {};
    String[] model = {};
    Context c;
    LayoutInflater inflater;
    public Adapterim(Context context, String[] ad, String[] model)
    {
        super(context, R.layout.simplerow2, ad);
        this.ad = ad;
        this.model = model;
        this.c = context;
    }
    public class Viewholder
    {
        TextView adtv;
        TextView modeltv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simplerow2,null, true);
        }
        final Viewholder holder = new Viewholder();
        holder.adtv = (TextView)convertView.findViewById(R.id.custom_textView);
        holder.modeltv = (TextView)convertView.findViewById(R.id.custom_textView2);
        holder.adtv.setText(ad[position]);
        holder.modeltv.setText(model[position]);
        return convertView;
    }
}