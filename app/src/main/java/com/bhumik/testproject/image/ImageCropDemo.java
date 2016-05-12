package com.bhumik.testproject.image;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.bhumik.testproject.R;

import java.io.File;
import java.io.IOException;

public class ImageCropDemo extends AppCompatActivity {

    private static final String TAG = "ImageCropDemo";
    Button btnImgCrop;
    ImageView imgv_img;
    final int CHOOSE_BIG_PICTURE = 12;
    //static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";//temp file
    //Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgcrop);


        // http://my.oschina.net/ryanhoo/blog/86843 follow link
        imgv_img = (ImageView) findViewById(R.id.imgv_imgcrop);
        btnImgCrop = (Button) findViewById(R.id.btnimgvCrop);

        btnImgCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImage();
            }
        });
    }

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

    void cropImage() {
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

    private Boolean isSDCARDMounted() {
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
                    if(getTempUri() != null){
                        Bitmap bitmap = decodeUriAsBitmap(getTempUri());//decode bitmap
                        if(bitmap !=null)
                            imgv_img.setImageBitmap(bitmap);
                        else
                            Toast.makeText(getApplicationContext(),"bitmap null",Toast.LENGTH_SHORT).show();
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
}
