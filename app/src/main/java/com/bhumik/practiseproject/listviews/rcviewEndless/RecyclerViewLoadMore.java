package com.bhumik.practiseproject.listviews.rcviewEndless;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhumik.practiseproject.R;

import java.util.ArrayList;

public class RecyclerViewLoadMore extends AppCompatActivity {

    private static final String TAG = "RecyclerViewDemo";
    private RecyclerView mRecyclerView;
    private RecyclerViewSimpleAdapter mRecyclerViewAdapter;

    private LinearLayout footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerloadmore);

        footer = (LinearLayout) findViewById(R.id.lvlm_footer);

        setupEndlessGlobal();
    }


    private void setupEndlessGlobal() {
        //LINK  https://gist.github.com/ssinss/e06f12ef66c51252563e
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvviewlm_demo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewSimpleAdapter(RecyclerViewLoadMore.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
                footer.setVisibility(View.GONE);
            }
        }, 3000);

        footer.setVisibility(View.VISIBLE);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                footer.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mRecyclerViewAdapter.addItems(7);
                        mRecyclerViewAdapter.notifyDataSetChanged();

                        footer.setVisibility(View.GONE);
                    }
                }, 3000);
            }
        });
    }

    //TODO -----------------

    public void addLog(String msg) {
        Log.d(TAG, "== " + msg + "==");

    }


    //==========

    private class RecyclerViewSimpleAdapter extends RecyclerView.Adapter<RecyclerViewSimpleAdapter.mViewHolder> {

        private final LayoutInflater mInflater;
        private ArrayList<Item> itemArrayList = null;

        public RecyclerViewSimpleAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            itemArrayList = new ArrayList<Item>();
            itemArrayList.addAll(generateRandomItems(20));
        }

        @Override
        public mViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new mViewHolder(view);
        }

        @Override
        public void onBindViewHolder(mViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return itemArrayList.size();
        }

        public ArrayList<Item> generateRandomItems(int n) {
            ArrayList<Item> temp = new ArrayList<Item>();
            for (int i = 0; i < n; i++) {
                temp.add(new Item("itemDetail " + (itemArrayList.size() + i)));
            }
            return temp;
        }

        public void addItems(int No) {
            itemArrayList.addAll(generateRandomItems(No));
        }

        class mViewHolder extends RecyclerView.ViewHolder {
            private TextView text1;

            public mViewHolder(View itemView) {
                super(itemView);
                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }

            public void bind(int position) {
                Item item = itemArrayList.get(position);
                text1.setText("details : " + item.getItemDetails());
                text1.setTag(position);
            }
        }

        public class Item {
            private String itemDetails;

            public Item(String itemDetails) {
                this.itemDetails = itemDetails;
            }

            public String getItemDetails() {
                return itemDetails;
            }
        }


    }
}
