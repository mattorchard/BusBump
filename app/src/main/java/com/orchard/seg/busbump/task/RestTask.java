package com.orchard.seg.busbump.task;

import android.content.Context;
import android.os.AsyncTask;

import com.orchard.seg.busbump.network.OCTranspoRestClient;

import java.lang.ref.WeakReference;

public abstract class RestTask <P, R, C extends Context> extends AsyncTask<P, Integer, R> {

    private static final String TAG = "GetArrivals";

    private final WeakReference<C> mContext;
    final OCTranspoRestClient mRestAdapter;

    public RestTask(C context) {
        mContext = new WeakReference<>(context);
        mRestAdapter = new OCTranspoRestClient(context.getResources());
    }

    @Override
    protected void onPostExecute(R results) {
        C context = mContext.get();
        if(context != null) {
            onPostExecute(results, context);
        }
    }

    protected abstract void onPostExecute(R results, C context);

    @Override
    protected void onCancelled(R partialResults) {
        C context = mContext.get();
        if(context != null) {
            onPostExecute(partialResults, context);
        }
    }

    protected abstract void onCancelled(R partialResults, C context);
}
