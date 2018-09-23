package com.kisielar.sattearth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

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
                Intent searchIntent = new Intent(LocationActivity.this, PreviewActivity.class);
                startActivity(searchIntent);
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO try to get gps data
                //TODO when data is gained send it to PreviewActivity
                Toast.makeText(LocationActivity.this, "Gps clicked()", Toast.LENGTH_SHORT).show();
                Log.d("SattEarthTag", "GPS onClick() triggered");
            }
        });
    }
}
