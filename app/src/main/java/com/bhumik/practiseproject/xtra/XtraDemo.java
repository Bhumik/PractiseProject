package com.bhumik.practiseproject.xtra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;

import com.bhumik.practiseproject.R;
import com.bhumik.practiseproject.xtra.items.CustomFilterAdapter;

import java.util.ArrayList;

/**
 * Created by bhumik on 17/5/16.
 */
public class XtraDemo extends AppCompatActivity {


    private AutoCompleteTextView mEmailView;
    private ArrayList arraymails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xtra);


        arraymails = new ArrayList();
        arraymails.add("@gmail.com");
        arraymails.add("@hotmail.com");
        arraymails.add("@yahoo.com");
        arraymails.add("@outlook.com");
        arraymails.add("@adinet.com.uy");
        

        mEmailView = (AutoCompleteTextView) findViewById(R.id.xtraAuto);
        CustomFilterAdapter adapter = new CustomFilterAdapter(this,android.R.layout.simple_list_item_1,arraymails);
        mEmailView.setAdapter(adapter);

    }



}
