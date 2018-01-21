package com.orchard.seg.busbump.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.model.BusInfo;
import com.orchard.seg.busbump.viewholder.BusInfoViewHolder;
import com.orchard.seg.busbump.viewholder.DeleteViewHolderListener;

import java.util.List;

public class BusInfoAdapter
        extends RecyclerView.Adapter<BusInfoViewHolder>
        implements DeleteViewHolderListener<BusInfoViewHolder> {

    private static final String TAG = "BusInfoAdapter";

    private List<BusInfo> mDataSet;

    public BusInfoAdapter(List<BusInfo> dataSet) {
        this.mDataSet = dataSet;
    }

    @Override
    public BusInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bus_info, parent, false);
        return new BusInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BusInfoViewHolder holder, int position) {
        BusInfo busInfo = mDataSet.get(position);
        holder.bindView(busInfo);
        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BusInfoAdapter.this.showPopupMenu(holder);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void showPopupMenu(BusInfoViewHolder holder) {
        Log.i(TAG, "");
        holder.showPopupMenu(this);
    }


    @Override
    public void delete(BusInfoViewHolder holder) {
        Log.i(TAG, "Deleting BusInfoViewHolder");

    }
}
