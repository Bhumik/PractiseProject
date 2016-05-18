package com.bhumik.practiseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by bhumik on 18/5/16.
 */
public class SoftKeyboardUtil {

    public static void observeSoftKeyBoard(Activity activity, final OnSoftKeyBoardHideListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHight = rect.bottom - rect.top;
                int hight = decorView.getHeight();
                boolean hide = (double) displayHight / hight > 0.8;
                listener.onSoftKeyBoardVisible(!hide);
            }
        });
    }


    public static void showKeyboard(Context context, View view) {
        if (context == null) {
            return;
        }
        context = context.getApplicationContext();
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
//        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideKeyboard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        context = context.getApplicationContext();
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public interface OnSoftKeyBoardHideListener {
        void onSoftKeyBoardVisible(boolean visible);
    }
    public static void hideSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void closeSoftInput(Context context, View focusingView) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusingView.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


}
