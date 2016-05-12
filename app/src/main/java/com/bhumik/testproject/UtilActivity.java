package com.bhumik.testproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhumik.testproject.broadcast.BroadcastDemo;
import com.bhumik.testproject.drawer.DrawerActivity;
import com.bhumik.testproject.fab.FABDemo;
import com.bhumik.testproject.image.ImageCropDemo;
import com.bhumik.testproject.loader.LoaderContactDemo;
import com.bhumik.testproject.notification.NotificationDemo;
import com.bhumik.testproject.rxjava.RxJavaDemo;

import java.util.ArrayList;
import java.util.List;

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
