package com.example.mijotons;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class Carte extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    GoogleMap map;
    private Location lastKnownLocation;
    private final LatLng defaultLocation = new LatLng(-30, 150);
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted = false;
    Intent intent;
    Spinner s_choix;
    boolean choix =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);

        s_choix = findViewById(R.id.s_choix);
        s_choix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("test",s_choix.getSelectedItem().toString());
                if(s_choix.getSelectedItem().toString().equals("Restaurant")){
                    choix=false;
                }
                else
                {
                    choix=true;
                }
                getDeviceLocation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Navigation
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigationBar);
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_placards:
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_historique:
                    intent = new Intent(this, Historique.class);
                    startActivity(intent);
                    break;
                case R.id.action_courses:
                    intent = new Intent(this, Courses.class);
                    startActivity(intent);
                    break;
                case R.id.action_favoris:
                    intent = new Intent(this, Favoris.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.carte);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        getLocationPermission();

        getDeviceLocation();

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.stylemap));


    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        }
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15));
                            String url = null;
                            if(!choix){
                                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lastKnownLocation.getLatitude()+"%2C"+lastKnownLocation.getLongitude()+"&radius=1000&type=restaurant&key=AIzaSyAaRJFQACeYU4eyE8TJrsznVjX8XsZg8xA";
                            }
                            else
                            {
                                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lastKnownLocation.getLatitude()+"%2C"+lastKnownLocation.getLongitude()+"&radius=2000&type=supermarket&key=AIzaSyAaRJFQACeYU4eyE8TJrsznVjX8XsZg8xA";
                            }
                            new PlaceTask().execute(url);
                        }
                    } else {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
            else
            {
                s_choix.setEnabled(false);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            enableMyLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] != -1){
            intent = new Intent(getApplicationContext(), Carte.class);
            startActivity(intent);
            finish();
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException
    {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line ="";
        while((line = bufferedReader.readLine())!= null)
        {
            builder.append(line);
        }
        String data = builder.toString();
        bufferedReader.close();
        return data;

    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String, String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            map.clear();

            for(int i = 0;i<hashMaps.size();i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);

                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");

                LatLng latLng = new LatLng(lat,lng);

                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                if(choix)
                {
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.markersm));
                }
                else
                {
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.markerresto));
                }

                options.title(name);
                map.addMarker(options);
            }
        }
    }


}