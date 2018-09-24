package com.kisielar.sattearth;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends AppCompatActivity {

    String wallpaperType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Bundle extras = getIntent().getExtras();
        if(extras != null) wallpaperType = extras.getString(LocationActivity.WALLPAPER);

        assert wallpaperType != null;
        changeBackgroundImage(wallpaperType);
    }

    private void changeBackgroundImage(String wallpaperType) {
        ConstraintLayout layout = findViewById(R.id.previewLayout);
        if (wallpaperType.equals("wallpaper1")) {
            layout.setBackgroundResource(R.mipmap.wallpaper1);
        } else if (wallpaperType.equals("wallpaper2")) {
            layout.setBackgroundResource(R.mipmap.wallpaper2);
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
            Toast.makeText(this, "There are no acceptation yet!", Toast.LENGTH_SHORT).show();
            return true;
        } return super.onOptionsItemSelected(item);
    }
}
