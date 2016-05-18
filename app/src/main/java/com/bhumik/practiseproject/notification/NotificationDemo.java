package com.bhumik.practiseproject.notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bhumik.practiseproject.R;

public class NotificationDemo extends AppCompatActivity implements View.OnClickListener {

    NotificationHandler nHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);
        nHandler = NotificationHandler.getInstance(NotificationDemo.this, NotificationDemo.class);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.btnnoti_simple_notification).setOnClickListener(this);
        findViewById(R.id.btnnoti_big_notification).setOnClickListener(this);
        findViewById(R.id.btnnoti_progress_notification).setOnClickListener(this);
        findViewById(R.id.btnnoti_button_notifcation).setOnClickListener(this);

        findViewById(R.id.btnnoti_1).setOnClickListener(this);
        findViewById(R.id.btnnoti_2).setOnClickListener(this);
        findViewById(R.id.btnnoti_3).setOnClickListener(this);
        findViewById(R.id.btnnoti_4).setOnClickListener(this);
        findViewById(R.id.btnnoti_5).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnnoti_simple_notification:
                nHandler.createSimpleNotification(this);
                break;

            case R.id.btnnoti_big_notification:
                nHandler.createExpandableNotification(this);
                break;

            case R.id.btnnoti_progress_notification:
                nHandler.createProgressNotification(this);
                break;

            case R.id.btnnoti_button_notifcation:
                nHandler.createButtonNotification(this);
                break;


            case R.id.btnnoti_1:
                nHandler.showNormalNotification(this);
                break;
            case R.id.btnnoti_2:
                nHandler.showBigTextStyleNotification(this);
                break;
            case R.id.btnnoti_3:
                nHandler.showBigPictureStyleNotification(this);
                break;
            case R.id.btnnoti_4:
                nHandler.showInboxStyleNotification(this);
                break;
            case R.id.btnnoti_5:
                nHandler.showCustomViewNotification(this);
                break;
        }

    }
}
