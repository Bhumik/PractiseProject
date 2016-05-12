package com.bhumik.testproject.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bhumik.testproject.R;

import rx.Observable;
import rx.Subscriber;

public class RxJavaDemo extends AppCompatActivity {

    Button btnrxhw;
    TextView txtrxlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        btnrxhw = (Button) findViewById(R.id.btnrxhw);
        txtrxlog = (TextView) findViewById(R.id.txtrxlog);


        btnrxhw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                rxHelloWorld();
                
            }
        });
    }

    private void rxHelloWorld() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("The Hello World");
                        subscriber.onCompleted();
                    }
                }
        );

        myObservable.subscribe(mySubscriber);
    }

    Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            txtrxlog.append("\nrxHelloWorld - onCompleted - ");
        }
        @Override
        public void onError(Throwable e) {
            txtrxlog.append("\nrxHelloWorld - onError - ");
        }
        @Override
        public void onNext(String o) {
            txtrxlog.append("\nrxHelloWorld - onNext("+o+")");
        }
    };
}
