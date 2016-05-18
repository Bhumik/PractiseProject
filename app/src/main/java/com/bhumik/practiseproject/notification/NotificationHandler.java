package com.bhumik.practiseproject.notification;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bhumik.practiseproject.R;

import java.util.Random;

public class NotificationHandler {
    // Notification handler singleton
    private static NotificationHandler nHandler;
    private static NotificationManager mNotificationManager;
    private static Class activityClass;


    private NotificationHandler() {
    }


    /**
     * Singleton pattern implementation
     *
     * @return
     */
    public static NotificationHandler getInstance(Context context, Class aClass) {
        if (nHandler == null) {
            nHandler = new NotificationHandler();
            mNotificationManager =
                    (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        activityClass = aClass;

        return nHandler;
    }


    /**
     * Shows a simple notification
     *
     * @param context aplication context
     */
    public void createSimpleNotification(Context context) {
        // Creates an explicit intent for an Activity
        Intent resultIntent = new Intent(context, activityClass);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(activityClass);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.drawer_icon) // notification icon
                .setContentTitle("I'm a simple notification") // main title of the notification
                .setContentText("I'm the text of the simple notification") // notification text
                .setContentIntent(resultPending); // notification intent

        // mId allows you to update the notification later on.
        mNotificationManager.notify(10, mBuilder.build());
    }


    public void createExpandableNotification(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Building the expandable content
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eu aliquet sem. Mauris tincidunt posuere erat vel " +
                    "dignissim. Donec condimentum, ligula id lobortis congue, leo nunc suscipit dui, non molestie ligula risus et velit. " +
                    "Pellentesque consectetur, ante molestie dapibus tincidunt, ipsum ipsum pretium turpis, eget elementum nulla arcu sed leo";
            String[] content = lorem.split("\\.");

            inboxStyle.setBigContentTitle("This is a big title");
            for (String line : content) {
                inboxStyle.addLine(line);
            }

            // Building the notification
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Expandable notification") // title of notification
                    .setContentText("This is an example of an expandable notification") // text inside the notification
                    .setStyle(inboxStyle); // adds the expandable content to the notification

            mNotificationManager.notify(11, nBuilder.build());

        } else {
            Toast.makeText(context, "Can't show", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Show a determinate and undeterminate progress notification
     *
     * @param context, activity context
     */
    public void createProgressNotification(final Context context) {

        // used to update the progress notification
        final int progresID = new Random().nextInt(1000);

        // building the notification
        final NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Progres notification")
                .setContentText("Now waiting")
                .setTicker("Progress notification created")
                .setUsesChronometer(true)
                .setProgress(100, 0, true);


        AsyncTask<Integer, Integer, Integer> downloadTask = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mNotificationManager.notify(progresID, nBuilder.build());
            }

            @Override
            protected Integer doInBackground(Integer... params) {
                try {
                    // Sleeps 2 seconds to show the undeterminated progress
                    Thread.sleep(2000);

                    // update the progress
                    for (int i = 0; i < 101; i += 5) {
                        nBuilder
                                .setContentTitle("Progress running...")
                                .setContentText("Now running...")
                                .setProgress(100, i, false)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentInfo(i + " %");

                        // use the same id for update instead created another one
                        mNotificationManager.notify(progresID, nBuilder.build());
                        Thread.sleep(150);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }


            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                nBuilder.setContentText("Progress finished :D")
                        .setContentTitle("Progress finished !!")
                        .setTicker("Progress finished !!!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setUsesChronometer(false);

                mNotificationManager.notify(progresID, nBuilder.build());
            }
        };

        // Executes the progress task
        downloadTask.execute();
    }


    public void createButtonNotification(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Prepare intent which is triggered if the  notification button is pressed
            Intent intent = new Intent(context, activityClass);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Building the notifcation
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Button notification") // notification title
                    .setContentText("Expand to show the buttons...") // content text
                    .setTicker("Showing button notification") // status bar message
                    .addAction(R.mipmap.ic_launcher, "Accept", pIntent) // accept notification button
                    .addAction(R.mipmap.ic_launcher, "Cancel", pIntent); // cancel notification button

            mNotificationManager.notify(1001, nBuilder.build());

        } else {
            Toast.makeText(context, "You need a higher version", Toast.LENGTH_LONG).show();
        }
    }

    public void showNormalNotification(Context context) {
        Notification noti = setNormalNotification(context);

        noti.defaults |= Notification.DEFAULT_LIGHTS;
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

        mNotificationManager.notify(0, noti);
    }

    public void showBigTextStyleNotification(Context context) {
        Notification noti = setBigTextStyleNotification(context);

        noti.defaults |= Notification.DEFAULT_LIGHTS;
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

        mNotificationManager.notify(0, noti);
    }

    public void showBigPictureStyleNotification(Context context) {
        Notification noti = setBigPictureStyleNotification(context);

        noti.defaults |= Notification.DEFAULT_LIGHTS;
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

        mNotificationManager.notify(0, noti);
    }

    public void showInboxStyleNotification(Context context) {
        Notification noti = setInboxStyleNotification(context);

        noti.defaults |= Notification.DEFAULT_LIGHTS;
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

        mNotificationManager.notify(0, noti);
    }

    public void showCustomViewNotification(Context context) {
        Notification noti = setCustomViewNotification(context);

        noti.defaults |= Notification.DEFAULT_LIGHTS;
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

        mNotificationManager.notify(0, noti);
    }


    private Notification setNormalNotification(Context context) {
        Bitmap remote_picture = null;

        try {
            remote_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.drawer_icon);
//            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setup an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(context, activityClass);

        // TaskStackBuilder ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself).
        stackBuilder.addParentStack(activityClass);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "One", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Two", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Three", resultPendingIntent)
                .setContentTitle("Normal Notification")
                .setContentText("This is an example of a Normal Style.").build();
    }

    private Notification setBigTextStyleNotification(Context context) {
        Bitmap remote_picture = null;

        // Create the style object with BigTextStyle subclass.
        NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle();
        notiStyle.setBigContentTitle("Big Text Expanded");
        notiStyle.setSummaryText("Nice big text.");

        try {
            remote_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.drawer_icon);
//            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the big text to the style.
        CharSequence bigText = "This is an example of a large string to demo how much " +
                "text you can show in a 'Big Text Style' notification.";
        notiStyle.bigText(bigText);

        // Creates an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(context, activityClass);

        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself).
        stackBuilder.addParentStack(activityClass);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "One", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Two", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Three", resultPendingIntent)
                .setContentTitle("Big Text Normal")
                .setContentText("This is an example of a Big Text Style.")
                .setStyle(notiStyle).build();
    }

    private Notification setBigPictureStyleNotification(Context context) {
        Bitmap remote_picture = null;

        // Create the style object with BigPictureStyle subclass.
        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
        notiStyle.setBigContentTitle("Big Picture Expanded");
        notiStyle.setSummaryText("Nice big picture.");

        try {
            remote_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.drawer_icon);
//            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the big picture to the style.
        notiStyle.bigPicture(remote_picture);

        // Creates an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(context, activityClass);

        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself).
        stackBuilder.addParentStack(activityClass);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "One", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Two", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Three", resultPendingIntent)
                .setContentTitle("Big Picture Normal")
                .setContentText("This is an example of a Big Picture Style.")
                .setStyle(notiStyle).build();
    }

    private Notification setInboxStyleNotification(Context context) {
        Bitmap remote_picture = null;

        // Create the style object with InboxStyle subclass.
        NotificationCompat.InboxStyle notiStyle = new NotificationCompat.InboxStyle();
        notiStyle.setBigContentTitle("Inbox Style Expanded");

        // Add the multiple lines to the style.
        // This is strictly for providing an example of multiple lines.
        for (int i = 0; i < 5; i++) {
            notiStyle.addLine("(" + i + " of 6) Line one here.");
        }
        notiStyle.setSummaryText("+2 more Line Samples");

        try {
            remote_picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.drawer_icon);
//            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creates an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(context, activityClass);

        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself).
        stackBuilder.addParentStack(activityClass);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "One", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Two", resultPendingIntent)
                .addAction(android.R.drawable.ic_dialog_alert, "Three", resultPendingIntent)
                .setContentTitle("Inbox Style Normal")
                .setContentText("This is an example of a Inbox Style.")
                .setStyle(notiStyle).build();
    }

    private Notification setCustomViewNotification(Context context) {

        // Creates an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(context, activityClass);

        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(activityClass);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create remote view and set bigContentView.
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_custom_remote);
        expandedView.setTextViewText(R.id.text_view, "Neat logo!");

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle("Custom View").build();

        notification.bigContentView = expandedView;

        return notification;
    }
}