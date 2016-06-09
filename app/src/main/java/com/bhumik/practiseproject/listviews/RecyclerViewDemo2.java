package com.bhumik.practiseproject.listviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhumik.practiseproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RecyclerViewDemo2 extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerdemo);
        setTitle("RecyclerViewDemo2");
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvview_demo);

        initRecyclerView(mRecyclerView);
    }


    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true); // set a fixed size
        initRecyclerLayoutManager(recyclerView); // initialize layout
        initRecyclerAdapter(recyclerView); // initialize adapter
        initItemDecoration(recyclerView); // initialize decorative
        initItemAnimator(recyclerView); // initialize animation
    }


    private void initRecyclerLayoutManager(RecyclerView recyclerView) {
        // staggered grid layout
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initRecyclerAdapter(RecyclerView recyclerView) {
        mAdapter = new MyAdapter(getData());
        recyclerView.setAdapter(mAdapter);
    }
    private void initItemDecoration(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new MyItemDecoration(this));
    }
    /**
     * Initialize RecyclerView (ItemAnimator) project Anime
     * @param recyclerView master control
     */
    private void initItemAnimator(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // default animation
    }



    /**
     * Analog data
     *
     * @return data
     */
    private ArrayList<DataModel> getData() {
        int COUNT = 57;
        ArrayList<DataModel> data = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            DataModel model = new DataModel();

            model.setDateTime(getBeforeDay(new Date(), i));
            model.setLabel("No. " + i);

            data.add(model);
        }

        return data;
    }

    /**
     * Get the date before the day
     *
     * @param date Date
     * @param i    deviate
     * @return new date
     */
    private Date getBeforeDay(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, i * (-1));
        return calendar.getTime();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycleview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.id_action_add:
                mAdapter.addData(1);
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<DataModel> mDataModels;
        private List<Integer> mHeights;

        MyAdapter(List<DataModel> dataModels) {
            if (dataModels == null) {
                throw new IllegalArgumentException("DataModel must not be null");
            }
            mDataModels = dataModels;
            mHeights = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recyclerdemo2_view, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DataModel dataModel = mDataModels.get(position);

            // Random height to simulate waterfalls.
            if (mHeights.size() <= position) {
                mHeights.add((int) (100 + Math.random() * 300));
            }

            ViewGroup.LayoutParams lp = holder.getTvLabel().getLayoutParams();
            lp.height = mHeights.get(position);

            holder.getTvLabel().setLayoutParams(lp);

            holder.getTvLabel().setText(dataModel.getLabel());
            holder.getTvDateTime().setText(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    .format(dataModel.getDateTime()));
        }

        @Override
        public int getItemCount() {
            return mDataModels.size();
        }

        public void addData(int position) {
            DataModel model = new DataModel();
            model.setDateTime(getBeforeDay(new Date(), position));
            model.setLabel("No. " + (int) (new Random().nextDouble() * 20.0f));

            mDataModels.add(position, model);
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            mDataModels.remove(position);
            notifyItemRemoved(position);
        }

        /**
         * Get  date before  day
         *
         * @param date Date
         * @param i    deviate
         * @return new date
         */
        private Date getBeforeDay(Date date, int i) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, i * (-1));
            return calendar.getTime();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvLabel; // tag
        private TextView mTvDateTime; // Date

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvLabel = (TextView) itemView.findViewById(R.id.item_demo2_text);
            mTvDateTime = (TextView) itemView.findViewById(R.id.item_demo2_date);
        }

        public TextView getTvLabel() {
            return mTvLabel;
        }

        public TextView getTvDateTime() {
            return mTvDateTime;
        }

    }

    public class DataModel {

        private String mLabel;
        private Date mDateTime;

        public String getLabel() {
            return mLabel;
        }

        public void setLabel(String label) {
            mLabel = label;
        }

        public Date getDateTime() {
            return mDateTime;
        }

        public void setDateTime(Date dateTime) {
            mDateTime = dateTime;
        }
    }

    public class MyItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public MyItemDecoration(Context context) {
            final TypedArray array = context.obtainStyledAttributes(ATTRS);
            mDivider = array.getDrawable(0);
            array.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }

        // 水平线
        public void drawHorizontal(Canvas c, RecyclerView parent) {

            final int childCount = parent.getChildCount();

            // In  bottom of each sub-control objects
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                final int left = child.getLeft() + child.getPaddingLeft();
                final int right = child.getWidth() + child.getLeft() - child.getPaddingRight();
                final int top = child.getBottom() - mDivider.getIntrinsicHeight() - child.getPaddingBottom();
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        // Vertical line
        public void drawVertical(Canvas c, RecyclerView parent) {

            final int childCount = parent.getChildCount();

            // In each sub-control right to draw lines
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                int right = child.getRight() - child.getPaddingRight();
                int left = right - mDivider.getIntrinsicWidth();
                final int top = child.getTop() + child.getPaddingTop();
                final int bottom = child.getTop() + child.getHeight() - child.getPaddingBottom();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        // Item之间的留白
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }
    }
}
