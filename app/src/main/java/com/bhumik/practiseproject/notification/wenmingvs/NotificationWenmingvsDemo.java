package com.bhumik.practiseproject.notification.wenmingvs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.bhumik.practiseproject.R;

import java.util.ArrayList;

public class NotificationWenmingvsDemo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_wenmingvs);
        initUI();
    }
    private void initUI () {
        findViewById(R.id.btnnoti_normal_singLine).setOnClickListener(this);
        findViewById(R.id.btnnoti_normal_moreLine).setOnClickListener(this);
        findViewById(R.id.btnnoti_mailbox).setOnClickListener(this);
        findViewById(R.id.btnnoti_bigPic).setOnClickListener(this);
        findViewById(R.id.btnnoti_customview).setOnClickListener(this);
        findViewById(R.id.btnnoti_buttom).setOnClickListener(this);
        findViewById(R.id.btnnoti_progress).setOnClickListener(this);
        findViewById(R.id.btnnoti_headUp).setOnClickListener(this);

    }


    @Override
    public void onClick (View v) {
        switch (v.getId()) {

            case R.id.btnnoti_normal_singLine:
                notify_normal_singLine(this);
                break;

            case R.id.btnnoti_normal_moreLine:
                notify_normal_moreLine(this);
                break;

            case R.id.btnnoti_mailbox:
                notify_mailbox(this);
                break;

            case R.id.btnnoti_bigPic:
                notify_bigPic(this);
                break;

            case R.id.btnnoti_customview:
                notify_customview(this);
                break;
            case R.id.btnnoti_buttom:
                notify_buttom(this);
                break;
            case R.id.btnnoti_progress:
                notify_progress(this);
                break;
            case R.id.btnnoti_headUp:
                notify_headUp(this);
                break;
        }

    }


    /**
     * High imitation Taobao
     */
    private void notify_normal_singLine(Context context) {
        //Setting data content want to show
        int requestCode = 1111;
        Intent intent = new Intent(this, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.noti_bigicon;
        String ticker = "You have a new notifications";
        String title = " double XI Shipping !!!";
        String content = " skin simulation inflatable doll , girlfriend home ! ";
        //Instantiate tools , and call interface
        NotifyUtil notify1 = new NotifyUtil(context, 1);
        notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
//        currentNotify = notify1;
    }
    private void notify_normal_moreLine(Context mContext) {
        //Setting data content want to show
        int requestCode = 112;
        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.noti_bigicon;
//        int smallIcon = R.drawable.netease_bigicon;
        String ticker = "You have a new notifications";
        String title = " KMT Chairman Huang Ming Chu Vice-President to resign temporarily on behalf of the party chairman ";
        String content = " According to Taiwan's  Central News Agency report, KMT Chairman Chu report today (April 18 ) to the Central Standing Committee , the party chairman to resign defeat , he thanks the Standing Committee teach tolerance, also announced by the Deputy future party work The Chairman Huang Ming so acting , all completed in the next election work . ";
        NotifyUtil notify2 = new NotifyUtil(mContext, 2);
        notify2.notify_normail_moreline(pIntent, smallIcon, ticker, title, content, true, true, false);
       // currentNotify = notify2;
    }

    /**
     * Inbox style
     */
    private void notify_mailbox(Context mContext) {
        //Setting data content want to show
        int requestCode = 15;

        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int largeIcon = R.drawable.drawer_icon;
        int smallIcon = R.drawable.drawer_icon;
/*
        int largeIcon = R.drawable.fbb_largeicon;
        int smallIcon = R.drawable.wx_smallicon;
*/
        String ticker = "You have a new notifications";
        String title = "Ice";
        ArrayList<String> messageList = new ArrayList<String>();
        messageList.add(" civilization , tonight? ");
        messageList.add(" night with me to play with it ? ");
        messageList.add(" how not to respond to me ?? I am angry ! ");
        messageList.add(" I'm really angry !!!!! Do you hear me ! ");
        messageList.add(" civilization , do not ignore me ! ");
        String content = "[" + messageList.size() + "Article]" + title + ": " + messageList.get(0);
        // Instantiate tools , and call interface
        NotifyUtil notify3 = new NotifyUtil(mContext, 3);
        notify3.notify_mailbox(pIntent, smallIcon, largeIcon, messageList, ticker,
                title, content, true, true, false);
        //currentNotify = notify3;
    }

    /**
     * High imitation Screenshot notice
     */
    private void notify_bigPic(Context mContext) {
        //Setting data content want to show
        int requestCode = 16;

        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.drawer_icon;
        int largePic = R.drawable.drawer_icon;
//        int smallIcon = R.drawable.xc_smaillicon;
  //      int largePic = R.drawable.screenshot;
        String ticker = "You have a new notifications";
        String title = " has crawled screenshot ";
        String content = " Touch to view your screenshot .";
        // Instantiate tools , and call interface
        NotifyUtil notify4 = new NotifyUtil(mContext, 4);
        notify4.notify_bigPic(pIntent, smallIcon, ticker, title, content, largePic, true, true, false);
        //currentNotify = notify4;
    }


    /**
     * Application of high imitation treasure
     */
    private void notify_customview(Context mContext) {
        int requestCode = 121;

        //Setting data content want to show
        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String ticker = "You have a new notifications";

        //Setting self -defined interface jump layout buttons
        Intent btnIntent = new Intent(mContext, NotificationWenmingvsDemo.class);
        btnIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //If it is to start activity, then use PendingIntent.getActivity, if it is to start the service , then yes getService
        PendingIntent Pintent = PendingIntent.getActivity(mContext,
                (int) SystemClock.uptimeMillis(), btnIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Custom Layout
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification_wenmingvs);
        remoteViews.setImageViewResource(R.id.image, R.drawable.noti_bigicon);
//        remoteViews.setImageViewResource(R.id.image, R.drawable.yybao_bigicon);
        remoteViews.setTextViewText(R.id.title, " junk package too much");
        remoteViews.setTextViewText(R.id.text, "3 a useless installation package to clean up the release of Space");
        remoteViews.setOnClickPendingIntent(R.id.button, Pintent);//Click the button after the action definition
        int smallIcon = R.drawable.drawer_icon;
//        int smallIcon = R.drawable.yybao_smaillicon;
        //Examples of tools, and call interface
        NotifyUtil notify5 = new NotifyUtil(mContext, 5);
        notify5.notify_customview(remoteViews, pIntent, smallIcon, ticker, true, true, false);
        //currentNotify = notify5;
    }

    /**
     * High style imitation Android update alerts
     */
    private void notify_buttom(Context mContext) {
        //Setting data content want to show
        int requestCode = 17;

        String ticker = "You have a new notifications";
        int smallIcon = R.drawable.noti_bigicon;
        int lefticon = R.drawable.noti_leftbutton;
/*
        int smallIcon = R.drawable.android_bigicon;
        int lefticon = R.drawable.android_leftbutton;
*/
        String lefttext = "talk about it later";
        Intent leftIntent = new Intent();
        leftIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent leftPendIntent = PendingIntent.getActivity(mContext,
                requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int righticon = R.drawable.noti_rightbutton;
//        int righticon = R.drawable.android_rightbutton;
        String righttext = "installation";
        Intent rightIntent = new Intent(mContext, NotificationWenmingvsDemo.class);
        rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(mContext,
                requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Examples of tools, and call interface
        NotifyUtil notify6 = new NotifyUtil(mContext, 6);
        notify6.notify_button(smallIcon, lefticon, lefttext, leftPendIntent, righticon, righttext, rightPendIntent, ticker, "A system update is downloaded", "Android 6.0.1", true, true, false);
        //currentNotify = notify6;
    }


    /**
     * High imitation Android Download style system
     */
    private void notify_progress(Context mContext) {
        //Setting data content want to show
        int requestCode = 185;

        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        int smallIcon = R.drawable.android_bigicon;
        int smallIcon = R.drawable.noti_bigicon;
        String ticker = "You have a new notifications";
        //Examples of tools, and call interface
        NotifyUtil notify7 = new NotifyUtil(mContext, 7);
        notify7.notify_progress(rightPendIntent, smallIcon, ticker, "Android 6.0.1 download", "Downloading", true, false, false);
       // currentNotify = notify7;
    }

    /**
     * Android 5。0 New Feature: suspension notice
     */
    private void notify_headUp(Context mContext) {
        int requestCode = 19;

        //Setting data content want to show
        int smallIcon = R.drawable.drawer_icon;
        int largeIcon = R.drawable.drawer_icon;
/*
        int smallIcon = R.drawable.hl_smallicon;
        int largeIcon = R.drawable.fbb_largeicon;
*/
        String ticker = "You have a new notifications";
        String title = "Fan Bingbing";
        String content = "Civilization , tonight at the Hilton Hotel 2016 rooms ha";
        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        int lefticon = R.drawable.drawer_icon;
//        int lefticon = R.drawable.hl_message;
        String lefttext = "Reply";
        Intent leftIntent = new Intent();
        leftIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent leftPendingIntent = PendingIntent.getActivity(mContext,
                requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int righticon = R.drawable.drawer_icon;
//        int righticon = R.drawable.hl_call;
        String righttext = "Call";
        Intent rightIntent = new Intent(mContext, NotificationWenmingvsDemo.class);
        rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendingIntent = PendingIntent.getActivity(mContext,
                requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Examples of tools, and call interface
        NotifyUtil notify8 = new NotifyUtil(mContext, 8);
        notify8.notify_HeadUp(pendingIntent, smallIcon, largeIcon, ticker, title, content, lefticon, lefttext, leftPendingIntent, righticon, righttext, rightPendingIntent, true, true, false);
        //currentNotify = notify8;
    }
}
