package com.orchard.seg.busbump.viewholder;

import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.dialog.DirectionColor;
import com.orchard.seg.busbump.model.Route;

import java.util.Locale;

public class RouteViewHolder extends RecyclerView.ViewHolder {

    private final View mRootView;
    private final TextView mRouteDescriptionTv;
    private final CardView mCardView;
    private Route mRoute;

    public RouteViewHolder(View itemView) {
        super(itemView);
        this.mRootView = itemView;
        this.mRouteDescriptionTv =
                (TextView) mRootView.findViewById(R.id.tv_item_route_description);
        this.mCardView =
                (CardView) mRootView.findViewById(R.id.card_view);
    }

    public void bindView(Route route) {
        mRoute = route;
        mRouteDescriptionTv.setText(String.format(Locale.getDefault(),
                "%d %s",
                route.getRouteNo(),
                route.getRouteHeading()));
        int colorId = DirectionColor.getColor(route.getDirection());
        mCardView.setCardBackgroundColor(Resources.getSystem().getColor(colorId));
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mRootView.setOnClickListener(onClickListener);
    }

    public Route getRoute() {
        return mRoute;
    }
}
