package com.orchard.seg.busbump.task;

import android.content.Context;
import android.util.Log;

import com.orchard.seg.busbump.model.Route;
import com.orchard.seg.busbump.parser.JsonRouteParser;


import java.io.IOException;
import java.util.List;

public class GetRoutes extends RestTask <Integer, List<Route>> {

    private static final String TAG = "GetRoutes";

    public GetRoutes(Context context) {
        super(context);
    }

    @Override
    protected List<Route> doInBackground(Integer... stopNos) {
        try {
            if (stopNos.length != 1) {
                throw new IllegalArgumentException(
                        "GetRoutes task must be used with a single stop number");
            }
            int stopNo = stopNos[0];
            String response = mRestAdapter.getBusRoutes(stopNo);
            JsonRouteParser parser = new JsonRouteParser();
            List<Route> routes = parser.readRoutes(response);
            Log.i(TAG, routes.toString());
            return routes;

        } catch (IOException | IllegalArgumentException ex) {
            Log.e(TAG, "Error fetching bus data", ex);
            this.cancel(false);
            return null;
        }
    }

    protected void onPostExecute(List<Route> routes) {
        //Todo: Make abstract once GetArrivals is subclassed
    }

    @Override
    protected void onCancelled(List<Route> routes) {
        //Todo: Make abstract once GetArrivals is subclassed
    }
}