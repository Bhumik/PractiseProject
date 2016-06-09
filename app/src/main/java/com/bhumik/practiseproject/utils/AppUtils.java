package com.bhumik.practiseproject.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bhumik on 18/5/16.
 */
public class AppUtils {

    private static String TAG = "AppUtils";

    //APK Utils
    public static boolean isValidAppPackageName(String pkg) {
        String regex = "^[a-zA-Z_]\\w*(\\.[a-zA-Z_]\\w*)*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pkg);
        return m.matches();
    }


    public static boolean isSystemApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
            if (info.publicSourceDir.startsWith("data/dataapp") || info.publicSourceDir.startsWith("/data/dataapp")) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static void uninstall(Context mContext, String packageName) {
        Intent intent;
        Uri uri;
        try {
            uri = Uri.fromParts("package", packageName, null);
            intent = new Intent(Intent.ACTION_DELETE, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void startApplication(final Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(packageName, 0);
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setPackage(pi.packageName);

            List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String className = ri.activityInfo.name;
                intent.setComponent(new ComponentName(packageName, className));
                ctx.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static boolean isValidApk(String apkPath, Context context) {
        boolean bRet = false;
        try {
            if (FileUtil.isFileExist(apkPath)) {
                PackageManager pm = context.getPackageManager();
                PackageInfo pkInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_CONFIGURATIONS);
                bRet = pkInfo != null;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return bRet;
    }

    public static List<PackageInfo> getInstalledPackages(PackageManager pm, int flags) {
        List<PackageInfo> infos = pm.getInstalledPackages(flags);
        return infos;
    }


    public static boolean isApkInstalled(Context context, String packageName) {
        if (packageName == null) {
            return false;
        }
        if (packageName.equals(context.getPackageName())) {
            return true;
        }
        boolean result = false;
        try {
            result = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_DISABLED_COMPONENTS) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean isAppInstalled(Context context, String URI) {
        PackageManager pm = context.getPackageManager();
        Boolean app_installed;
        try {
            pm.getPackageInfo(URI, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException E) {
            app_installed = false;
        }
        return app_installed;
    }


    public static boolean isApkInstalled(Context context, String packageName, String versionCode) {
        if (packageName == null) {
            return false;
        }
        if (packageName.equals(context.getPackageName())) {
            return true;
        }
        boolean result = false;

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_DISABLED_COMPONENTS);
            if (info == null) {
                return false;
            }
            result = String.valueOf(info.versionCode).equals(versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }




    /**
     * Under Backup data / app directory for the Program apk installation files to the SD card root directory
     * @param packageName
     * @param mActivity
     * @throws IOException
     */
    public static void backupApp(String packageName, Activity mActivity) throws IOException {
        // Storage location
        String newFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        String oldFile = null;
        try {
            // Original location
            oldFile = mActivity.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(newFile);
        System.out.println(oldFile);

        File in = new File(oldFile);
        File out = new File(newFile + packageName + ".apk");
        if (!out.exists()) {
            out.createNewFile();
            Log.i(TAG, "File Backup Success！" + "Deposited in" + newFile + "Directory");
        } else {
            Log.i(TAG, "File Backup Success！" + "Deposited in" + newFile + "Directory");
        }

        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);

        int count;
        // File is too large , I feel the need to modify the
        byte[] buffer = new byte[256 * 1024];
        while ((count = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }

        fis.close();
        fos.flush();
        fos.close();
    }


    /**
     * installApk
     *
     * @param context context
     * @param file    APK file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * installApk
     *
     * @param context context
     * @param file    APK file uri
     */
    public static void installApk(Context context, Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * uninstallApk
     *
     * @param context     context
     * @param packageName packageName
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }
}
