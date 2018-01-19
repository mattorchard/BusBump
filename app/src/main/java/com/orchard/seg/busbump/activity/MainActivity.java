package com.orchard.seg.busbump.activity;

import android.content.Intent;
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
import com.orchard.seg.busbump.dialog.AddBusInfoDialogManager;
import com.orchard.seg.busbump.dialog.OnDialogFinishListener;
import com.orchard.seg.busbump.model.BusInfo;
import com.orchard.seg.busbump.model.Route;
import com.orchard.seg.busbump.repository.BusInfoRepository;
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
                showBusInfoCreationDialog();
            }
        });
    }

    public void showBusInfoCreationDialog() {
        AddBusInfoDialogManager dialogManager =
                new AddBusInfoDialogManager(MainActivity.this,
                        getLayoutInflater());
        dialogManager.setOnDialogFinishListener(new OnDialogFinishListener() {
            @Override
            public void finish(Intent intent) {
                int stopNo = intent.getIntExtra(AddBusInfoDialogManager.INTENT_STOP_NO, -1);
                if (stopNo != -1) {
                    Route route = (Route) intent
                            .getSerializableExtra(AddBusInfoDialogManager.INTENT_ROUTE);
                    addBusInfo(new BusInfo(stopNo,
                            route.getRouteNo(),
                            route.getDirectionId()));
                }
            }
        });
        dialogManager.show();
    }

    public void addBusInfo(BusInfo busInfo) {
        BusInfoRepository repo = new BusInfoRepository(MainActivity.this);
        repo.insertBusInfo(busInfo);
        mDataSet.add(busInfo);
        mBusInfoAdapter.notifyDataSetChanged();
    }

    //Todo: Should be removed once BusInfo CRUD is implemented
    @Deprecated
    private void injectSampleData() {
        BusInfoRepository repo = new BusInfoRepository(MainActivity.this);
        repo.injectSampleData();
    }
    //Todo: Should be removed once BusInfo CRUD is implemented
    @Deprecated
    private void deleteAllData() {
        BusInfoRepository repo = new BusInfoRepository(MainActivity.this);
        repo.deleteAll();
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
        } else if (id == R.id.action_delete_local_data) {
            deleteAllData();
        }
        return super.onOptionsItemSelected(item);
    }
}
