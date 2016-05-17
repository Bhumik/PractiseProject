package com.bhumik.practiseproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UtilActivity extends AppCompatActivity {

    Button btnutil_mthread,btnutil_thread;
    TextView txtUtillog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);

        btnutil_mthread  = (Button) findViewById(R.id.btnutil_mthread);
        btnutil_thread  = (Button) findViewById(R.id.btnutil_thread);
        txtUtillog  = (TextView) findViewById(R.id.txtUtillog);

        btnutil_mthread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUtillog.setText("isMainThread -isMainThread("+Utils.isMainThread()+")");
            }
        });
        btnutil_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isMainthres = Utils.isMainThread();
                        final boolean pass = isMainthres;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtUtillog.setText("isThread -isMainThread("+Utils.isMainThread()+")");
                            }
                        });

                    }
                }).start();

            }
        });
    }
}
