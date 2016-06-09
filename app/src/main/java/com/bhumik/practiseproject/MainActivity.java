package com.bhumik.practiseproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhumik.practiseproject.broadcastNservice.BindServiceDemo;
import com.bhumik.practiseproject.broadcastNservice.BroadcastDemo;
import com.bhumik.practiseproject.databind.DataBindDemo;
import com.bhumik.practiseproject.drawer.DrawerActivity;
import com.bhumik.practiseproject.fab.FABDemo;
import com.bhumik.practiseproject.image.ImageOps;
import com.bhumik.practiseproject.image.PhotoWallLruDemo;
import com.bhumik.practiseproject.intent.IntentDemo;
import com.bhumik.practiseproject.listviews.RecyclerViewGridDemo;
import com.bhumik.practiseproject.listviews.recylerviewhinv.RecycleViewHinVDemo;
import com.bhumik.practiseproject.loader.LoaderContactDemo;
import com.bhumik.practiseproject.misc.HandlersDemo;
import com.bhumik.practiseproject.network.volley.VolleyDemo;
import com.bhumik.practiseproject.notification.NotificationDemo;
import com.bhumik.practiseproject.notification.wenmingvs.NotificationWenmingvsDemo;
import com.bhumik.practiseproject.rxjava.RxJavaDemo;
import com.bhumik.practiseproject.styling.SpannableDemo;
import com.bhumik.practiseproject.ui.CanvasDemoActivity;
import com.bhumik.practiseproject.ui.WhatsUpUI;
import com.bhumik.practiseproject.utils.Utils;
import com.bhumik.practiseproject.listviews.RecyclerViewDemo;
import com.bhumik.practiseproject.listviews.RecyclerViewDemo2;
import com.bhumik.practiseproject.listviews.universalList.UniversalListDemo;
import com.bhumik.practiseproject.xml.XmlParseDemo;
import com.bhumik.practiseproject.xtra.XtraDemo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.setStatusBarTranslucent(MainActivity.this);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);


        ListView lstv = (ListView) findViewById(R.id.lstvactmain);


        CustomAdapter arrayAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        lstv.setAdapter(arrayAdapter);

        arrayAdapter.addItem(BroadcastDemo.class, "Broadcast Send Receive Sample");
        arrayAdapter.addItem(BindServiceDemo.class, "BindServiceDemo");
        arrayAdapter.addItem(DrawerActivity.class, "DrawerActivity ");
        arrayAdapter.addItem(LoaderContactDemo.class, "Loader - contacts");
        arrayAdapter.addItem(RxJavaDemo.class, "RxJavaDemo");
        arrayAdapter.addItem(FABDemo.class, "FABDemo");
        arrayAdapter.addItem(ImageOps.class, "Image Operations - crop - Matrix");
        arrayAdapter.addItem(HandlersDemo.class, "Handlers - message - Demo");
        arrayAdapter.addItem(NotificationDemo.class, "Notification Demo");
        arrayAdapter.addItem(NotificationWenmingvsDemo.class, "NotificationWenmingvsDemo Demo");
        arrayAdapter.addItem(UtilActivity.class, "UtilActivity");
        arrayAdapter.addItem(IntentDemo.class, "IntentDemo - Pass data through Serializeable/Pacelable");
        arrayAdapter.addItem(PhotoWallLruDemo.class, "PhotoWall Lru Demo - similar to android sample");
        arrayAdapter.addItem(VolleyDemo.class, "VolleyDemo");
        arrayAdapter.addItem(XmlParseDemo.class, "XmlParseDemo");
        arrayAdapter.addItem(WhatsUpUI.class, "Whatsup like profile UI Demo");
        arrayAdapter.addItem(SpannableDemo.class, "Styling - Spannable Text Demo");
        arrayAdapter.addItem(RecyclerViewDemo.class, "RecyclerViewDemo");
        arrayAdapter.addItem(RecyclerViewGridDemo.class, "RecyclerViewGridDemo ");
        arrayAdapter.addItem(RecyclerViewDemo2.class, "RecyclerViewDemo 2 waterfall");
        arrayAdapter.addItem(RecycleViewHinVDemo.class, "RecyclerView Hinv - horizontal recylerview in vertical recycleview");
        arrayAdapter.addItem(UniversalListDemo.class, "Listview with Custom Universal Adapter");
        arrayAdapter.addItem(DataBindDemo.class, "DataBind Demo");
        arrayAdapter.addItem(XtraDemo.class, "== XtraDemo ==");
        arrayAdapter.addItem(CanvasDemoActivity.class, "CanvasDemoActivity - https://github.com/Panl/LearnAndroid");

        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 111, 0, "Permission");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 111) {
            askPermission();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void askPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(this, "askPermission", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }

            }
        } else {
            Toast.makeText(this, "askPermission - build version < M", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
            case 2:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted" + requestCode, Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "Permission denied" + requestCode, Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public class CustomAdapter extends ArrayAdapter<Class> {

        private LayoutInflater inflater;
        private List<Class> objects;
        private List<String> details;

        public CustomAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
            inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            objects = new ArrayList<>();
            details = new ArrayList<>();
        }


        public void addItem(Class className, String detail) {
            objects.add(className);
            details.add(detail);
        }

        @Override
        public Class getItem(int position) {
            return objects.get(position);
        }

        public String getItemDetail(int position) {
            return details.get(position);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(getItem(position).getSimpleName() + "\n" + getItemDetail(position));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, getItem(position)));
                }
            });
            return convertView;
        }
    }

    /*
    public class CustomAdapter extends ArrayAdapter<Class> {

        private LayoutInflater inflater;

        public CustomAdapter(Context context, int resource, int textViewResourceId, List<Class> objects) {
            super(context, resource, textViewResourceId, objects);
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(""+getItem(position).getSimpleName());

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,getItem(position)));
                }
            });
            return convertView;
        }
    }

*/


}
