package com.bhumik.practiseproject;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bhumik.practiseproject.utils.DeviceUtils;
import com.bhumik.practiseproject.utils.Utils;

import java.util.Calendar;

public class UtilActivity extends AppCompatActivity {

    Button btnutil_mthread, btnutil_thread;
    TextView txtUtillog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
        txtUtillog = (TextView) findViewById(R.id.txtUtillog);


        checkMainThread();
        setEditClickableDemo();
    }

    private void checkMainThread() {
        btnutil_mthread = (Button) findViewById(R.id.btnutil_mthread);
        btnutil_thread = (Button) findViewById(R.id.btnutil_thread);

        btnutil_mthread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUtillog.setText("isMainThread -isMainThread(" + Utils.isMainThread() + ")");
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
                                txtUtillog.setText("isThread -isMainThread(" + Utils.isMainThread() + ")");
                            }
                        });

                    }
                }).start();

            }
        });
    }

    private void setEditClickableDemo() {
        /*
        * Completely filled EditView link can not be clicked, only to jump by adding an occupying space
        * ( "\ u200B") at the end, before you can click on to the tail.
        */

        EditText mEtEditText = (EditText) findViewById(R.id.mEtEditText);
        mEtEditText.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spannable = new SpannableString("http://www.google.com");
        Linkify.addLinks(spannable, Linkify.WEB_URLS);

        // Add a zero-width space (\ u200B), before you can click on to the final position, otherwise it will trigger link
        CharSequence text = TextUtils.concat(spannable, "\u200B");
        mEtEditText.setText(text);
    }


    public void addCalenderEvent() {
        //< Note: this calendar event intent only in the API 14 and later to support.
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        // intent.setType("vnd.android.cursor.item/event");
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);

        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 10, 30);

        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Bus Time, DEMOBUS");
        calendarIntent.putExtra(CalendarContract.Events.CALENDAR_ID, 123);
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Iscon,Ahmedabad");
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, "sample demo calender event demo notifing bus depart time from location Iscon,Ahmedabad");
        startActivity(calendarIntent);

        //calIntent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
        //calIntent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY
    }

    public long addCalenderEvent2(Context context, long calID) {
        if (!DeviceUtils.checkPermission(context, "android.permission.WRITE_CALENDAR")) {
            return 0;
        }

        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 9, 14, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 9, 14, 8, 45);
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Jazzercise");
        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        return eventID;
    }

    private int deleteEventFromCalendar(long id) {
        ContentResolver cr = getContentResolver();
        Uri eventUri = Uri.parse("content://com.android.calendar/events");  // or
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(eventUri, id);
        int rows = cr.delete(deleteUri, null, null);
        return rows;
    }
}
