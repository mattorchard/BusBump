package com.orchard.seg.busbump.viewholder;

import android.support.v7.widget.RecyclerView;

public interface DeleteViewHolderListener <H extends RecyclerView.ViewHolder> {
    void delete(H holder);
}
