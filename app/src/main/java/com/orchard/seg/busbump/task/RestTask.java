package com.orchard.seg.busbump.task;

import android.content.Context;
import android.os.AsyncTask;

import com.orchard.seg.busbump.network.OCTranspoRestClient;

import java.lang.ref.WeakReference;

public abstract class RestTask <P, R> extends AsyncTask<P, Integer, R> {

    private static final String TAG = "GetArrivals";

    final OCTranspoRestClient mRestAdapter;

    public RestTask(Context context) {
        mRestAdapter = new OCTranspoRestClient(context.getResources());
    }
}
