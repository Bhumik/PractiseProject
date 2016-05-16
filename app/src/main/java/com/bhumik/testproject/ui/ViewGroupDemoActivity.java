package com.bhumik.testproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bhumik.testproject.R;

/**
 * Created by bhumik on 16/5/16.
 */
public class ViewGroupDemoActivity extends AppCompatActivity {

    private static String TAG = "ViewGroupDemoActivity";
    private Button btn;
    private MyViewGroup myViewGroup;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroupdemo);

        //setContentView(new MyViewGroup(MainActivity.this));

        btn = (Button) findViewById(R.id.btncvg);
        myViewGroup = (MyViewGroup) findViewById(R.id.cvg_custemViewGroup);


        //µã»÷ÎÒ²é¿´»æÖÆÁ÷³Ì
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(txt.getVisibility() == View.VISIBLE)
//					txt.setVisibility(View.INVISIBLE) ;
//				else
//					txt.setVisibility(View.INVISIBLE) ;

                //myViewGroup.invalidate() ;
//				if(myViewGroup.getVisibility() == View.VISIBLE)
//					myViewGroup.setVisibility(View.GONE) ;
//				else
//					myViewGroup.setVisibility(View.VISIBLE) ;
//
                myViewGroup.requestFocus();
            }
        });


    }
}