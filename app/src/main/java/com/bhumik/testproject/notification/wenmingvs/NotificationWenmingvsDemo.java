package com.bhumik.testproject.notification.wenmingvs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.bhumik.testproject.R;
import com.bhumik.testproject.notification.NotificationHandler;

import java.util.ArrayList;

public class NotificationWenmingvsDemo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);
        initUI();
    }
    private void initUI () {
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
    public void onClick (View v) {
        switch (v.getId()) {

            case R.id.btnnoti_simple_notification:
                notify_normal_singLine(this);
                break;

            case R.id.btnnoti_big_notification:
                notify_normal_moreLine
                break;

            case R.id.btnnoti_progress_notification:
                notify_mailbox
                break;

            case R.id.btnnoti_button_notifcation:
                notify_bigPic
                break;

            case R.id.btnnoti_1:
                break;
            case R.id.btnnoti_2:
                break;
            case R.id.btnnoti_3:
                break;
            case R.id.btnnoti_4:
                break;
            case R.id.btnnoti_5:
                break;
        }

    }


    /**
     * 高仿淘宝
     */
    private void notify_normal_singLine(Context context) {
        //设置想要展示的数据内容
        int requestCode = 1111;
        Intent intent = new Intent(this, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.mipmap.ic_launcher;
        String ticker = "您有一条新通知";
        String title = "双十一大优惠！！！";
        String content = "仿真皮肤充气娃娃，女朋友带回家！";

        //实例化工具类，并且调用接口
        NotifyUtil notify1 = new NotifyUtil(context, 1);
        notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
//        currentNotify = notify1;
    }
    private void notify_normal_moreLine(Context mContext) {
        //设置想要展示的数据内容
        int requestCode = 112;
        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.drawer_icon;
//        int smallIcon = R.drawable.netease_bigicon;
        String ticker = "您有一条新通知";
        String title = "朱立伦请辞国民党主席 副主席黄敏惠暂代党主席";
        String content = "据台湾“中央社”报道，国民党主席朱立伦今天(18日)向中常会报告，为败选请辞党主席一职，他感谢各位中常委的指教包容，也宣布未来党务工作由副主席黄敏惠暂代，完成未来所有补选工作。";
        //实例化工具类，并且调用接口
        NotifyUtil notify2 = new NotifyUtil(mContext, 2);
        notify2.notify_normail_moreline(pIntent, smallIcon, ticker, title, content, true, true, false);
       // currentNotify = notify2;
    }

    /**
     * 收件箱样式
     */
    private void notify_mailbox(Context mContext) {
        //设置想要展示的数据内容
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
        String ticker = "您有一条新通知";
        String title = "冰冰";
        ArrayList<String> messageList = new ArrayList<String>();
        messageList.add("文明,今晚有空吗？");
        messageList.add("晚上跟我一起去玩吧?");
        messageList.add("怎么不回复我？？我生气了！！");
        messageList.add("我真生气了！！！！！你听见了吗!");
        messageList.add("文明，别不理我！！！");
        String content = "[" + messageList.size() + "条]" + title + ": " + messageList.get(0);
        //实例化工具类，并且调用接口
        NotifyUtil notify3 = new NotifyUtil(mContext, 3);
        notify3.notify_mailbox(pIntent, smallIcon, largeIcon, messageList, ticker,
                title, content, true, true, false);
        //currentNotify = notify3;
    }

    /**
     * 高仿系统截图通知
     */
    private void notify_bigPic(Context mContext) {
        //设置想要展示的数据内容
        int requestCode = 16;

        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.drawer_icon;
        int largePic = R.drawable.drawer_icon;
//        int smallIcon = R.drawable.xc_smaillicon;
  //      int largePic = R.drawable.screenshot;
        String ticker = "您有一条新通知";
        String title = "已经抓取屏幕截图";
        String content = "触摸可查看您的屏幕截图";
        //实例化工具类，并且调用接口
        NotifyUtil notify4 = new NotifyUtil(mContext, 4);
        notify4.notify_bigPic(pIntent, smallIcon, ticker, title, content, largePic, true, true, false);
        //currentNotify = notify4;
    }


    /**
     * 高仿应用宝
     */
    private void notify_customview(Context mContext) {
        int requestCode = 121;

        //设置想要展示的数据内容
        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String ticker = "您有一条新通知";

        //设置自定义布局中按钮的跳转界面
        Intent btnIntent = new Intent(mContext, NotificationWenmingvsDemo.class);
        btnIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //如果是启动activity，那么就用PendingIntent.getActivity，如果是启动服务，那么是getService
        PendingIntent Pintent = PendingIntent.getActivity(mContext,
                (int) SystemClock.uptimeMillis(), btnIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 自定义布局
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification_wenmingvs);
        remoteViews.setImageViewResource(R.id.image, R.drawable.drawer_icon);
//        remoteViews.setImageViewResource(R.id.image, R.drawable.yybao_bigicon);
        remoteViews.setTextViewText(R.id.title, "垃圾安装包太多");
        remoteViews.setTextViewText(R.id.text, "3个无用安装包，清理释放的空间");
        remoteViews.setOnClickPendingIntent(R.id.button, Pintent);//定义按钮点击后的动作
        int smallIcon = R.drawable.drawer_icon;
//        int smallIcon = R.drawable.yybao_smaillicon;
        //实例化工具类，并且调用接口
        NotifyUtil notify5 = new NotifyUtil(mContext, 5);
        notify5.notify_customview(remoteViews, pIntent, smallIcon, ticker, true, true, false);
        //currentNotify = notify5;
    }

    /**
     * 高仿Android更新提醒样式
     */
    private void notify_buttom(Context mContext) {
        //设置想要展示的数据内容
        int requestCode = 17;

        String ticker = "您有一条新通知";
        int smallIcon = R.drawable.drawer_icon;
        int lefticon = R.drawable.drawer_icon;
/*
        int smallIcon = R.drawable.android_bigicon;
        int lefticon = R.drawable.android_leftbutton;
*/
        String lefttext = "以后再说";
        Intent leftIntent = new Intent();
        leftIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent leftPendIntent = PendingIntent.getActivity(mContext,
                requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int righticon = R.drawable.drawer_icon;
//        int righticon = R.drawable.android_rightbutton;
        String righttext = "安装";
        Intent rightIntent = new Intent(mContext, NotificationWenmingvsDemo.class);
        rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(mContext,
                requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //实例化工具类，并且调用接口
        NotifyUtil notify6 = new NotifyUtil(mContext, 6);
        notify6.notify_button(smallIcon, lefticon, lefttext, leftPendIntent, righticon, righttext, rightPendIntent, ticker, "系统更新已下载完毕", "Android 6.0.1", true, true, false);
        //currentNotify = notify6;
    }


    /**
     * 高仿Android系统下载样式
     */
    private void notify_progress(Context mContext) {
        //设置想要展示的数据内容
        int requestCode = 185;

        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        int smallIcon = R.drawable.android_bigicon;
        int smallIcon = R.drawable.drawer_icon;
        String ticker = "您有一条新通知";
        //实例化工具类，并且调用接口
        NotifyUtil notify7 = new NotifyUtil(mContext, 7);
        notify7.notify_progress(rightPendIntent, smallIcon, ticker, "Android 6.0.1 下载", "正在下载中", true, false, false);
       // currentNotify = notify7;
    }

    /**
     * Android 5。0 新特性：悬浮式通知
     */
    private void notify_headUp(Context mContext) {
        int requestCode = 19;

        //设置想要展示的数据内容
        int smallIcon = R.drawable.drawer_icon;
        int largeIcon = R.drawable.drawer_icon;
/*
        int smallIcon = R.drawable.hl_smallicon;
        int largeIcon = R.drawable.fbb_largeicon;
*/
        String ticker = "您有一条新通知";
        String title = "范冰冰";
        String content = "文明，今晚在希尔顿酒店2016号房哈";
        Intent intent = new Intent(mContext, NotificationWenmingvsDemo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        int lefticon = R.drawable.drawer_icon;
//        int lefticon = R.drawable.hl_message;
        String lefttext = "回复";
        Intent leftIntent = new Intent();
        leftIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent leftPendingIntent = PendingIntent.getActivity(mContext,
                requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int righticon = R.drawable.drawer_icon;
//        int righticon = R.drawable.hl_call;
        String righttext = "拨打";
        Intent rightIntent = new Intent(mContext, NotificationWenmingvsDemo.class);
        rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendingIntent = PendingIntent.getActivity(mContext,
                requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //实例化工具类，并且调用接口
        NotifyUtil notify8 = new NotifyUtil(mContext, 8);
        notify8.notify_HeadUp(pendingIntent, smallIcon, largeIcon, ticker, title, content, lefticon, lefttext, leftPendingIntent, righticon, righttext, rightPendingIntent, true, true, false);
        //currentNotify = notify8;
    }
}
