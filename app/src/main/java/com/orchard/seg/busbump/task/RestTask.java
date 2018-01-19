package com.orchard.seg.busbump.task;

import android.os.AsyncTask;

import com.orchard.seg.busbump.network.OCTranspoRestClient;


abstract class RestTask <P, R> extends AsyncTask<P, Integer, R> {

    final OCTranspoRestClient mRestAdapter;

    RestTask(OCTranspoRestClient restClient) {
        this.mRestAdapter = restClient;
    }
}
