package com.bhumik.practiseproject.listviews.recylerviewhinv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bhumik.practiseproject.R;

import java.util.ArrayList;

//Recycler view horizontal in vertical
public class RecycleViewHinVDemo extends AppCompatActivity {

    private Toolbar toolbar;
    ArrayList<SectionDataModel> allSampleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_recycle_hinv);


        toolbar = (Toolbar) findViewById(R.id.recycleH_toolbar);

        allSampleData = new ArrayList<SectionDataModel>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }

        createDummyData();

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel sectionDataModel = new SectionDataModel();
            sectionDataModel.setHeaderTitle("Section " + i);
            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }
            sectionDataModel.setAllItemsInSection(singleItem);
            allSampleData.add(sectionDataModel);
        }
    }

}
