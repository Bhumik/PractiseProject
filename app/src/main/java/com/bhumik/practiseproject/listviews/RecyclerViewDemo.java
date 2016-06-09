package com.bhumik.practiseproject.listviews;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bhumik.practiseproject.R;

import java.util.ArrayList;

public class RecyclerViewDemo extends AppCompatActivity {

    private static final String TAG = "RecyclerViewDemo";
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerdemo);

        setupToolbar();

        setupRecyclerView();

   //     setupEndlessRecyclerView();
    }



    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.recycle_toolbar);
        toolbar.setLogo(android.R.drawable.sym_call_outgoing);
        toolbar.setLogoDescription("toolbar logo desc");
        toolbar.setSubtitle("RecyclerView");
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setTitle("RecyclerView Demo");
        toolbar.setTitleTextColor(Color.CYAN);
        toolbar.setNavigationIcon(android.R.drawable.ic_media_play);
       // toolbar.setNavigationContentDescription("Nav content Desc");

        //inflate menu
       // toolbar.inflateMenu(R.menu.menu_toolbar_recyclerview);

        setSupportActionBar(toolbar);
    }

    public void setupRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvview_demo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAdapter =  new RecyclerViewAdapter(RecyclerViewDemo.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }


    private void setupEndlessRecyclerView() {
        //LINK  https://gist.github.com/ssinss/e06f12ef66c51252563e
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvview_demo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerViewAdapter =  new RecyclerViewAdapter(RecyclerViewDemo.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

/*
        //Deprecated
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // do something...
                mRecyclerViewAdapter.addItems(7);
            }
        });
*/
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mRecyclerViewAdapter.addItems(7);
                mRecyclerViewAdapter.notifyDataSetChanged();

/*                //=== Efficiency Purpose
                int curSize = mRecyclerViewAdapter.getItemCount();
                mRecyclerViewAdapter.addItems(7);
                // for efficiency purposes, only notify the adapter of what elements that got changed
                // curSize will equal to the index of the first element inserted because the list is 0-indexed
                mRecyclerViewAdapter.notifyItemRangeInserted(curSize, 7);*/
            }
        });
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.mViewHolder> {

        private final LayoutInflater mInflater;
        private ArrayList<Item> itemArrayList=null;

        public RecyclerViewAdapter(Context context) {
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

        public ArrayList<Item> generateRandomItems(int n) {
            ArrayList<Item> temp = new ArrayList<Item>();
            for (int i = 0; i < n; i++) {
                temp.add(new Item( "itemDetail " + (itemArrayList.size() +i)));
            }
            return temp;
        }

        public void addItems(int No) {
            itemArrayList.addAll(generateRandomItems(No));
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




    public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        public String TAG = EndlessRecyclerViewScrollListener.class.getSimpleName();

        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 20;
        // The current offset index of data you have loaded
        private int currentPage = 0;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
            this.mLinearLayoutManager = layoutManager;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish.
        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
            int visibleItemCount = view.getChildCount();
            int totalItemCount = mLinearLayoutManager.getItemCount();

            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }
            }
            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                currentPage++;
                onLoadMore(currentPage, totalItemCount);
                loading = true;
            }
        }

        // Defines the process for actually loading more data based on page
        public abstract void onLoadMore(int page, int totalItemsCount);

    }



    //==================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_recyclerview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_isendless:
                item.setChecked(! item.isChecked());
                if( item.isChecked()){
                    setupEndlessRecyclerView();
                    toolbar.setSubtitle("Endless RecyclerView");
                }else{
                    setupRecyclerView();
                    toolbar.setSubtitle("RecyclerView");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //==========

    public void addLog(String msg){
        Log.d(TAG,"== "+msg+"==");

    }
}
