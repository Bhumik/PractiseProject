package com.bhumik.practiseproject.notification.wenmingvs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class NotifyUtil {

    private static final int FLAG = Notification.FLAG_INSISTENT;
    int requestCode = (int) SystemClock.uptimeMillis();
    private int NOTIFICATION_ID;
    private NotificationManager nm;
    private Notification notification;
    private NotificationCompat.Builder cBuilder;
    private Notification.Builder nBuilder;
    private Context mContext;


    public NotifyUtil(Context context, int ID) {
        this.NOTIFICATION_ID = ID;
        mContext = context;
        // Being a system service to initialize the object
        nm = (NotificationManager) mContext
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        cBuilder = new NotificationCompat.Builder(mContext);
    }

    /**
     * A variety of information provided in the notification bar at the top
     *
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     */
    private void setCompatBuilder(PendingIntent pendingIntent, int smallIcon, String ticker,
                                  String title, String content, boolean sound, boolean vibrate, boolean lights) {
// // If the current Activity started at the front desk is not open a new Activity.
// Intent.setFlags (Intent.FLAG_ACTIVITY_SINGLE_TOP);
// // When this parameter is set below PendingIntent.FLAG_UPDATE_CURRENT time , so click on the notification bar often no effect , you need to give notification to set a unique requestCode
// // After the Intent encapsulated PendingIntent , click the notification message , it will start the corresponding program
// PendingIntent pIntent = PendingIntent.getActivity (mContext,
// RequestCode, intent, FLAG);

        cBuilder.setContentIntent(pendingIntent); // the notice of Intent to start
        cBuilder.setSmallIcon(smallIcon); // set the status bar at the top of the small icon
        cBuilder.setTicker(ticker); // status bar at the top of the message

        cBuilder.setContentTitle(title); // set the title of Notification Center
        cBuilder.setContentText(content); // set the contents of the notification center
        cBuilder.setWhen(System.currentTimeMillis());

        /*
        * After the AutoCancel set to true, when you click on the notification bar after notification , it will be canceled automatically disappear ,
        * Not set , then click on the news is not clear , but you can delete the slide
        * /
        cBuilder.setAutoCancel(true);
        // The Ongoing set to true then the notification will not be deleted slide
        // notifyBuilder.setOngoing(true);
        /*
         * From Android4.1 start , by the following method to set the priority of notification
		 * The higher the priority , the more forward notice of discharge , low priority and will not display an icon in the status bar at the top of the phone
		 */
        cBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        /*
        * Notification.DEFAULT_ALL: ringtones, flash , shake all the system default .
        * Notification.DEFAULT_SOUND: default ringtone .
        * Notification.DEFAULT_VIBRATE: default vibration.
        * Notification.DEFAULT_LIGHTS: default flash .
        * NotifyBuilder.setDefaults (Notification.DEFAULT_ALL);
		*/
        int defaults = 0;

        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        cBuilder.setDefaults(defaults);
    }

    /**
     * Set builder information when using large text will use this
     *
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     */
    private void setBuilder(PendingIntent pendingIntent, int smallIcon, String ticker, boolean sound, boolean vibrate, boolean lights) {
        nBuilder = new Notification.Builder(mContext);
        // If the current Activity started at the front desk is not open a new Activity。
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pIntent = PendingIntent.getActivity(mContext,
//                requestCode, intent, FLAG);
        nBuilder.setContentIntent(pendingIntent);

        nBuilder.setSmallIcon(smallIcon);


        nBuilder.setTicker(ticker);
        nBuilder.setWhen(System.currentTimeMillis());
        nBuilder.setPriority(Notification.PRIORITY_MAX);
        //NotificationCompat.PRIORITY_MAX

        int defaults = 0;

        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        nBuilder.setDefaults(defaults);
    }

    /**
     * Ordinary notification
     * <p>
     * 1.Sliding disappear , pull down the notifications menu is displayed in the notification menu
     *
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param content
     */
    public void notify_normal_singline(PendingIntent pendingIntent, int smallIcon,
                                       String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {

        setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        sent();
    }

    /**
     * Notification multiple settings ( on millet can not seem to set up large icons , large icons for the default application icon )
     *
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param content
     */
    public void notify_mailbox(PendingIntent pendingIntent, int smallIcon, int largeIcon, ArrayList<String> messageList,
                               String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {

        setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);

        // Set to true then the Ongoing notification can not be deleted slide
        // cBuilder.setOngoing(true);

        /**
         // Deleted
         Intent deleteIntent = new Intent(mContext, DeleteService.class);
         int deleteCode = (int) SystemClock.uptimeMillis();
         // Open a service deleted
         PendingIntent deletePendingIntent = PendingIntent.getService(mContext,
         deleteCode, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
         cBuilder.setDeleteIntent(deletePendingIntent);

         **/

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), largeIcon);
        cBuilder.setLargeIcon(bitmap);

        cBuilder.setDefaults(Notification.DEFAULT_ALL);// Setting default sound
        //cBuilder.setVibrate(new long[]{0, 100, 200, 300});// Set custom vibration
        cBuilder.setAutoCancel(true);
        // builder.setSound(Uri.parse("file:///sdcard/click.mp3"));

        // Set notification style to your inbox style in the notification center two fingers to pull out , more will be able to qualify , but rare
        //cBuilder.setNumber(messageList.size());
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (String msg : messageList) {
            inboxStyle.addLine(msg);
        }
        inboxStyle.setSummaryText("[" + messageList.size() + "Article]" + title);
        cBuilder.setStyle(inboxStyle);
        sent();
    }

    /**
     * Custom notifications views
     *
     * @param remoteViews
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     */
    public void notify_customview(RemoteViews remoteViews, PendingIntent pendingIntent,
                                  int smallIcon, String ticker, boolean sound, boolean vibrate, boolean lights) {

        setCompatBuilder(pendingIntent, smallIcon, ticker, null, null, sound, vibrate, lights);

        notification = cBuilder.build();
        notification.contentView = remoteViews;
        // 发送该通知
        nm.notify(NOTIFICATION_ID, notification);
    }

    /**
     * Notification information may accommodate multiple lines of text prompts ( as supported in the high version of the system , so you want to be judged )
     *
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param content
     */
    public void notify_normail_moreline(PendingIntent pendingIntent, int smallIcon, String ticker,
                                        String title, String content, boolean sound, boolean vibrate, boolean lights) {

        final int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            notify_normal_singline(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
            Toast.makeText(mContext, " Your phone is lower than Android 4.1.2, does not support multi-line notification show !!", Toast.LENGTH_SHORT).show();
        } else {
            setBuilder(pendingIntent, smallIcon, ticker, true, true, false);
            nBuilder.setContentTitle(title);
            nBuilder.setContentText(content);
            nBuilder.setPriority(Notification.PRIORITY_HIGH);
            notification = new Notification.BigTextStyle(nBuilder).bigText(content).build();
            // 发送该通知
            nm.notify(NOTIFICATION_ID, notification);
        }
    }

    /**
     * A progress bar notification , can be set to fuzzy or precise schedule schedule
     *
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param content
     */
    public void notify_progress(PendingIntent pendingIntent, int smallIcon,
                                String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {

        setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        /*
         * Because the progress bar will be updated in real time notification bar said to constantly send new tips , so here is not recommended to enable notification sound .
         * Here it is as an example , to explain to the next principle . You will hear a notification sound after the notification is sent repeatedly .
		 * */

        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr;
                for (incr = 0; incr <= 100; incr += 10) {
                    // Parameters: 1. Maximum progress 2. Current progress 3. Is there an accurate display of progress
                    cBuilder.setProgress(100, incr, false);
                    // cBuilder.setProgress(0, 0, true);
                    sent();
                    try {
                        Thread.sleep(1 * 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Schedule is full, the message is set
                cBuilder.setContentText("Download completed").setProgress(0, 0, false);
                sent();
            }
        }).start();
    }

    /**
     * Notify accommodate big picture
     *
     * @param pendingIntent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param bigPic
     */
    public void notify_bigPic(PendingIntent pendingIntent, int smallIcon, String ticker,
                              String title, String content, int bigPic, boolean sound, boolean vibrate, boolean lights) {

        setCompatBuilder(pendingIntent, smallIcon, ticker, title, null, sound, vibrate, lights);
        NotificationCompat.BigPictureStyle picStyle = new NotificationCompat.BigPictureStyle();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                bigPic, options);
        picStyle.bigPicture(bitmap);
        picStyle.bigLargeIcon(bitmap);
        cBuilder.setContentText(content);
        cBuilder.setStyle(picStyle);
        sent();
    }

    /**
     * There are two buttons notification
     *
     * @param smallIcon
     * @param leftbtnicon
     * @param lefttext
     * @param leftPendIntent
     * @param rightbtnicon
     * @param righttext
     * @param rightPendIntent
     * @param ticker
     * @param title
     * @param content
     */
    public void notify_button(int smallIcon, int leftbtnicon, String lefttext, PendingIntent leftPendIntent, int rightbtnicon, String righttext, PendingIntent rightPendIntent, String ticker,
                              String title, String content, boolean sound, boolean vibrate, boolean lights) {

        requestCode = (int) SystemClock.uptimeMillis();
        setCompatBuilder(rightPendIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        cBuilder.addAction(leftbtnicon,
                lefttext, leftPendIntent);
        cBuilder.addAction(rightbtnicon,
                righttext, rightPendIntent);
        sent();
    }

    public void notify_HeadUp(PendingIntent pendingIntent, int smallIcon, int largeIcon, String ticker, String title, String content, int leftbtnicon, String lefttext, PendingIntent leftPendingIntent, int rightbtnicon, String righttext, PendingIntent rightPendingIntent, boolean sound, boolean vibrate, boolean lights) {

        setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        cBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), largeIcon));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cBuilder.addAction(leftbtnicon,
                    lefttext, leftPendingIntent);
            cBuilder.addAction(rightbtnicon,
                    righttext, rightPendingIntent);
        } else {
            Toast.makeText(mContext, " version lower than Andriod5.0, not experience HeadUp style notice ", Toast.LENGTH_SHORT).show();
        }
        sent();
    }


    /**
     * Send notifications
     */
    private void sent() {
        notification = cBuilder.build();
        // Send the notification
        nm.notify(NOTIFICATION_ID, notification);
    }

    /**
     * According id Clear notification
     */
    public void clear() {
        // Cancellation
        nm.cancelAll();
    }
}