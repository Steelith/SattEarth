package com.kisielar.sattearth;

import android.app.WallpaperManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

public class PreviewActivity extends AppCompatActivity {

    String wallpaperType;
    int actualWallpaperId;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        layout = findViewById(R.id.previewLayout);

        Bundle extras = getIntent().getExtras();
        if(extras != null) wallpaperType = extras.getString(LocationActivity.WALLPAPER);

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
}
