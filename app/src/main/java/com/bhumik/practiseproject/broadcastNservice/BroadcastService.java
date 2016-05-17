package com.bhumik.practiseproject.broadcastNservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BroadcastService extends Service {

    Handler runner;
    Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();

        runner = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss\n"+System.currentTimeMillis());
                String currentDateandTime = sdf.format(new Date());

                Intent intent = new Intent();
                intent.setAction(BroadcastDemo.SERVICE_ACTION_DATA_AVAILABLE);
                intent.putExtra(BroadcastDemo.SERVICE_DATA_RECEIVED, "Date time : " + currentDateandTime);
                sendBroadcast(intent);

                runner.postDelayed(runnable, 500);
            }
        };
        runner.postDelayed(runnable, 200);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        runner.removeCallbacks(runnable);
        super.onDestroy();
    }
}
