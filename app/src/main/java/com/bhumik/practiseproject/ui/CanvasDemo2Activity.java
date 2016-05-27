package com.bhumik.practiseproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.bhumik.practiseproject.R;
import com.bhumik.practiseproject.ui.views.CircularProgressView;
import com.bhumik.practiseproject.ui.views.MyViewGroup;
import com.bhumik.practiseproject.ui.views.PieView;
import com.bhumik.practiseproject.ui.views.PieView.PieData;

import java.util.ArrayList;

/**
 * Created by bhumik on 16/5/16.
 */
public class CanvasDemo2Activity extends AppCompatActivity {

    private static String TAG = "CanvasDemo2Activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_canvasdemo);


    }

}