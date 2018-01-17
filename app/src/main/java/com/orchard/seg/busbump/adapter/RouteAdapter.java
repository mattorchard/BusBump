package com.orchard.seg.busbump.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.model.Route;
import com.orchard.seg.busbump.viewholder.RouteViewHolder;

import java.util.List;

public abstract class RouteAdapter extends RecyclerView.Adapter<RouteViewHolder> {

    private List<Route> mDataSet;

    protected RouteAdapter(List<Route> routes) {
        this.mDataSet = routes;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RouteViewHolder holder, int position) {
        Route route = mDataSet.get(position);
        holder.bindView(route);
        holder.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                onItemClick(holder.getRoute());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDataSet(List<Route> routes) {
        mDataSet.clear();
        mDataSet.addAll(routes);
        this.notifyDataSetChanged();
    }

    abstract protected void onItemClick(Route route);
}
