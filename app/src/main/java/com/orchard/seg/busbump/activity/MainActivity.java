package com.orchard.seg.busbump.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.orchard.seg.busbump.repository.BusInfoRepository;
import com.orchard.seg.busbump.task.GetArrivals;
import com.orchard.seg.busbump.task.GetRoutes;
import com.orchard.seg.busbump.viewholder.BusInfoViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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
        BusInfoRepository busInfoRepository = new BusInfoRepository(MainActivity.this);
        mDataSet = busInfoRepository.getBusInfoDataSet();

        mBusInfoRv = (RecyclerView) findViewById(R.id.rv_main_bus_list);
        mBusInfoLayoutManager = new LinearLayoutManager(MainActivity.this);
        mBusInfoRv.setLayoutManager(mBusInfoLayoutManager);
        mBusInfoAdapter = new BusInfoAdapter(mDataSet);
        mBusInfoRv.setAdapter(mBusInfoAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testBusInfoTask();
            }
        });
        testArrivalTask();
    }

    //Todo: Should be removed once more of the UI is implemented
    public void testArrivalTask() {
        GetArrivals<MainActivity> getArrivals = new GetArrivals<>(MainActivity.this);
        getArrivals.execute(mDataSet.toArray(new BusInfo[mDataSet.size()]));
    }

    public void testBusInfoTask() {
        GetRoutes<MainActivity> getRoutes = new GetRoutes<>(MainActivity.this);
        getRoutes.execute(3020);
        GetRoutes<MainActivity> getMoreRoutes = new GetRoutes<>(MainActivity.this);
        getMoreRoutes.execute(7613);
    }

    //Todo: Should be removed once BusInfo CRUD is implemented
    @Deprecated
    private void injectSampleData() {
        BusInfoRepository repo = new BusInfoRepository(MainActivity.this);
        repo.injectSampleData();
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
        } else if (id == R.id.action_refresh_all) {
            return true;
        } else if (id == R.id.action_inject_sample_data) {
            injectSampleData();
        }
        return super.onOptionsItemSelected(item);
    }
}
