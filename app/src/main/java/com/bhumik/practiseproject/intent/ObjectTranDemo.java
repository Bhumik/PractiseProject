package com.bhumik.practiseproject.intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bhumik.practiseproject.intent.bean.Book;
import com.bhumik.practiseproject.intent.bean.Person;

/**
 * Created by bhumik on 13/5/16.
 */
public class ObjectTranDemo extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView mTextView = new TextView(this);

        int type = getIntent().getIntExtra("type", 1);

        if (type == 1) {
            Person mPerson = (Person) getIntent().getSerializableExtra(IntentDemo.SER_KEY);
            mTextView.setText("IS by You name:" + mPerson.getName() + "/ N" + "Age IS by You:" + mPerson.getAge());
            setContentView(mTextView);
        } else if (type == 2) {
            Book mBook = (Book) getIntent().getParcelableExtra(IntentDemo.PAR_KEY);
            mTextView.setText("IS Book name:" + mBook.getBookName() + "/ N" +
                    "IS the Author:" + mBook.getAuthor() + "/ N" +
                    "PublishTime IS:" + mBook.getPublishTime());
            setContentView(mTextView);

        } else {
            mTextView.setText("Error getting type of demo to show");
            setContentView(mTextView);
        }


    }

}
