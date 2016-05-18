package com.bhumik.practiseproject.utils.xtra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bhumik on 18/5/16.
 */
public class ToastUtil {
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    private final static int WHAT = 100;
    private static View mToastView;
    private static WindowManager mWindowManager;
    private static int sDuration;
    private static View mOldView;

    /**
     * Use this to get the location of the toast.
     */
    private static Toast mToast;
    private static TextView mTextView;
    private static ToastUtil instance = null;
    private static Handler toastHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            cancelOldAlert();
            int id = msg.what;
            if (WHAT == id) {
                cancelCurrentAlert();
            }
        }
    };
    private WindowManager.LayoutParams mLayoutParams;

    /*
    * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="@android:drawable/toast_frame"
    android:gravity="center"
    android:orientation="horizontal" >

    <ImageView
        android:id="@+id/toast_img"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        tools:ignore="ContentDescription" />

    <TextView
        android:id="@+id/toast_text"
        android:layout_width="0dip"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_weight="1"
        android:textAppearance="@android:style/TextAppearance.Small"
        android:textColor="#ffffffff" />

</LinearLayout>
*/

    @SuppressLint("ShowToast")
    private ToastUtil(Context context) {
        mWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);


        //TODO - implement with above commented xml file code
        //  mToastView = LayoutInflater.from(context).inflate(R.layout.toast_view,null);
        //  mTextView = (TextView) mToastView.findViewById(R.id.toast_text);

        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.windowAnimations = android.R.style.Animation_Toast;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mLayoutParams.setTitle("Toast");
        mLayoutParams.gravity = mToast.getGravity();
    }

    private static ToastUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (ToastUtil.class) {
                if (instance == null) {
                    instance = new ToastUtil(context);
                }
            }
        }
        return instance;
    }

    public static ToastUtil makeText(Context context, CharSequence text,
                                     int duration) {
        ToastUtil util = getInstance(context);
        sDuration = duration;
        mToast.setText(text);
        mTextView.setText(text);
        return util;
    }

    public static ToastUtil makeText(Context context, int resId, int duration) {
        ToastUtil util = getInstance(context);
        sDuration = duration;
        mToast.setText(resId);
        mTextView.setText(context.getResources().getString(resId));
        return util;
    }

    private static void cancelOldAlert() {
        if (mOldView != null && mOldView.getParent() != null) {
            mWindowManager.removeView(mOldView);
        }
    }

    public static void cancelCurrentAlert() {
        if (mToastView != null && mToastView.getParent() != null) {
            mWindowManager.removeView(mToastView);
        }
    }

    /**
     * Show the toast with specified time
     */
    public void show() {
        long time = 0;
        switch (sDuration) {
            case LENGTH_SHORT:
                time = 2000;
                break;
            case LENGTH_LONG:
                time = 3500;
                break;
            default:
                time = 2000;
                break;
        }

        // cancel the previous toast
        cancelOldAlert();
        toastHandler.removeMessages(WHAT);
        mLayoutParams.y = mToast.getYOffset();
        mWindowManager.addView(mToastView, mLayoutParams);

        mOldView = mToastView;
        toastHandler.sendEmptyMessageDelayed(WHAT, time);
    }
}