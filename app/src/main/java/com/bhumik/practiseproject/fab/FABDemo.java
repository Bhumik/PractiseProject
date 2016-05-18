package com.bhumik.practiseproject.fab;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhumik.practiseproject.R;

public class FABDemo extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabdemo);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview_fab);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(FABDemo.this));

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
