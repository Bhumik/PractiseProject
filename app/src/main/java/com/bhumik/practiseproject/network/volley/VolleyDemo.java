package com.bhumik.practiseproject.network.volley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhumik.practiseproject.R;

import org.json.JSONObject;

/**
 * Created by bhumik on 16/5/16.
 */
public class VolleyDemo extends AppCompatActivity {


    Button btnVolleySendRequest, btnVolleyImgRequest, btnVolleyNetworkImage, btnVolleyRequestJson;
    TextView txtVolleyLog;
    ImageView imgvVolleydemo;
    NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley1);

        initUI();


        btnVolleySendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLog();
                addLog("= sendSimpleRequest =");
                sendSimpleRequest();
            }
        });
        btnVolleyImgRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLog();
                addLog("= Image Request =");
                imageRequest();
                imgvVolleydemo.setVisibility(View.VISIBLE);
            }
        });
        btnVolleyNetworkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLog();
                addLog("= networkImageView =");
                networkImageView.setVisibility(View.VISIBLE);
                networkImageView();
            }
        });
        btnVolleyRequestJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLog();
                addLog("= requestJSON =");
                requestJSON();
            }
        });
    }

    private void initUI() {
        btnVolleySendRequest = (Button) findViewById(R.id.btnVolleySendRequest);
        btnVolleyImgRequest = (Button) findViewById(R.id.btnVolleyImgRequest);
        btnVolleyNetworkImage = (Button) findViewById(R.id.btnVolleyNetworkImage);
        btnVolleyRequestJson = (Button) findViewById(R.id.btnVolleyRequestJson);

        txtVolleyLog = (TextView) findViewById(R.id.txtVolleyLog);
        imgvVolleydemo = (ImageView) findViewById(R.id.imgvVolleydemo);
        networkImageView = (NetworkImageView) findViewById(R.id.imgvVolleynetworkImageView);

        resetVisibility();
    }

    public void resetVisibility() {
        imgvVolleydemo.setVisibility(View.GONE);
        networkImageView.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        addLog("=  Volley.newRequestQueue  =");
    }


    public void addLog(String msg) {
        txtVolleyLog.append(msg + "\n");

    }

    public void clearLog() {
        txtVolleyLog.setText("");
        resetVisibility();
    }

    public void sendSimpleRequest() {
        RequestQueue mQueue = Volley.newRequestQueue(this);

        String url = "http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        addLog("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                addLog("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        mQueue.add(stringRequest);
    }

    public void imageRequest() {
        String url = "http://i.imgur.com/7spzG.png";

        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        addLog("setBitmap (getByteCount) : " + bitmap.getByteCount());
                        imgvVolleydemo.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        addLog("Error :" + error.getMessage());
                        imgvVolleydemo.setImageResource(R.drawable.drawer_icon);
                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    /*
    *  In your layout XML file, you use NetworkImageView in much the same way you would use ImageView
    *
    *  */

    public void networkImageView() {
        String IMAGE_URL = "http://developer.android.com/images/training/system-ui.png";

        ImageLoader mImageLoader = MySingleton.getInstance(this).getImageLoader();

        // Set the URL of the image that should be loaded into this view, and
        // specify the ImageLoader that will be used to make the request.
        networkImageView.setImageUrl(IMAGE_URL, mImageLoader);

    }

    public void requestJSON() {
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        addLog("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }


}
