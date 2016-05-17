package com.bhumik.practiseproject.broadcastNservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.bhumik.practiseproject.broadcastNservice.BindServiceDemo.OnProgressListener;


public class BindService extends Service {

    public static final int MAX_PROGRESS = 100;
    private int progress = 0;
    private OnProgressListener onProgressListener;

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    public int getProgress() {
        return progress;
    }

    /*
       Simulation task, updated every second
    */
    public void startDownLoad() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < MAX_PROGRESS) {
                    progress += 5;

                    // Notify the caller schedule changes
                    if (onProgressListener != null) {
                        onProgressListener.onProgress(progress);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        /**
         * Get the current instance of Service
         *
         * @return
         */
        public BindService getService() {
            return BindService.this;
        }
    }
}
