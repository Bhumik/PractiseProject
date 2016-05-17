package com.bhumik.practiseproject.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.bhumik.practiseproject.R;

/**
 * Created by bhumik on 17/5/16.
 */
public class CircularProgressView extends View {

    private int mCircleColor;   //Round progress bar color
    private int mPointWidth;    // Point width
    private int mPointColor;    //Color
    private Bitmap mCenterImage;//Intermediate image
    private double mProgress;   //schedule
    private Paint mPointPaint;  //Point pen
    private Paint mCirclePaint; //Brush ring
    private int mWidth;         //width
    private int mHeight;        //Height
    private Matrix mMatrix;     //Telescopic Photos
    private BitmapShader mBitmapShader; //Image Rendering
    private ShapeDrawable mShapeDrawable;   //Picture Styles
    private RectF mRectf;   // Progress display range
    private Rect mRect;     // Progress display range

    private float mStartAngle = -90f;
    private float mDestAngle;
    private int mViewSize; //view display size


    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressView, defStyle, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CircularProgressView_pointWidth:
                    mPointWidth = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_SP, 16, getResources()
                                            .getDisplayMetrics()));
                    break;
                case R.styleable.CircularProgressView_circleColor:
                    mCircleColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CircularProgressView_pointColor:
                    mPointColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CircularProgressView_centerImage:
                    mCenterImage = BitmapFactory.decodeResource(getResources(),
                            typedArray.getResourceId(attr, 0));
            }
        }
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        // Brush ring
        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(mPointWidth - 8);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        // End brush
        mPointPaint = new Paint();
        mPointPaint.setColor(mPointColor);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStrokeWidth(1);
        mPointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        mViewSize = Math.min(mWidth, mHeight) - mPointWidth;

        // Calculate the size of the intermediate round
        int left = (mWidth - mViewSize) / 2;
        int top = (mHeight - mViewSize) / 2;
        int right = left + mViewSize;
        int bottom = top + mViewSize;
        mRect = new Rect(left, top, right, bottom);
        mRectf = new RectF(mRect);

        // Draw intermediate image
        drawBitmap(canvas);

        // Draw circle
        canvas.drawArc(mRectf, mStartAngle, mDestAngle, false, mCirclePaint);

        //Draw circular end position
        drawCicle(canvas);

    }


    /**
     * Intermediate round draw pictures
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        // Draw intermediate image
        mBitmapShader = new BitmapShader(mCenterImage, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        //Sizing compression ratio compression pictures, so pictures can adapt controls
        int bSize = Math.min(mCenterImage.getWidth(), mCenterImage.getHeight());
        float scale = mViewSize * 1.0f / bSize;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(matrix);

        //Create a circular shape, as the property is set to shape BitmapShader
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(mBitmapShader);
        mShapeDrawable.setBounds(mRect);
        mShapeDrawable.draw(canvas);

    }


    /**
     * Draw circular end position
     *
     * @param canvas
     */
    private void drawCicle(Canvas canvas) {
        // radius
        int arcRadius = mViewSize / 2;

        // Angle of deflection
        double angle = (double) (mStartAngle + mDestAngle);

        // End point coordinates calculated by trigonometric
        float cx = (float) (arcRadius * Math.cos(Math.toRadians(angle)))
                + mWidth / 2;
        float cy = (float) (arcRadius * Math.sin(Math.toRadians(angle)))
                + mHeight / 2;
        canvas.drawCircle(cx, cy, mPointWidth / 2, mPointPaint);
    }

    /**
     * Setting the pace
     */
    public void setProgress(double progress) {
        mProgress = progress;
        new Thread() {
            @Override
            public void run() {
                try {
                    float totleAngle = (float) (360 * mProgress);
                    for (float angle = 0; angle <= totleAngle; angle += 10) {
                        mDestAngle = angle;
                        postInvalidate();
                        Thread.sleep(40);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }


}
