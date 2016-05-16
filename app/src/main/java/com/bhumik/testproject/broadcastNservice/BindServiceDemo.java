package com.bhumik.testproject.broadcastNservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhumik.testproject.R;

public class BindServiceDemo extends AppCompatActivity {

    Button btnBcastSend, btnToggleService;
    TextView txtLog;

    private BindService bindService;
    private ProgressBar mProgressBar;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            addLog("conn ... onServiceDisconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // Returns a MsgService objects
            addLog("conn ... onServiceConnected");
            bindService = ((BindService.MsgBinder) service).getService();

            // Register the callback interface to receive changes the download progress
            bindService.setOnProgressListener(new OnProgressListener() {

                @Override
                public void onProgress(int progress) {
                    mProgressBar.setProgress(progress);

                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicebind);

        btnBcastSend = (Button) findViewById(R.id.btnBcastSend);
        btnToggleService = (Button) findViewById(R.id.btnToggleService);
        txtLog = (TextView) findViewById(R.id.txtbindLog);

        mProgressBar = (ProgressBar) findViewById(R.id.pbarbind);

        // Bind Service
        btnToggleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindService != null) {
                    addLog("bindService.startDownLoad()");
                    bindService.startDownLoad();
                } else {
                    Toast.makeText(getApplicationContext(), "bindService is null", Toast.LENGTH_SHORT).show();
                    addLog("bindService is null - cannot startDownLoad()");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        txtLog.setText("");

        Intent intent = new Intent(BindServiceDemo.this, BindService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        addLog("bindService");
    }

    @Override
    protected void onPause() {
        unbindService(conn);
        addLog("unbindService");
        super.onPause();
    }

    private void addLog(String msg) {
        txtLog.append(msg + "\n");
    }

    public interface OnProgressListener {
        void onProgress(int Progress);
    }
}
