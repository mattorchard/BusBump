package com.orchard.seg.busbump.network;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.orchard.seg.busbump.R;

import java.io.IOException;

public class ArrivalRestClient extends RestClient {

    private final String mUrlSuffix;

    public ArrivalRestClient(@NonNull Resources resources) {
        super(resources.getString(R.string.octranspo_base_url));
        mUrlSuffix = String.format("%s?format=JSON&appID=%s&apiKey=%s&stopNo=",
                resources.getString(R.string.octranspo_endpoint_get_arrivals),
                resources.getString(R.string.octranspo_app_id_1),
                resources.getString(R.string.octranspo_api_key_1));
    }

    @SuppressLint("DefaultLocale")// Formatting used for URLSuffix
    public String getBusTimes(int stopNo) throws IOException {
        return super.getRequest(mUrlSuffix + stopNo);
    }
}
