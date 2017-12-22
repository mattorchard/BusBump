package com.orchard.seg.busbump.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.adapter.BusInfoAdapter;
import com.orchard.seg.busbump.model.BusInfo;
import com.orchard.seg.busbump.viewholder.BusInfoViewHolder;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<BusInfo> mDataSet;

    private RecyclerView mBusInfoRv;
    private RecyclerView.LayoutManager mBusInfoLayoutManager;
    private RecyclerView.Adapter<BusInfoViewHolder> mBusInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Todo: Replace placeholder data with call to ORM for placeholder data.
        mDataSet = getSampleDataSet();

        mBusInfoRv = (RecyclerView) findViewById(R.id.rv_main_bus_list);
        mBusInfoLayoutManager = new LinearLayoutManager(MainActivity.this);
        mBusInfoRv.setLayoutManager(mBusInfoLayoutManager);
        mBusInfoAdapter = new BusInfoAdapter(mDataSet);
        mBusInfoRv.setAdapter(mBusInfoAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Todo: Remove this method and replace with ORM.
    private List<BusInfo> getSampleDataSet() {
        List<BusInfo> dataSet = new LinkedList<>();
        dataSet.add(new BusInfo(7613, 16));
        dataSet.add(new BusInfo(7610, 16));
        dataSet.add(new BusInfo(3020, 97, 1));
        return dataSet;
    }
}
