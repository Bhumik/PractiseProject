package com.bhumik.testproject.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by bhumik on 16/5/16.
 */
public class MyView extends View {

    private Paint paint = new Paint();

    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set the size of the View 8080
        setMeasuredDimension(50, 50);
    }


    // Canvas objects exist, the default display area exists
    @Override
    public void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        Log.i("MyViewGroup", "MyView IS onDraw");
        // Bold
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        paint.setColor(Color.RED);
        canvas.drawColor(Color.BLUE);
        canvas.drawRect(0, 0, 30, 30, paint);
        canvas.drawText("MyView", 10, 40, paint);
    }
}
