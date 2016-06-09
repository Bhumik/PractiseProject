package com.bhumik.practiseproject.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;

/**
 * Created by bhumik on 18/5/16.
 */
public class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    /**
     * Get device dpi
     *
     * @return Device dpi
     */
    public static int getDeviceDpi(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.density * 160f);
    }

    /**
     * @param context Context
     * @return Returns the version of the application name
     */
    public static String getAppVersonName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    /**
     * Get version number Code
     *
     * @param context Context
     * @return version number Code
     */
    public static String getAppVersonCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return "" + info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * Get device id
     *
     * @param context
     * @return
     */
    private static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * Get Android id
     *
     * @param context
     * @return
     */
    private static String getAndroidId(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }


    /**
     * check google play service is avaliable
     *
     * @param context Context
     * @return
     */
    public static boolean isGooglePlayServiceAvaliable(Context context) {
        boolean isInstall = false;
        try {
            Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
            isInstall = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInstall && getGooglePlayServiceVersion(context) >= 4;
    }

    private static int getGooglePlayServiceVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * <p>True if the device is connected or connection to network.</p>
     * Need permission: <code>android.permission.ACCESS_NETWORK_STATE</code>
     *
     * @param context
     * @return If you currently have a network connection returns true if the state of network access or no network connection returns false
     */
    private static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                return ni.isConnectedOrConnecting();
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }


    /**
     * Determines whether Sd Card readable and writable
     *
     * @return If Sdcard both read write return true or false otherwise
     */
    public static boolean isSdCardWritable() {
        return ((android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) ? true : false);
    }


    /**
     * Read device's MAC address
     *
     * @param context
     * @return MAC Address
     */
    public static String getMac(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                Log.w(TAG,
                        "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            }
        } catch (Exception e) {
            Log.w(TAG, "Could not get mac address." + e.toString());
        }
        return "";
    }


    /**
     * Check if current application has the specified permission
     *
     * @param context
     * @param permission name of permission <code>android.permission.INTERNET</code>
     * @return Only when the current authority of the host application with parameters corresponding to the permission to return true or false otherwise
     */
    public static boolean checkPermission(Context context, String permission) {
        if (context == null) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission(permission, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    /**
     * Check whether there is enough space sdcard
     *
     * @return true if There is otherwise no
     */
    public static boolean enoughSpaceOnSdCard(long updateSize) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return (updateSize < getRealSizeOnSdcard());
    }

    /**
     * Get sdcard available space
     */
    public static long getRealSizeOnSdcard() {
        File path = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Phone Check whether there is enough space
     *
     * @return true if There is otherwise no
     */
    public static boolean enoughSpaceOnPhone(long updateSize) {
        return getRealSizeOnPhone() > updateSize;
    }

    /**
     * Get Phone free space
     */
    public static long getRealSizeOnPhone() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long realSize = blockSize * availableBlocks;
        return realSize;
    }


    /**
     * isLandscape
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * isPortrait
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


    public static int getScreenBrightnessModeState(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }
    /**
     * Brightness acquisition system requires permission WRITE_SETTINGS
     *
     * @param context context
     * @return Brightness , range is 0-255 ; the default 255
     */
    public static int getScreenBrightness(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 255);
    }
    public static boolean setScreenBrightness(Context context,
                                              int screenBrightness) {
        int brightness = screenBrightness;
        if (screenBrightness < 1) {
            brightness = 1;
        } else if (screenBrightness > 255) {
            brightness = screenBrightness % 255;
            if (brightness == 0) {
                brightness = 255;
            }
        }
        boolean result = Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, brightness);
        return result;
    }


    /**
     * Get screen sleep time , you need permission WRITE_SETTINGS
     * @param context context
     * @return Screen sleep time , in milliseconds , default 30,000
     */
    public static int getScreenSleepTime(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, 30000);
    }
    /**
     * Sleep Time setting screen , you need permission WRITE_SETTINGS
     * @param context context
     * @return Setup is successful
     */
    public static boolean setScreenSleepTime(Context context, int millis) {
        return Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, millis);
    }


    /**
     * Get flight mode state, you need permission WRITE_APN_SETTINGS
     *
     * @param context context
     * @return 1 : Open ; 0: Off ; Default : Off
     */
    @SuppressWarnings("deprecation")
    public static int getAirplaneModeState(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0);
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0);
        }
    }

    /**
     *Analyzing the flight mode is turned on , you need permission WRITE_APN_SETTINGS
     *
     * @param context context
     * @return rue: open ; false: Close ; off by default
     */
    public static boolean isAirplaneModeOpen(Context context) {
        return getAirplaneModeState(context) == 1;
    }

    /**
     * Flight mode set state authority needs WRITE_APN_SETTINGS
     *
     * @param context context
     * @param enable Flight mode status
     * @return Setup is successful
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressWarnings("deprecation")
    public static boolean setAirplaneMode(Context context, boolean enable) {
        boolean result = true;
        // If the current state and the state to set the flight mode is not the same
        if (isAirplaneModeOpen(context) != enable) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                result = Settings.System.putInt(context.getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
            } else {
                result = Settings.Global.putInt(context.getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, enable ? 1 : 0);
            }
            // Send flight mode has been changed broadcasting
            context.sendBroadcast(new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        }
        return result;
    }
}
