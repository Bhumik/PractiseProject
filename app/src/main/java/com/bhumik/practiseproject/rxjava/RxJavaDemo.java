package com.bhumik.practiseproject.rxjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bhumik.practiseproject.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RxJavaDemo extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        mTextView = (TextView) findViewById(R.id.rx_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.rx_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }


    private void rxHelloWorld() {
        mTextView.setText("");
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mTextView.append("\nrxHelloWorld - onCompleted - ");
            }

            @Override
            public void onError(Throwable e) {
                mTextView.append("\nrxHelloWorld - onError - ");
            }

            @Override
            public void onNext(String o) {
                mTextView.append("\nrxHelloWorld - onNext(" + o + ")");
            }
        };

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

    public void observer(View v) {
        //Creating an observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                mTextView.setText(mTextView.getText() + "\nCompleted!!!");
            }

            @Override
            public void onError(Throwable e) {
                mTextView.setText(mTextView.getText() + "\nError:" + e.getMessage() + "!!!");
            }

            @Override
            public void onNext(String s) {
                mTextView.setText(mTextView.getText() + "\nNext:" + s);
            }
        };
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void subscriber(View v) {
        //Create an observer Observer is a built-in excuse RxJava a Observer abstract class , made ​​some extensions
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                // Events not sent before the call, you can do some work equipment
                mTextView.setText(mTextView.getText() + "\nI'm getting ready!!!");
            }

            @Override
            public void onCompleted() {
                mTextView.setText(mTextView.getText() + "\nCompleted!!!");
            }

            @Override
            public void onError(Throwable e) {
                mTextView.setText(mTextView.getText() + "\nError:" + e.getMessage() + "!!!");
            }

            @Override
            public void onNext(String s) {
                mTextView.setText(mTextView.getText() + "\nNext:" + s);
            }

        };
        // subscriber.isUnsubscribed();//Whether Subscribe
        // subscriber.unsubscribe();//unsubscribe
        // Create Shortcut
        Observable observable = Observable.just("111", "222", "3333");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        /*
          public Subscription subscribe(Subscriber subscriber){
            subscriber.onStart();   // Start passing event
            onSubscribe.call(subscriber);   //
            return subscriber; // Back subscriber Easy to unsubscribe
          }
         */

        // Use data to create
        // String[] events= {"one", "two", "three"};
        // Observable observable = Observable.from(events);
    }

    public void subscribe(View v) {
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                mTextView.setText(mTextView.getText() + "\nNext:" + s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                mTextView.setText(mTextView.getText() + "\nError:" + e.getMessage() + "!!!");
            }
        };

        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                mTextView.setText(mTextView.getText() + "\nCompleted!!!");
            }
        };
        Observable.just("999", "888", "777").observeOn(AndroidSchedulers.mainThread()).subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    public void clear(View v) {
        mTextView.setText("");
    }

    // Scheduler Thread Control
    /*
        Schedulers.immediate(); // The current thread , the default
        Schedulers.newThread(); // Start a new thread
        Schedulers.io();    //I/OInternal operating implements a number of unlimited thread pool idle threads can be reused
        Schedulers.computation(); // Scheduler CPU intensive computations used to calculate
        AndroidSchedulers.mainThread(); // Android main thread
    */
    // subscribeOn() - Specifies observer thread
    // observeOn() - Designated observer thread

    public void change1(View v) {
        Observable.just(1, 2, 3, 4, 5, 6)
                .map(new Func1<Integer, String>() { // Change
                    @Override
                    public String call(Integer integer) {
                        return integer.toString();
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mTextView.setText(mTextView.getText() + "\nNext:" + s);
            }
        });
    }

    public void change2(View v) {
        Observable.just("China", "Japen", "England")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.from(new String[]{s, s.length() + ""});
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mTextView.setText(mTextView.getText() + "\nNext:" + s);
            }
        });
    }

    public void throttle(View v) {
        Observable<String> observale = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext("1111");
                    Thread.sleep(300);
                    subscriber.onNext("2222");
                    Thread.sleep(300);
                    subscriber.onNext("3333");
                    Thread.sleep(300);
                    subscriber.onNext("4444");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // An event that does not accept receiving an event under 300ms
        observale.throttleFirst(310, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mTextView.setText(mTextView.getText() + "\nNext:" + s);
            }
        });
    }

    public void lift(View v) {
        // With a Obervale received a Oberbale all events and deal with their own
        Observable.just(1, 2, 3, 4).lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer s) {
                        // Made the interception, and send the original Subscribe, can be seen as a proxy
                        subscriber.onNext(s.toString());
                        mTextView.setText(mTextView.getText() + "\nNext lift:" + s);
                    }
                };
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mTextView.setText(mTextView.getText() + "\nNext subscribe:" + s);
            }
        });
    }

    public void compose(View v) {
        final Observable.Operator<String, Integer> operator1 = new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext(String.valueOf(integer));
                        mTextView.setText(String.format("%s\nOperator1:%d", mTextView.getText(), integer));
                    }
                };
            }
        };
        final Observable.Operator<Integer, String> operator2 = new Observable.Operator<Integer, String>() {
            @Override
            public Subscriber<? super String> call(final Subscriber<? super Integer> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        subscriber.onNext(Integer.valueOf(s));
                        mTextView.setText(String.format("%s\nOperator2:%s", mTextView.getText(), s));
                    }
                };
            }
        };
        // Set acrobatic play , int-> string-> int-> string but do not change
        Observable.just(1, 2, 3, 4).compose(new Observable.Transformer<Integer, String>() {
            @Override
            public Observable<String> call(Observable<Integer> integerObservable) {
                return integerObservable.lift(operator1).lift(operator2).lift(operator1);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mTextView.setText(String.format("%s\nOnNext:%s", mTextView.getText(), s));
            }
        });
    }




}
