package com.bhumik.practiseproject.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhumik.practiseproject.R;

/**
 * Created by bhumik on 16/5/16.
 */
public class MyViewGroup extends ViewGroup {


    private static String TAG = "MyViewGroup";
    private Context mContext;

    public MyViewGroup(Context context) {
        super(context);
        mContext = context;
        init();
    }

    // Xml attribute is defined, it is necessary that  constructor  
    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    // Add three sub-View is MyViewGroup  
    private void init() {
        // Call  parent class ViewGroup addView () method adds a child View  

        // Child objects a: Button  
        Button btn = new Button(mContext);
        btn.setText(" I AM  Button");
        this.addView(btn);

        // Child two objects: ImageView   
        ImageView img = new ImageView(mContext);
        img.setBackgroundResource(R.drawable.noti_bigicon);
        this.addView(img);

        // Child objects three: TextView  
        TextView txt = new TextView(mContext);
        txt.setText("Only  Text");
        this.addView(txt);

        // Child objects Four: Custom View  
        MyView myView = new MyView(mContext);
        this.addView(myView);
    }

    @Override
    // Perform measure for each sub-View (): set  size of each sub-View,  actual width and height  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // By init () method, we add three objects for ViewGroup view, Button, ImageView, TextView  
        int childCount = getChildCount();
        Log.i(TAG, "the size of this ViewGroup is ---->" + childCount);

        Log.i(TAG, "***** **** onMeasure Start");

        // Get  actual length and width ViewGroup involves using MeasureSpec class  
        int specSize_Widht = MeasureSpec.getSize(widthMeasureSpec);
        int specSize_Heigth = MeasureSpec.getSize(heightMeasureSpec);

        Log.i(TAG, "**** specSize_Widht" + specSize_Widht + "* specSize_Heigth *****" + specSize_Heigth);

        // Set  width and height of  ViewGroup  
        setMeasuredDimension(specSize_Widht, specSize_Heigth);


        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);    // obtain a reference to each object
            child.measure(50, 50);    // simple settings for each sub-View object width and height of 50px, 50px
            // Or you can call  parent class method ViewGroup measureChild () or measureChildWithMargins () method  
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

    }

    // Layout view for each child View
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        // By init () method, we add three objects for ViewGroup view, Button, ImageView, TextView  
        int childCount = getChildCount();

        int startLeft = 0; // set  start of each sub-View abscissa
        int startTop = 10;  // for each child from  parent view View location, simply set it to 10px. It can be understood as android: margin = 10px;

        Log.i(TAG, "**** onLayout start ****");
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);   //obtain a reference to each object
            child.layout(startLeft, startTop, startLeft + child.getMeasuredWidth(), startTop + child.getMeasuredHeight());
            startLeft = startLeft + child.getMeasuredWidth() + 10;  //spacing // startLeft calibration value, View between set 10px;
            Log.i(TAG, "**** onLayout startLeft ****" + startLeft);
        }

    }

    // Draw procedure Android has a good package for us, here just to observe  method call away
    protected void dispatchDraw(Canvas Canvas) {
        Log.i(TAG, "**** **** dispatchDraw Start");

        super.dispatchDraw(Canvas);
    }

    protected Boolean drawChild(Canvas Canvas, View Child, Long drawingTime) {
        Log.i(TAG, "**** **** drawChild Start");
        return super.drawChild(Canvas, Child, drawingTime);
    }
}
