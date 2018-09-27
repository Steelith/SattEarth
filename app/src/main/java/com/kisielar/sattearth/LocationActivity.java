package com.kisielar.sattearth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LocationActivity extends AppCompatActivity {

    public static final String WALLPAPER_KEY = "Wallpaper";
    public static final String LOCATION_KEY = "Location";
    public static final String METHOD_KEY = "Method";
    
    EditText locationText;
    Button searchButton;
    ImageView gpsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationText = findViewById(R.id.locationEditText);
        searchButton = findViewById(R.id.buttonSearch);
        gpsButton = findViewById(R.id.imageViewGps);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SattEarthTag", "Search onClick() triggered");
                String userLocation = locationText.getText().toString();

                Intent searchIntent = new Intent(LocationActivity.this, PreviewActivity.class);
                searchIntent.putExtra(WALLPAPER_KEY, "wallpaper1");
                searchIntent.putExtra(LOCATION_KEY, userLocation);
                searchIntent.putExtra(METHOD_KEY, "search");
                startActivity(searchIntent);
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO try to get gps data
                //TODO when data is gained send it to PreviewActivity
                Log.d("SattEarthTag", "GPS onClick() triggered");

                Intent searchIntent = new Intent(LocationActivity.this, PreviewActivity.class);
                searchIntent.putExtra(WALLPAPER_KEY, "wallpaper2");
                searchIntent.putExtra(METHOD_KEY, "gps");
                startActivity(searchIntent);
            }
        });
    }


}
