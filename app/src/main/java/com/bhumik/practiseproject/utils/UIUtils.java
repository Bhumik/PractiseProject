package com.bhumik.practiseproject.utils;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by bhumik on 18/5/16.
 */
public class UIUtils {

    private static final String TAG = "UIUtils";

    /**
     * get the screen size
     *
     * @return the screen size
     */
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    /**
     * @return Inch screen
     */
    public static double getScreenInch(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        double diagonalPixels = Math.sqrt(Math.pow(outMetrics.widthPixels, 2)
                + Math.pow(outMetrics.heightPixels, 2));
        return diagonalPixels / (160 * outMetrics.density);
    }

    /**
     * dp to px
     *
     * @param context  context
     * @param dipValue dp
     * @return Returns the corresponding value px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px to dp
     *
     * @param context context
     * @param pxValue pixelValue
     * @return Returns the corresponding value dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Get the view in the screen position
     *
     * @param view To get the view coordinates
     * @return Returns an array with two elements , the first element is the x coordinate , and the second for the y coordinate
     */
    public static int[] getViewLocationInScreen(View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        return loc;
    }

    /**
     * Get in view of the position of a Window
     *
     * @param view To get the view coordinates
     * @return Returns an array with two elements , the first element is the x coordinate , and the second for the y coordinate
     */
    public static int[] getViewLocationInWindow(View view) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        return loc;
    }

    /**
     * Gets the height of the status bar
     *
     * @param context Context
     * @return status bar height
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static PointF getCorner(View view) {
        float[] src = new float[8];
        float[] dst = new float[]{0, 0, view.getWidth(), 0, 0, view.getHeight(), view.getWidth(), view.getHeight()};
        view.getMatrix().mapPoints(src, dst);
        PointF cornerPoint = new PointF(view.getX() + src[0], view.getY() + src[1]);

        PointF TopLeftCorner = new PointF(view.getX() + src[0], view.getY() + src[1]);
        PointF TopRightCorner = new PointF(view.getX() + src[2], view.getY() + src[3]);
        PointF BottomLeftCorner = new PointF(view.getX() + src[4], view.getY() + src[5]);
        PointF BottomRightCorner = new PointF(view.getX() + src[6], view.getY() + src[7]);

        return cornerPoint;
    }

}
