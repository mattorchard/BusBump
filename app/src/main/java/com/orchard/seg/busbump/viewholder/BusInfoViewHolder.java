package com.orchard.seg.busbump.viewholder;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.animation.ViewWeightHolder;
import com.orchard.seg.busbump.model.Arrivals;
import com.orchard.seg.busbump.model.BusInfo;
import com.orchard.seg.busbump.network.OCTranspoRestClient;
import com.orchard.seg.busbump.task.GetArrivals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BusInfoViewHolder
        extends RecyclerView.ViewHolder
        implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    private static final String TAG = "BusInfoViewHolder";

    private BusInfo mBusInfo;
    private CardView mRootView;
    private TextView mNameTv;
    private Space mNameSpace;
    private ProgressBar mProgressBar;
    private ImageView mErrorIconIv;
    private TextView[] mTimeFields;

    private GetArrivals mTask;
    private boolean mFirstFetch = true;

    private DeleteViewHolderListener<BusInfoViewHolder> mDeleteListener;

    public BusInfoViewHolder(View itemView) {
        super(itemView);
        this.mRootView = (CardView) itemView;
        this.mNameTv = (TextView) itemView.findViewById(R.id.tv_item_bus_info_name);
        this.mNameSpace = (Space) itemView.findViewById(R.id.sp_item_bus_info_name_space);
        this.mProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_item_bus_info_fetch);
        this.mErrorIconIv = (ImageView) itemView.findViewById(R.id.iv_item_bus_info_error_icon);
        this.mTimeFields = new TextView[] {
                (TextView) itemView.findViewById(R.id.tv_bus_info_time_first),
                (TextView) itemView.findViewById(R.id.tv_bus_info_time_second),
                (TextView) itemView.findViewById(R.id.tv_bus_info_time_third)
        };
        mRootView.setOnClickListener(this);
    }

    public void bindView(BusInfo busInfo) {
        this.mBusInfo = busInfo;
        String placeholderText = String.format(Locale.getDefault(),
                "%d  %d  %d",
                busInfo.getStopNumber(),
                busInfo.getBusNumber(),
                busInfo.getDirectionNumber() + 1);
        mNameTv.setText(placeholderText);
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

    private GetArrivals getTask() {
        Resources resources = mRootView.getContext().getResources();
        return new GetArrivalsForBusInfo(
        new OCTranspoRestClient(resources.getString(R.string.octranspo_base_url),
                                resources.getString(R.string.octranspo_app_id_1),
                                resources.getString(R.string.octranspo_api_key_1)),
                this);
    }

    @Override
    public void onClick(View v) {
        if (mTask == null && mBusInfo != null) {
            mTask = getTask();
            mTask.execute(mBusInfo);
        }
    }

    private void setTimeFieldsVisible(boolean visible) {
        for (TextView view : mTimeFields) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    void fetchArrivalsStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorIconIv.setVisibility(View.INVISIBLE);
    }

    void fetchArrivalsFailed() {
        mProgressBar.setVisibility(View.GONE);
        mErrorIconIv.setVisibility(View.VISIBLE);
        setTimeFieldsVisible(false);
        mTask = null;
    }

    void fetchArrivalsCompleted(Arrivals arrival) {
        mProgressBar.setVisibility(View.GONE);
        mErrorIconIv.setVisibility(View.INVISIBLE);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.CANADA);
        for (int i = 0; i < 3; i++) {
            TextView timeField = mTimeFields[i];
            String text = (arrival.getNumArrivals() - 1 < i)
                    ? ""
                    : dateFormat.format(arrival.getArrival(i));
            timeField.setText(text);
        }
        mTask = null;
        setTimeFieldsVisible(true);
        animateBackgroundColor();
        if (mFirstFetch) {
            mFirstFetch = false;
            animateMoveName();
        }
    }

    @ColorInt
    private int grayscale(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] * 0.25f; //Gray scale the color
        hsv[2] = hsv[2] * 0.75f; //Darken slightly
        return Color.HSVToColor(hsv);
    }

    private void animateBackgroundColor() {
        Resources resources = mRootView.getContext().getResources();
        int colorFrom = resources.getColor(R.color.blue);
        int colorTo = grayscale(colorFrom);
        ValueAnimator colorAnimation =
                ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(30 * 1000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mRootView.setCardBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    private void animateMoveName() {
        ViewWeightHolder animationWrapper = new ViewWeightHolder(mNameSpace);
        ObjectAnimator anim = ObjectAnimator.ofFloat(animationWrapper,
                "weight",
                animationWrapper.getWeight(),
                0);
        anim.setDuration(500);
        anim.start();
    }
}
class GetArrivalsForBusInfo extends GetArrivals {

    private BusInfoViewHolder mViewHolder;

    GetArrivalsForBusInfo(OCTranspoRestClient restClient, BusInfoViewHolder viewHolder) {
        super(restClient);
        this.mViewHolder = viewHolder;
    }

    @Override
    protected void onPreExecute() {
        mViewHolder.fetchArrivalsStarted();
    }

    @Override
    protected void onCancelled(Arrivals[] arrivals) {
        mViewHolder.fetchArrivalsFailed();
    }

    @Override
    protected void onPostExecute(Arrivals[] arrivals) {
        if (arrivals.length > 0) {
            mViewHolder.fetchArrivalsCompleted(arrivals[0]);
        } else {
            mViewHolder.fetchArrivalsFailed();
        }

    }
}