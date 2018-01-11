package com.orchard.seg.busbump.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.model.Route;

import java.util.Locale;

public class RouteViewHolder extends RecyclerView.ViewHolder {

    private final TextView mRouteNoTv;
    private final TextView mDirectionTv;
    private final TextView mHeadingTv;
    private final View mRootView;
    private int mDirectionId;

    public RouteViewHolder(View itemView) {
        super(itemView);
        this.mRootView = itemView;
        this.mRouteNoTv = (TextView) mRootView.findViewById(R.id.tv_item_route_no);
        this.mDirectionTv = (TextView) mRootView.findViewById(R.id.tv_item_route_direction);
        this.mHeadingTv = (TextView) mRootView.findViewById(R.id.tv_item_route_heading);
    }

    public void bindView(Route route) {
        mDirectionId = route.getDirectionId();
        mRouteNoTv.setText(String.format(Locale.getDefault(), "%d", route.getRouteNo()));
        mDirectionTv.setText(route.getDirection());
        mHeadingTv.setText(route.getRouteHeading());
    }

    public int getDirectionId() {
        return mDirectionId;
    }
}
