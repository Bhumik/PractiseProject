package com.bhumik.testproject.broadcastNservice;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bhumik.testproject.R;

public class BroadcastDemo extends AppCompatActivity {

    public static String ACTIVITY_ACTION_DATA_AVAILABLE = "com.bhumik.testproject.broadcast.ACTIVITY_ACTION_DATA_AVAILABLE";
    public static String ACTIVITY_DATA_RECEIVED = "com.bhumik.testproject.broadcast.ACTIVITY_DATA_RECEIVED";
    public static String SERVICE_ACTION_DATA_AVAILABLE = "com.bhumik.testproject.broadcast.SERVICE_ACTION_DATA_AVAILABLE";
    public static String SERVICE_DATA_RECEIVED = "com.bhumik.testproject.broadcast.SERVICE_DATA_RECEIVED";
    Button btnBcastSend, btnToggleService;
    TextView txtBcastData;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTIVITY_ACTION_DATA_AVAILABLE.equals(action)) {
                String data = intent.getStringExtra(ACTIVITY_DATA_RECEIVED);
                txtBcastData.setText("Activity (data Received): \n\t" + data);
            }
            if (SERVICE_ACTION_DATA_AVAILABLE.equals(action)) {
                String data = intent.getStringExtra(SERVICE_DATA_RECEIVED);
                txtBcastData.setText("Service (data Received): \n\t" + data);
            }
        }
    };

    public static boolean isBroadcastServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        btnBcastSend = (Button) findViewById(R.id.btnBcastSend);
        btnToggleService = (Button) findViewById(R.id.btnToggleService);
        txtBcastData = (TextView) findViewById(R.id.txtBcastData);

        btnBcastSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(ACTIVITY_ACTION_DATA_AVAILABLE);
                intent.putExtra(ACTIVITY_DATA_RECEIVED, "Time (in Millis): " + System.currentTimeMillis());
                sendBroadcast(intent);
            }
        });

        btnToggleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBroadcastServiceRunning(BroadcastDemo.this,BroadcastService.class)){
                    stopService(new Intent(BroadcastDemo.this,BroadcastService.class));
                    btnToggleService.setText("START Service");;
                }else{
                    startService(new Intent(BroadcastDemo.this,BroadcastService.class));
                    btnToggleService.setText("STOP Service");;
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTIVITY_ACTION_DATA_AVAILABLE);
        intentFilter.addAction(SERVICE_ACTION_DATA_AVAILABLE);
        registerReceiver(broadcastReceiver, intentFilter);

        if(isBroadcastServiceRunning(BroadcastDemo.this,BroadcastService.class)){
            btnToggleService.setText("STOP Service");;
        }else{
            btnToggleService.setText("START Service");;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        if(isBroadcastServiceRunning(BroadcastDemo.this,BroadcastService.class)){
            stopService(new Intent(BroadcastDemo.this,BroadcastService.class));
        }
        super.onDestroy();
    }
}
