package com.bhumik.practiseproject.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhumik.practiseproject.R;
import com.bhumik.practiseproject.views.bean.Item;

import java.util.ArrayList;

public class RecyclerViewDemo extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerdemo);


        setupToolbar();
        ;

        mRecyclerView = (RecyclerView) findViewById(R.id.rcvview_demo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(RecyclerViewDemo.this));

    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setLogo(R.drawable.drawer_icon);
        toolbar.setLogoDescription("toolbar logo desc");
        toolbar.setSubtitle("SubTitle");
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.CYAN);
        toolbar.setNavigationIcon(R.drawable.wx_smallicon);
        toolbar.setNavigationContentDescription("Nav content Desc");

        toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(toolbar);

    }

    public ArrayList<Item> generateRandomItems(int n) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (int i = 0; i < n; i++) {
            temp.add(new Item(i, "item " + i));
        }
        return temp;
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.mViewHolder> {
        private final LayoutInflater mInflater;

        public RecyclerViewAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
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
            return 50;
        }

        class mViewHolder extends RecyclerView.ViewHolder {
            private TextView text1;

            public mViewHolder(View itemView) {
                super(itemView);
                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }

            public void bind(int position) {
                text1.setText("pos : " + position);
                text1.setTag(position);
            }
        }

    }


}
