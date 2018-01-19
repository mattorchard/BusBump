package com.orchard.seg.busbump.viewholder;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.model.BusInfo;

import java.util.Locale;

public class BusInfoViewHolder
        extends RecyclerView.ViewHolder
        implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "BusInfoViewHolder";

    private View mRootView;
    private BusInfo mBusInfo;
    private TextView mPlaceholderTv;

    private DeleteViewHolderListener<BusInfoViewHolder> mDeleteListener;

    public BusInfoViewHolder(View itemView) {
        super(itemView);
        this.mRootView = itemView;
        this.mPlaceholderTv = (TextView) itemView.findViewById(R.id.tv_item_bus_info_placeholder);
    }

    public void bindView(BusInfo busInfo) {
        this.mBusInfo = busInfo;
        String placeholderText = String.format(Locale.getDefault(),
                "%d|%d|%d",
                busInfo.getStopNumber(),
                busInfo.getBusNumber(),
                busInfo.getDirectionNumber());
        mPlaceholderTv.setText(placeholderText);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.mRootView.setOnLongClickListener(listener);
    }

    public void showPopupMenu(DeleteViewHolderListener<BusInfoViewHolder> deleteListener) {
        Context context = mRootView.getContext();
        if (context != null) {
            PopupMenu popup = new PopupMenu(context, mRootView);
            mDeleteListener = deleteListener;
            popup.setOnMenuItemClickListener(this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_bus_info_popup, popup.getMenu());
            popup.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.i(TAG, "Popup item clicked");
        switch (item.getItemId()) {
            case R.id.menu_bus_info_color:
                editColor();
                return true;
            case R.id.menu_bus_info_name:
                editName();
                return true;
            case R.id.menu_bus_info_delete:
                delete();
                return true;
            default:
                return false;
        }
    }

    private void editColor() {
        Log.i(TAG, "Setting color");
    }

    private void editName() {
        Log.i(TAG, "Setting name");
    }

    private void delete() {
        if (mDeleteListener != null) {
            Log.i(TAG, "Deleting BusInfo");
            mDeleteListener.delete(BusInfoViewHolder.this);
        } else {
            Log.w(TAG, "Attempted to delete BusInfo, but no handler set");
        }
    }

    public BusInfo getBusInfo() {
        return mBusInfo;
    }
}