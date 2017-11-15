package com.example.personalassistant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{

    ListView listView;
    String set[]={"Silent Mode Using Time","Silent Mode Using Location"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,set){
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View v=super.getView(position,convertView,parent);
                TextView tv=(TextView)v.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return v;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(set[position].equals("Silent Mode Using Time"))
                {
                    Intent intent=new Intent(MainActivity.this,TimeActivity.class);
                    startActivity(intent);
                }
                else if(set[position].equals("Silent Mode Using Location"))
                {
                    Intent intent=new Intent(MainActivity.this,LocationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
