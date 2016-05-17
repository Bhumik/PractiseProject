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

/**
 * Created by bhumik on 16/5/16.
 */
public class ViewGroupDemoActivity extends AppCompatActivity {

    private static String TAG = "ViewGroupDemoActivity";
    private Button btn;
    private MyViewGroup myViewGroup;

    private SeekBar seekUIcpview;
    private CircularProgressView cUIcpview;
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroupdemo);

        initMyCustomViews();
        initCircularView();
    }

    private void initMyCustomViews() {
        btn = (Button) findViewById(R.id.btncvg);
        myViewGroup = (MyViewGroup) findViewById(R.id.cvg_custemViewGroup);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myViewGroup.requestFocus();
            }
        });
    }

    private void initCircularView() {

        seekUIcpview  = (SeekBar) findViewById(R.id.seekUIcpview);
        cUIcpview  = (CircularProgressView) findViewById(R.id.cUIcpview);

        seekUIcpview.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double Cprogress = (double) progress/(double) 100;
                Log.d(TAG,"= seekavr - progress : "+Cprogress+" =");
                cUIcpview.setProgress(Cprogress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


}