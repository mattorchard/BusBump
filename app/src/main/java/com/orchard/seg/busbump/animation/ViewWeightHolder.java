package com.orchard.seg.busbump.animation;


import android.view.View;
import android.widget.LinearLayout;

public class ViewWeightHolder {

    private View mView;
    private LinearLayout.LayoutParams mLayoutParams;

    public ViewWeightHolder(View view) {
        if (! (view.getLayoutParams() instanceof LinearLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view should have LinearLayout as parent");
        }
        this.mView = view;
        this.mLayoutParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
    }

    public void setWeight(float weight) {
        mLayoutParams.weight = weight;
        mView.getParent().requestLayout();
    }

    public float getWeight() {
        return mLayoutParams.weight;
    }
}
