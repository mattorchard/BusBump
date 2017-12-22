package com.orchard.seg.busbump.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.model.BusInfo;

import java.util.Locale;

public class BusInfoViewHolder extends RecyclerView.ViewHolder {

    private TextView mPlaceholderTv;

    public BusInfoViewHolder(View itemView) {
        super(itemView);
        this.mPlaceholderTv = (TextView) itemView.findViewById(R.id.tv_item_bus_info_placeholder);
    }

    public void bindView(BusInfo busInfo) {
        String placeholderText = String.format(Locale.getDefault(),
                "%d|%d|%d",
                busInfo.getStopNumber(),
                busInfo.getBusNumber(),
                busInfo.getDirectionNumber());
        mPlaceholderTv.setText(placeholderText);
    }
}
