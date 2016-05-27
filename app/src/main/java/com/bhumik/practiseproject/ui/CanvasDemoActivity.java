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
public class CanvasDemoActivity extends AppCompatActivity {

    private static String TAG = "ViewGroupDemoActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_canvasdemo);

        initPieView();
        initMyCustomViews();
        initCircularView();

    }

    public void initPieView(){
        ArrayList<PieData> pieDatas = new ArrayList<>();
        PieView cv_pieview = (PieView) findViewById(R.id.cv_pieview);
        PieData pieData0 = new PieData("Millet",2000f);
        PieData pieData1 = new PieData("Huawei",500f);
        PieData pieData2 = new PieData("apple",3000f);
        PieData pieData3 = new PieData("Samsung",300f);
        PieData pieData4 = new PieData("Meizu",100f);
        pieDatas.add(pieData0);
        pieDatas.add(pieData1);
        pieDatas.add(pieData2);
        pieDatas.add(pieData3);
        pieDatas.add(pieData4);

        cv_pieview.setData(pieDatas);
    }

    private void initMyCustomViews() {
        Button btn = (Button) findViewById(R.id.btncvg);
        final MyViewGroup myViewGroup = (MyViewGroup) findViewById(R.id.cvg_custemViewGroup);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myViewGroup.requestFocus();
            }
        });
    }

    private void initCircularView() {

        final SeekBar seekUIcpview = (SeekBar) findViewById(R.id.seekUIcpview);
        final CircularProgressView cUIcpview = (CircularProgressView) findViewById(R.id.cUIcpview);

        seekUIcpview.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double Cprogress = (double) progress / (double) 100;
                Log.d(TAG, "= seekavr - progress : " + Cprogress + " =");
                cUIcpview.setProgress(Cprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }


}