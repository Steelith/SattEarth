package com.kisielar.sattearth;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class PreviewActivity extends AppCompatActivity {

    String wallpaperType;
    int actualWallpaperId;
    ConstraintLayout layout;
    TextView previewTextView;

    final String BASE_LINK_FOR_IMAGE = "https://epic.gsfc.nasa.gov/archive/enhanced/";
    final String BASE_LINK_FOR_JSON = "https://epic.gsfc.nasa.gov/api/enhanced/date/";
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    //TODO doesn't work on first day of month
    int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;

    final int REQUEST_CODE = 123;
    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 5;
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        layout = findViewById(R.id.previewLayout);
        previewTextView = findViewById(R.id.previewTextView);

        String jsonLink = String.format(Locale.getDefault(), BASE_LINK_FOR_JSON + "%d-%02d-%02d", year, month, day);
        //String imageLink = String.format(Locale.getDefault(), BASE_LINK_FOR_IMAGE + "%d/%02d/%02d/jpg/",year, month, day);
        Log.d("SattEarthTag", jsonLink);
        doNetworking(jsonLink);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            wallpaperType = extras.getString(LocationActivity.WALLPAPER_KEY);
            String methodType = extras.getString(LocationActivity.METHOD_KEY);

            assert methodType != null;
            if (methodType.equals("gps")) {
                getWeatherForCurrentLocation();
            } else if (methodType.equals("search")) {
                String userLocation = extras.getString(LocationActivity.LOCATION_KEY);
                previewTextView.setText(userLocation);
            }
        }

        assert wallpaperType != null;
        changeBackgroundImage(wallpaperType);
    }

    private void changeBackgroundImage(String wallpaperType) {
        if (wallpaperType.equals("wallpaper1")) {
            layout.setBackgroundResource(R.mipmap.wallpaper1);
            actualWallpaperId = R.mipmap.wallpaper1;
        } else if (wallpaperType.equals("wallpaper2")) {
            layout.setBackgroundResource(R.mipmap.wallpaper2);
            actualWallpaperId = R.mipmap.wallpaper2;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_accept) {
            WallpaperManager wallpaperMenago = WallpaperManager.getInstance(this);
            try {
                wallpaperMenago.setResource(actualWallpaperId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Wallpaper changed", Toast.LENGTH_SHORT).show();

            return true;
        } return super.onOptionsItemSelected(item);
    }

    private void getWeatherForCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d("SattEarthTag", "longitude is: " + longitude);
                Log.d("SattEarthTag", "latitude is: " + latitude);

                previewTextView.setText(longitude + " + " + latitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //Log.d("SattEarthTag", "onStatusChanged() called");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("SattEarthTag", "onProviderEnabled() called");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("SattEarthTag", "onProviderDisabled() called");
                Toast.makeText(PreviewActivity.this, "Please turn on GPS module.", Toast.LENGTH_SHORT).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    private void doNetworking(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SattEarthTag", "Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SattEarthTag", "Response failed");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
