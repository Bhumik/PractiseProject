package com.bhumik.practiseproject.image;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bhumik.practiseproject.R;
import com.bhumik.practiseproject.utils.DeviceUtils;

import java.io.File;
import java.io.IOException;

public class ImageOps extends AppCompatActivity {

    private static final String TAG = "ImageOps";
    final int CHOOSE_BIG_PICTURE = 12;
    private Button btnImgCrop,btn_mat1,btn_mat2,btn_mat3,btn_mat4;
    private ImageView imgv_img,imgv_before,imgv_after;
    private Paint paint;
    private Bitmap baseBitmap;
    //static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";//temp file
    //Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgops);


        // http://my.oschina.net/ryanhoo/blog/86843 follow link
       /* imgv_img = (ImageView) findViewById(R.id.imgv_imgcrop);
        btnImgCrop = (Button) findViewById(R.id.btnimgvCrop);
        btnImgCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImage();
            }
        });*/



        baseBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imgv_before = (ImageView) findViewById(R.id.imgv_imgops_before);
        imgv_after = (ImageView) findViewById(R.id.imgv_imgops_after);
        btn_mat1 = (Button) findViewById(R.id.btn_imgvops_mat1);
        btn_mat2 = (Button) findViewById(R.id.btn_imgvops_mat2);
        btn_mat3 = (Button) findViewById(R.id.btn_imgvops_mat3);
        btn_mat4 = (Button) findViewById(R.id.btn_imgvops_mat4);
        btn_mat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapScale(2.0f, 4.0f);
            }
        });
        btn_mat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapRotate(180);
            }
        });
        btn_mat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapTranslate(20f, 20f);
            }
        });
        btn_mat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapSkew(0.2f, 0.4f);
            }
        });

        imgv_before.setImageBitmap(baseBitmap);

        paint = new Paint();
        paint.setAntiAlias(true);
    }


    // ============= CropImage ============


    void cropImage() {
            /*
Additional options	----- type of data	description

crop	-----  String	Send Crop signal
aspectX	-----  int	The proportion of X direction
aspectY	-----  int	The proportion of Y direction
outputX	-----  int	Wide cutting area
outputY	-----  int	High crop area
scale	-----  boolean	Whether to keep the proportion of
return-data	-----  boolean	Whether to keep the data returned in a Bitmap
data	-----  Parcelable	Corresponding Bitmap data
circleCrop	-----  String	Circular crop area?
MediaStore.EXTRA_OUTPUT ( "output")	-----  HATE	URI will point to the appropriate file: /// ..., see the sample code
*/

        try {
            // Launch the Selected Picker to the Choose Photo Business Card for
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 600);
            intent.putExtra("outputY", 300);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true); // no face detection
            intent.putExtra("circleCrop", true);//extraaaaa
            startActivityForResult(intent, CHOOSE_BIG_PICTURE);
        } catch (ActivityNotFoundException E) {
            Toast.makeText(this, "photoPickerNotFoundText", Toast.LENGTH_LONG).show();
        }

    }
    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }
    private File getTempFile() {
        if (isSDCARDMounted()) {

            File f = new File(Environment.getExternalStorageDirectory(), "testImage");
            try {
                f.createNewFile();
            } catch (IOException E) {
                // the TODO Auto-generated the catch Block
                Toast.makeText(this, "fileIOIssue", Toast.LENGTH_LONG).show();
            }
            return f;
        } else {
            File f = new File(Environment.getExternalStorageDirectory(), "/sdcard/testImage");
            try {
                f.createNewFile();
            } catch (IOException E) {
                // the TODO Auto-generated the catch Block
                Toast.makeText(this, "fileIOIssue", Toast.LENGTH_LONG).show();
            }

            return f;
        }
    }
    private boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CHOOSE_BIG_PICTURE:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);//it seems to be null
                    if (getTempUri() != null) {
                        Bitmap bitmap = decodeUriAsBitmap(getTempUri());//decode bitmap
                        if (bitmap != null)
                            imgv_img.setImageBitmap(bitmap);
                        else
                            Toast.makeText(getApplicationContext(), "bitmap null", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    private Bitmap decodeUriAsBitmap(Uri tempUri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), tempUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //================ MATRIX ================

    protected void bitmapScale(float x, float y) {
        Bitmap afterBitmap = Bitmap.createBitmap(
                (int) (baseBitmap.getWidth() * x),
                (int) (baseBitmap.getHeight() * y), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        matrix.setScale(x, y);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        imgv_after.setImageBitmap(afterBitmap);
    }
    protected void bitmapSkew(float dx, float dy) {
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth()
                + (int) (baseBitmap.getWidth() * dx), baseBitmap.getHeight()
                + (int) (baseBitmap.getHeight() * dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        matrix.setSkew(dx, dy);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        imgv_after.setImageBitmap(afterBitmap);
    }
    protected void bitmapTranslate(float dx, float dy) {
        Bitmap afterBitmap = Bitmap.createBitmap((int) (baseBitmap.getWidth() * dx), (int) (baseBitmap.getHeight() * dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        matrix.setTranslate(dx, dy);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        imgv_after.setImageBitmap(afterBitmap);
    }
    protected void bitmapRotate(float degrees) {
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(), baseBitmap.getHeight(), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, baseBitmap.getWidth() / 2, baseBitmap.getHeight() / 2);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        imgv_after.setImageBitmap(afterBitmap);
    }

}
