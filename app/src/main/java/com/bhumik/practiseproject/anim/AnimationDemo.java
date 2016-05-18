package com.bhumik.practiseproject.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bhumik.practiseproject.R;

public class AnimationDemo extends AppCompatActivity {

    Button btnAnimAllXml, btnAnimAllCode, btnAnimClass, btnTvcloseAnimClass, btnCustomAnimClass;
    ImageView imgv_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        imgv_anim = (ImageView) findViewById(R.id.imgv_anim);
        btnAnimAllXml = (Button) findViewById(R.id.btnAnimAllXml);
        btnAnimAllCode = (Button) findViewById(R.id.btnAnimAllCode);
        btnAnimClass = (Button) findViewById(R.id.btnAnimClass);
        btnTvcloseAnimClass = (Button) findViewById(R.id.btnTvcloseAnimClass);
        btnCustomAnimClass = (Button) findViewById(R.id.btnCustomAnimClass);

        btnAnimAllXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(AnimationDemo.this, R.anim.animall);
                imgv_anim.startAnimation(animation);

            }
        });
        btnAnimAllCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeginAllAnimation();
            }
        });
        btnAnimClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animationclass animationclass = new Animationclass();
                animationclass.setDuration(800); // the 800ms duration, the higher the frequency the shorter the duration
                animationclass.setRepeatCount(2); // number of repetitions does not include the first
                imgv_anim.startAnimation(animationclass);
            }
        });
        btnTvcloseAnimClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVCloseAni tvCloseAni = new TVCloseAni();
                imgv_anim.startAnimation(tvCloseAni);
            }
        });
        btnCustomAnimClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAni customAni = new CustomAni();
                imgv_anim.startAnimation(customAni);
            }
        });

    }


    void aplhaAnimation() {
        ImageView imgv_anim = (ImageView) findViewById(R.id.imgv_anim);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animall);
        imgv_anim.startAnimation(animation);

        AlphaAnimation alpha = new AlphaAnimation(0, 1); // 0 ----> 1 from transparent to opaque
        alpha.setDuration(3000); // set the duration of the animation
        imgv_anim.startAnimation(alpha); // start animation

    }

    private void BeginAllAnimation() {
        // create an animation set
        AnimationSet aniSet = new AnimationSet(false);

        // Transparency Animation
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(4000);
        aniSet.addAnimation(alpha);

        // Rotation animation
        RotateAnimation rotate = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(4000);
        aniSet.addAnimation(rotate);

        // Zoom animation
        ScaleAnimation scale = new ScaleAnimation(1.5F, 0.5f, 1.5F, 0.5f);
        scale.setDuration(4000);
        aniSet.addAnimation(scale);

        // Displacement animation
        TranslateAnimation translate = new TranslateAnimation(0, 160, 0, 240);
        translate.setDuration(4000);
        aniSet.addAnimation(translate);

        // Animation listening
        aniSet.setAnimationListener(new Animation.AnimationListener() {
            // start animation
            @Override
            public void onAnimationStart(Animation Animation) {

            }

            // End of the animation, usually achieved here page jump logic
            @Override
            public void onAnimationEnd(Animation Animation) {
                // when the movie ends, jump to the main page
                Toast.makeText(AnimationDemo.this, "Animation Completed", Toast.LENGTH_SHORT).show();
                //   startActivity ( new the Intent (GroupAni. The this , AnimationDemo.class));
            }

            // Animation repeats
            @Override
            public void onAnimationRepeat(Animation Animation) {

            }
        });

        // Set the animation to llGroup
        imgv_anim.startAnimation(aniSet);
    }


    public class Animationclass extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            t.getMatrix().setTranslate(
                    (float) Math.sin(interpolatedTime * 50) * 8,
                    (float) Math.sin(interpolatedTime * 50) * 8
            ); // 50 the greater the higher the frequency, the smaller amplitudes smaller 8
            super.applyTransformation(interpolatedTime, t);
        }
    }

    public class TVCloseAni extends Animation {

        private int mCenterWidth, mCenterHeight;

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            // set the default length
            setDuration(4000);
            // held state animation
            setFillAfter(true);
            // set the default interpolator
            // setInterpolator (new BounceInterpolator ()) ; // rebound effect interpolator
            mCenterWidth = width / 2;
            height = mCenterHeight / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            Matrix matrix = t.getMatrix();
            matrix.preScale(1,
                    1 - interpolatedTime,
                    mCenterWidth,
                    mCenterHeight);
        }
    }

    public class CustomAni extends Animation {

        private int mCenterWidth, mCenterHeight;
        private Camera mCamera = new Camera();
        private float mRotateY = 0.0f;

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);

            setDuration(4000);
            // held state animation
            setFillAfter(true);
            // set the default interpolator
            setInterpolator(new BounceInterpolator()); // rebound effect interpolator
            mCenterWidth = width / 2;
            mCenterHeight = height / 2;

        }

        // Set the rotation angle of exposure to the interface
        public void setRotateY(float rotateY) {
            mRotateY = rotateY;
        }

        // From the core definition of animation, during the execution of the animation will continue to callback to this method, and each callback interpolatedTime values ​​are constantly changing (0 ---- 1)
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            Matrix matrix = t.getMatrix();
            mCamera.save();
            // Use Camera settings Y axis rotation angle
            mCamera.rotateY(mRotateY * interpolatedTime);
            // Change the rotation applied to the matrix
            mCamera.getMatrix(matrix);
            mCamera.restore();
            // Set the offset matrix effect before by pre way to change the center of rotation
            matrix.preTranslate(mCenterWidth, mCenterHeight); // the rotation before the start of the displacement animation
            matrix.postTranslate(-mCenterWidth, -mCenterHeight); // after the rotation start displacement animation
        }
    }
}
