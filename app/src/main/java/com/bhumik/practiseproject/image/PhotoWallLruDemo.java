package com.bhumik.practiseproject.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.bhumik.practiseproject.R;

public class PhotoWallLruDemo extends AppCompatActivity {

    private static final String TAG = "PhotoWallLruDemo";
    private GridView mPhotoWall;
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photowalllru);
        mPhotoWall = (GridView) findViewById(R.id.gridvphoto_wall);
        adapter = new PhotoWallAdapter(this, 0, mPhotoWall);
        mPhotoWall.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // End all download tasks when you quit the program
        adapter.cancelAllTasks();
    }

}
