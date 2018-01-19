package com.orchard.seg.busbump.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orchard.seg.busbump.R;
import com.orchard.seg.busbump.adapter.RouteAdapter;
import com.orchard.seg.busbump.model.Route;
import com.orchard.seg.busbump.network.OCTranspoRestClient;
import com.orchard.seg.busbump.task.GetRoutes;

import java.util.LinkedList;
import java.util.List;

public class AddBusInfoDialogManager {

    public static final String INTENT_STOP_NO = "INTENT_STOP_NO";
    public static final String INTENT_ROUTE = "INTENT_ROUTE";

    private int mStopNo;
    private final OCTranspoRestClient mRestClient;
    private final AlertDialog mDialog;
    private OnDialogFinishListener mOnDialogFinishListener;

    private GetRoutes mTask;

    private TextView mMessageTv;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RouteAdapter mRouteAdapter;

    private View createRouteView(Context context, LayoutInflater layoutInflater) {
        @SuppressLint("InflateParams") // Dialog should be inflated with null root.
                View rootView = layoutInflater.inflate(R.layout.dialog_select_route, null);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_dialog_bus_info_routes);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mRouteAdapter = new RouteAdapter(new LinkedList<Route>()) {
            @Override
            protected void onItemClick(Route route) {
                AddBusInfoDialogManager.this.clickRoute(route);
            }
        };
        mRecyclerView.setAdapter(mRouteAdapter);

        mMessageTv =
                (TextView) rootView.findViewById(R.id.tv_dialog_bus_info_message);
        mProgressBar =
                (ProgressBar) rootView.findViewById(R.id.pb_dialog_bus_info_loading);
        TextInputEditText stopNoTiet =
                (TextInputEditText) rootView.findViewById(R.id.et_dialog_bus_info_stop_no);

        stopNoTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 4) {
                    try {
                        int stopNo = Integer.parseInt(s.toString());
                        if (stopNo > 9999 || stopNo < 0) {
                            throw new NumberFormatException("Stop number must be four digits");
                        }
                        AddBusInfoDialogManager.this.searchForBusStop((stopNo));
                    } catch (NumberFormatException ex) {
                        s.clear();
                    }
                }
            }
        });
        return rootView;
    }

    private AlertDialog createDialog(final Context context, final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
        builder.setNegativeButton(R.string.bus_info_dialog_button_negative, null);
        return builder.create();
    }


    public AddBusInfoDialogManager(Context context,
                                   LayoutInflater layoutInflater) {
        View rootView = createRouteView(context, layoutInflater);
        mDialog = createDialog(context, rootView);
        Resources resources = context.getResources();
        mRestClient = new OCTranspoRestClient(resources.getString(R.string.octranspo_base_url),
                resources.getString(R.string.octranspo_app_id_1),
                resources.getString(R.string.octranspo_api_key_1));
    }

    public void show() {
        mDialog.show();
    }

    private void searchForBusStop(int stopNo) {
        if (mTask != null) {
            mTask.cancel(false);
        }
        mTask = new GetRoutesForStop(mRestClient, AddBusInfoDialogManager.this);
        mTask.execute(stopNo);
        mStopNo = stopNo;
    }

    void fetchRoutesStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
        mMessageTv.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }

    void fetchRoutesFailed() {
        mProgressBar.setVisibility(View.GONE);
        mMessageTv.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mMessageTv.setText(R.string.bus_info_dialog_message_error);
    }

    void fetchRoutesCompleted(List<Route> routes) {
        mProgressBar.setVisibility(View.GONE);
        if (routes.size() > 0) {
            mMessageTv.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRouteAdapter.setDataSet(routes);
        } else {
            mMessageTv.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mMessageTv.setText(R.string.bus_info_dialog_message_empty);
        }
    }

    private void clickRoute(Route route) {
        mDialog.dismiss();
        Intent closingIntent = new Intent();
        closingIntent.putExtra(INTENT_ROUTE, route);
        closingIntent.putExtra(INTENT_STOP_NO, mStopNo);
        if (mOnDialogFinishListener != null) {
            mOnDialogFinishListener.finish(closingIntent);
        }
    }

    public void setOnDialogFinishListener(OnDialogFinishListener listener) {
        this.mOnDialogFinishListener = listener;
    }
}
class GetRoutesForStop extends GetRoutes {

    private AddBusInfoDialogManager mDialogManager;

    GetRoutesForStop(OCTranspoRestClient restClient, AddBusInfoDialogManager dialogManager) {
        super(restClient);
        this.mDialogManager = dialogManager;
    }

    @Override
    protected void onPreExecute() {
        mDialogManager.fetchRoutesStarted();
    }

    @Override
    protected void onCancelled(List<Route> routes) {
        mDialogManager.fetchRoutesFailed();
    }

    @Override
    protected void onPostExecute(List<Route> routes) {
        mDialogManager.fetchRoutesCompleted(routes);
    }
}