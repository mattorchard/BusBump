package com.orchard.seg.busbump.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.orchard.seg.busbump.model.Arrivals;
import com.orchard.seg.busbump.model.BusInfo;
import com.orchard.seg.busbump.parser.ArrivalParser;
import com.orchard.seg.busbump.network.OCTranspoRestClient;
import com.orchard.seg.busbump.parser.JsonArrivalParser;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class GetArrivals <C extends Context> extends RestTask <BusInfo, Arrivals[], C> {

    private static final String TAG = "GetArrivals";

    public GetArrivals(C context) {
        super(context);
    }

    private Arrivals fetchArrivalsForBusInfo(BusInfo busInfo) throws IOException{
        Log.i(TAG, "Making request for bus times");
        String response = mRestAdapter.getBusTimes(busInfo.getStopNumber());
        Log.i(TAG, "Parsing response from server");
        ArrivalParser arrivalParser = new JsonArrivalParser(busInfo);
        Arrivals arrivals = arrivalParser.readArrivals(response);
        Log.i(TAG, "Arrivals parsed successfully");
        Log.v(TAG, "Arrival Contents: " + arrivals.toString());
        return arrivals;
    }

    @Override
    protected Arrivals[] doInBackground(BusInfo... busInfos) {
        Arrivals[] arrivalsArray = new Arrivals[busInfos.length];
        try {
            for (int i = 0; i < busInfos.length; i++) {
                publishProgress((int) ((i / (float) busInfos.length) * 100));
                arrivalsArray[i] = fetchArrivalsForBusInfo(busInfos[i]);
                if (isCancelled()) break;
            }
        } catch (IOException ex) {
            Log.e(TAG, "Error fetching bus times", ex);
            this.cancel(false);
        }
        return arrivalsArray;
    }

    protected void onPostExecute(Arrivals[] arrivals, C context) {
        //Todo: Make abstract once GetArrivals is subclassed
    }

    @Override
    protected void onCancelled(Arrivals[] partialResults, C context) {
        //Todo: Make abstract once GetArrivals is subclassed
    }
}
