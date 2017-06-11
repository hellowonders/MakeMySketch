package com.makemysketch.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.makemysketch.app.api.ItemInterface;
import com.makemysketch.app.utils.RecyclerAdapter;

import java.util.List;

public class Display_Inventory extends AppCompatActivity implements ItemInterface {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_inventory);
        new RetrieveImage(this).execute();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void paintingDetails(List<Object> paintingDetailList) {
        List<Object> list = paintingDetailList;
         if (list != null) {
             adapter = new RecyclerAdapter(list);
             recyclerView.setAdapter(adapter);
         }
    }
}