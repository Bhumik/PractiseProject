package com.bhumik.testproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Utils {

    public static boolean isMainThread(){
        return (Looper.myLooper() == Looper.getMainLooper() ? true : false);
    }
}
