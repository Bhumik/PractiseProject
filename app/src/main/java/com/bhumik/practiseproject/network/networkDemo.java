package com.bhumik.practiseproject.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bhumik.practiseproject.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class networkDemo extends AppCompatActivity {

    Button btnNetwork1;
    TextView txtNetworkLog;

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        btnNetwork1 = (Button) findViewById(R.id.btnNetwork1);
        txtNetworkLog = (TextView) findViewById(R.id.txtNetworkLog);


        btnNetwork1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLog();
                new HttpAsyncTask().execute("http://hmkcode.appspot.com/rest/controller/get.json");
            }
        });
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void addLog(String msg) {
        txtNetworkLog.append(msg + "\n");
    }

    public void clearLog() {
        txtNetworkLog.setText("");
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            try {
                JSONObject json = new JSONObject(result);
                String str = "";

                JSONArray articles = json.getJSONArray("articleList");
                str += "articles length = " + json.getJSONArray("articleList").length();
                str += "\n--------\n";
                str += "names: " + articles.getJSONObject(0).names();
                str += "\n--------\n";
                str += "url: " + articles.getJSONObject(0).getString("url");

                addLog("HttpAsyncTask \n" + str);
                //etResponse.setText(json.toString(1));

            } catch (JSONException e) {
                addLog("HttpAsyncTask - JSON Exception : \n" + e.getMessage());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public String GET(String url) {
            InputStream inputStream = null;
            String result = "";
            try {
                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();
                // convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return result;
        }

    }


}
