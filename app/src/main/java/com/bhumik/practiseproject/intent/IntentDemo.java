package com.bhumik.practiseproject.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bhumik.practiseproject.R;
import com.bhumik.practiseproject.intent.bean.Book;
import com.bhumik.practiseproject.intent.bean.Person;

public class IntentDemo extends AppCompatActivity {

    public final static String SER_KEY = "com.bhumik.testproject.intent.ser";
    public final static String PAR_KEY = "com.bhumik.testproject.intent.par";
    Button btnSerializable, btnParcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_passdata);
        btnSerializable = (Button) findViewById(R.id.btnIntentSerializable);
        btnParcelable = (Button) findViewById(R.id.btnIntentParcelable);

        btnSerializable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serializeMethod();
            }
        });

        btnParcelable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pacelableMethod();
            }
        });
    }


    // Serializeable pass object's methods
    public void serializeMethod() {
        Person mPerson = new Person();
        mPerson.setName("Frankie");
        mPerson.setAge(25);

        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SER_KEY, mPerson);

        Intent mIntent = new Intent(this, ObjectTranDemo.class);
        mIntent.putExtra("type", 1);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }

    // Pacelable method of passing objects
    public void pacelableMethod() {
        Book mBook = new Book();
        mBook.setBookName("the Android Tutor");
        mBook.setAuthor("Frankie");
        mBook.setPublishTime(2010);

        Bundle mBundle = new Bundle();
        mBundle.putParcelable(PAR_KEY, mBook);

        Intent mIntent = new Intent(this, ObjectTranDemo.class);
        mIntent.putExtra("type", 2);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }

}
