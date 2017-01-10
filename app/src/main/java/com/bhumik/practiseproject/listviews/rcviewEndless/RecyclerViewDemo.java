package com.bhumik.practiseproject.listviews.rcviewEndless;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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

import com.bhumik.practiseproject.R;

import java.util.ArrayList;

public class RecyclerViewDemo extends AppCompatActivity {

    private static final String TAG = "RecyclerViewDemo";
    private RecyclerView mRecyclerView;
    private RecyclerViewSimpleAdapter mRecyclerViewAdapter;
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
        mRecyclerViewAdapter = new RecyclerViewSimpleAdapter(RecyclerViewDemo.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void setupEndlessGlobal() {
        //LINK  https://gist.github.com/ssinss/e06f12ef66c51252563e
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvview_demo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewSimpleAdapter(RecyclerViewDemo.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mRecyclerViewAdapter.addItems(7);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }


    private void setupEndlessSpecific() {
        //LINK  https://gist.github.com/ssinss/e06f12ef66c51252563e
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvview_demo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewSimpleAdapter(RecyclerViewDemo.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerView.addOnScrollListener(new EndlessSpecificScrollListener(linearLayoutManager) {
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


    private void setupLoadMoreWithLoading() {
        //LINK  https://gist.github.com/ssinss/e06f12ef66c51252563e
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvview_demo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        final LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(RecyclerViewDemo.this);
        mRecyclerView.setAdapter(loadMoreAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if (loadMoreAdapter.isLoading) {
                    return;
                }

                loadMoreAdapter.showLoading();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreAdapter.removeLoading();
                        loadMoreAdapter.addMoreItems(7);
                    }
                }, 3000);
            }
        });
    }


    //TODO -----------------

    // TODO ==================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 11, 0, "Normal");
        menu.add(0, 12, 0, "EndlessSpecific");
        menu.add(0, 13, 0, "EndlessGlobal");
        menu.add(0, 14, 0, "LoadmoreLoading");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 11) {
            setupRecyclerView();
            toolbar.setSubtitle("RecyclerView");
            return true;
        }
        if (item.getItemId() == 12) {
            setupEndlessSpecific();
            toolbar.setSubtitle("Endless Specific");
            return true;
        }
        if (item.getItemId() == 13) {
            setupEndlessGlobal();
            toolbar.setSubtitle("Endless Global");
            return true;
        }
        if (item.getItemId() == 14) {
            setupLoadMoreWithLoading();
            toolbar.setSubtitle("LoadMore With Loading");
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void addLog(String msg) {
        Log.d(TAG, "== " + msg + "==");

    }

    private class RecyclerViewSimpleAdapter extends RecyclerView.Adapter<RecyclerViewSimpleAdapter.mViewHolder> {

        private final LayoutInflater mInflater;
        private ArrayList<Item> itemArrayList=null;

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

    private class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater mInflater;
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        public boolean isLoading = false;
        private ArrayList<Item> itemArrayList = null;

        public LoadMoreAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            itemArrayList = new ArrayList<Item>();
            itemArrayList.addAll(generateRandomItems(20));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == VIEW_TYPE_LOADING) {
                View view = mInflater.inflate(R.layout.list_item_loading, viewGroup, false);
                return new LoadingViewHolder(view);
            } else { //if (viewType == VIEW_TYPE_ITEM) {
                View view = mInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                return new ItemViewHolder(view);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return itemArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Item item = itemArrayList.get(position);
                itemViewHolder.text1.setText("details : " + item.getItemDetails());
            }
        }

        @Override
        public int getItemCount() {
            return itemArrayList.size();
        }

        public void removeLoading() {
            isLoading = false;
            int size = itemArrayList.size();
            if (size > 0 && itemArrayList.get(size - 1) == null) {
                itemArrayList.remove(size - 1);
                // notifyItemRemoved(size);
            }
        }

        public boolean isLoading() {
            return isLoading;
        }

        public void showLoading() {
            isLoading = true;
            itemArrayList.add(null);
            notifyItemInserted(itemArrayList.size() - 1);

        }

        public ArrayList<Item> generateRandomItems(int n) {
            ArrayList<Item> temp = new ArrayList<Item>();
            for (int i = 0; i < n; i++) {
                temp.add(new Item( "itemDetail " + (itemArrayList.size() +i)));
            }
            return temp;
        }

        public void addMoreItems(int No) {
            itemArrayList.addAll(generateRandomItems(No));
            notifyItemRangeInserted(itemArrayList.size(), No);
        }

        public void addItem(Item item) {
            itemArrayList.add(item);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView text1;

            public ItemViewHolder(View itemView) {
                super(itemView);
                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }

        class LoadingViewHolder extends RecyclerView.ViewHolder {
            public LoadingViewHolder(View itemView) {
                super(itemView);
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

    private class LoadMoreAdapterFooter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater mInflater;
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        public boolean isLoading = false;
        RecyclerView recyclerView;
        private ArrayList<Item> itemArrayList = null;

        public LoadMoreAdapterFooter(Context context, RecyclerView recyclerView) {
            this.mInflater = LayoutInflater.from(context);
            this.itemArrayList = new ArrayList<Item>();
            this.itemArrayList.addAll(generateRandomItems(20));
            this.recyclerView = recyclerView;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == VIEW_TYPE_LOADING) {
                View view = mInflater.inflate(R.layout.list_item_loading, viewGroup, false);
                return new LoadingViewHolder(view);
            } else { //if (viewType == VIEW_TYPE_ITEM) {
                View view = mInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                return new ItemViewHolder(view);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return itemArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Item item = itemArrayList.get(position);
                itemViewHolder.text1.setText("details : " + item.getItemDetails());
            }
        }

        @Override
        public int getItemCount() {
            return itemArrayList.size();
        }

        public void removeLoading() {
            isLoading = false;
            int size = itemArrayList.size();
            if (size > 0 && itemArrayList.get(size - 1) == null) {
                itemArrayList.remove(size - 1);
                notifyItemRemoved(size);
            }
        }

        public boolean isLoading() {
            return isLoading;
        }

        public void showLoading() {
            isLoading = true;
            itemArrayList.add(null);
            notifyItemInserted(itemArrayList.size() - 1);

        }

        public ArrayList<Item> generateRandomItems(int n) {
            ArrayList<Item> temp = new ArrayList<Item>();
            for (int i = 0; i < n; i++) {
                temp.add(new Item("itemDetail " + (itemArrayList.size() + i)));
            }
            return temp;
        }

        public void addMoreItems(int No) {
            itemArrayList.addAll(generateRandomItems(No));
            notifyDataSetChanged();
        }

        public void addItem(Item item) {
            itemArrayList.add(item);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView text1;

            public ItemViewHolder(View itemView) {
                super(itemView);
                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }

        class LoadingViewHolder extends RecyclerView.ViewHolder {
            public LoadingViewHolder(View itemView) {
                super(itemView);
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

    //==========

    public abstract class EndlessSpecificScrollListener extends RecyclerView.OnScrollListener {
        public String TAG = EndlessSpecificScrollListener.class.getSimpleName();

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

        public EndlessSpecificScrollListener(LinearLayoutManager layoutManager) {
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
}
