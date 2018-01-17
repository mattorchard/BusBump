package com.orchard.seg.busbump.network;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.orchard.seg.busbump.R;

import java.io.IOException;

public class OCTranspoRestClient extends RestClient {

    private static final String BUS_TIMES_ENDPOINT = "GetNextTripsForStopAllRoutes";
    private static final String BUS_ROUTES_ENDPOINT = "GetRouteSummaryForStop";
    private static final String FORMAT_QS = "format=JSON";

    private final String mAppIdQs;
    private final String mApiKeyQs;

    public OCTranspoRestClient(String urlBase, String appId, String apiKey) {
        super(urlBase);
        mAppIdQs = "appID=" + appId;
        mApiKeyQs = "apiKey=" + apiKey;
    }

    public String getBusTimes(int stopNo) throws IOException {
        return super.getRequest(BUS_TIMES_ENDPOINT
                + super.formatQueryString(
                        FORMAT_QS, mAppIdQs, mApiKeyQs, "stopNo=" + stopNo));
    }

    public String getBusRoutes(int stopNo) throws IOException {
        return super.getRequest(BUS_ROUTES_ENDPOINT
                + super.formatQueryString(
                FORMAT_QS, mAppIdQs, mApiKeyQs, "stopNo=" + stopNo));
    }
}
