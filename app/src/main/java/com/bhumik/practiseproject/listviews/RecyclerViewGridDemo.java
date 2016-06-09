package com.bhumik.practiseproject.listviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhumik.practiseproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewGridDemo extends AppCompatActivity {

    private static final String TAG = "RecyclerViewDemo";
    private RecyclerView mRecyclerView;
    private RecyclerGridAdapter recyclerGridAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerdemo);

        setupToolbar();

        setupRecyclerView();

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

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerGridAdapter = new RecyclerGridAdapter(this);
        mRecyclerView.setAdapter(recyclerGridAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
/*
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                recyclerGridAdapter.setLockedAnimations(true);
            }
        });
*/

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                recyclerGridAdapter.increaseItemCountby(7);
            }
        });

    }



    public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int PHOTO_ANIMATION_DELAY = 600;
        private final Interpolator INTERPOLATOR = new DecelerateInterpolator();


        private Context context;
        private int cellSize;

        private boolean lockedAnimations = false;
        private int lastAnimatedItem = -1;

        int items=30;

        public RecyclerGridAdapter(Context context) {
            this.context = context;
            this.cellSize = getScreenWidth(context) / 3;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(context).inflate(R.layout.list_item_recycle_grid_item_photo, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.height = cellSize;
            layoutParams.width = cellSize;
            layoutParams.setFullSpan(false);
            view.setLayoutParams(layoutParams);
            return new PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            bindPhoto((PhotoViewHolder) holder, position);
        }

        private void bindPhoto(final PhotoViewHolder holder, int position) {
            Picasso.with(context)
                    .load("http://loremflickr.com/400/400?random=4455"+position)//                    .load("https://unsplash.it/400/?random")
                    .resize(cellSize, cellSize)
                    .centerCrop()
                    .into(holder.ivPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                            animatePhoto(holder);
                        }

                        @Override
                        public void onError() {

                        }
                    });
            if (lastAnimatedItem < position) lastAnimatedItem = position;
        }

        private void animatePhoto(PhotoViewHolder viewHolder) {
            if (!lockedAnimations) {
                if (lastAnimatedItem == viewHolder.getPosition()) {
                    setLockedAnimations(true);
                }

                long animationDelay = PHOTO_ANIMATION_DELAY + viewHolder.getPosition() * 30;

                viewHolder.flRoot.setScaleY(0);
                viewHolder.flRoot.setScaleX(0);

                viewHolder.flRoot.animate()
                        .scaleY(1)
                        .scaleX(1)
                        .setDuration(200)
                        .setInterpolator(INTERPOLATOR)
                        .setStartDelay(animationDelay)
                        .start();
            }
        }

        @Override
        public int getItemCount() {
            return items;
        }

        public void setItemCount(int items) {
            this.items =items;
            notifyDataSetChanged();
        }
        public void increaseItemCountby(int items) {
            this.items =this.items +items;
            notifyDataSetChanged();
        }


        class PhotoViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.flRoot)
            FrameLayout flRoot;
            @Bind(R.id.ivPhoto)
            ImageView ivPhoto;

            public PhotoViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public void setLockedAnimations(boolean lockedAnimations) {
            this.lockedAnimations = lockedAnimations;
        }
    }

    public void addLog(String msg){
        Log.d(TAG,"== "+msg+"==");

    }
    public static int getScreenWidth(Context c) {

            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
    }



}
