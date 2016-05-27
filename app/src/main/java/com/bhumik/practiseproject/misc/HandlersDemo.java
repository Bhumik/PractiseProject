package com.bhumik.practiseproject.misc;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bhumik.practiseproject.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by bhumik on 20/5/16.
 */
public class HandlersDemo extends AppCompatActivity {

    Button btn_sendmsg, btn1, btn2, btn3, btn4;
    ImageView imgv_preview;
    private static String image_path = "https://www.dollsofindia.com/images/products/krishna-pictures/radha-krishna-LO68_l.jpg";
    private static int IS_FINISH = 1;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlers);

        btn_sendmsg = (Button) findViewById(R.id.btn_handler_sendmsg);
        imgv_preview = (ImageView) findViewById(R.id.imgv_handler_preview);

        btn1 = (Button) findViewById(R.id.btn_handler_1);
        btn2 = (Button) findViewById(R.id.btn_handler_2);
        btn3 = (Button) findViewById(R.id.btn_handler_3);
        btn4 = (Button) findViewById(R.id.btn_handler_4);


        dialog = new ProgressDialog(this);
        dialog.setTitle("message");
        dialog.setMessage("Loading, please later ... ");
        dialog.setCancelable(true);
        btn_sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyMsgThread()).start();
                dialog.show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = "use Message.Obtain + Hander.sendMessage () to send a message";
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain(handler);
                        msg.what = 2;
                        msg.obj = "use Message.sendToTarget send message";
                        msg.sendToTarget();
                    }
                }).start();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(3);
                    }
                }).start();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        msg.what = 4;
                        msg.obj = "use Message.Obtain + Hander.sendMessage () message transmission delay";
                        handler.sendMessageDelayed(msg,3000);
                    }
                }).start();
            }
        });

    }

    private Handler handler = new Handler() {
        // get the message Handler, the rewrite handleMessage () method
        @Override
        public void handleMessage(Message msg) {
            // determine whether the message code 1
            if (msg.what == IS_FINISH) {
                byte[] data = (byte[]) msg.obj;
                Bitmap BMP = BitmapFactory.decodeByteArray(data, 0, data.length);
                imgv_preview.setImageBitmap(BMP);
                dialog.dismiss();
            }
        }
    };


    private class MyMsgThread implements Runnable {
        @Override
        public void run() {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet HttpGet = new HttpGet(image_path);
            HttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(HttpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    byte[] Data = EntityUtils.toByteArray(httpResponse.getEntity());
                    // Gets a Message object, set what is 1
                    Message msg = Message.obtain();
                    msg.obj = Data;
                    msg.what = IS_FINISH;
                    // send the message to the message queue
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
