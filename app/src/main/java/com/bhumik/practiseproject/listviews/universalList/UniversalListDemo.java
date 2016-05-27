package com.bhumik.practiseproject.listviews.universalList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bhumik.practiseproject.R;
import com.bhumik.practiseproject.listviews.bean.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhumik on 17/5/16.
 * Source : http://blog.csdn.net/tianzhaoai/article/details/50371026
 */
public class UniversalListDemo extends AppCompatActivity {

    private List<Item> mItems;
    private ListView lstv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_universal);
        lstv = (ListView) findViewById(R.id.lstvViewsUniversal);
        mItems = generateRandomItems(50);

        lstv.setAdapter(new CommonAdapter<Item>(UniversalListDemo.this, mItems, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(UniversalViewHolder holder, Item item) {
                holder.setText(android.R.id.text1, item.getItemDetails());
            }
        });

        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(UniversalListDemo.this, "click on the first" + position + "an Item", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public ArrayList<Item> generateRandomItems(int n) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (int i = 0; i < n; i++) {
            temp.add(new Item(i, "item " + i));
        }
        return temp;
    }

}
