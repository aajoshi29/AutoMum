package com.example.personalassistant;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.location.Address;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity
{

    private GoogleMap mMap;
    String location;
    Double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng point)
            {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(point);
                markerOptions.title(point.latitude + " : " + point.longitude);
                location=point.toString();
                latitude=point.latitude;
                longitude=point.longitude;
                Toast.makeText(MapsActivity.this,location,Toast.LENGTH_SHORT).show();
                mMap.clear();
                mMap.addMarker(markerOptions);
            }
        });
    }
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }
    public void onSearch(View view)
    {
        EditText Location=(EditText)findViewById(R.id.TFaddress);
        String Location_str=Location.getText().toString();
        List<Address> addressList = null;
        if(Location_str != null || !Location_str.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try
            {
                addressList = geocoder.getFromLocationName(Location_str, 1);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    private void setUpMapIfNeeded()
    {
        if (mMap == null)
        {
            mMap =((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap !=null)
            {
                setUpMap();
            }
        }
    }
    private void setUpMap()
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
    }
    public void onSave(View v)
    {
        Intent intent=new Intent(MapsActivity.this,LocationActivity.class);
        intent.putExtra("location",location);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        setResult(RESULT_OK,intent);
        finish();
    }
}
