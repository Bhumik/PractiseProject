package com.bhumik.practiseproject.databind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import android.databinding.DataBindingUtil;
//import com.bhumik.practiseproject.databinding.ActivityDatabindBinding;

/**
 * Created by bhumik on 18/5/16.
 */
public class DataBindDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        ActivityDatabindBinding mBinding; // page binding class
        mBinding = DataBindingUtil.setContentView (this , R.layout.activity_databind); // Bind page
        mBinding.tempTxtView.setVisibility (View.VISIBLE); // use Id
        DataBindData dataBindData = new DataBindData("205");
        mBinding.setDataBindData(dataBindData); // bind data*/
    }
}
