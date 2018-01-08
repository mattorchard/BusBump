package com.orchard.seg.busbump.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;


public class RestClient {

    private static final int TIMEOUT = 2000;
    private String mUrlBase;

    public RestClient(@NonNull String urlBase) {
        this.mUrlBase = urlBase;
        if (!mUrlBase.endsWith("/") && !mUrlBase.endsWith("\\")) {
            mUrlBase += "/";
        }
    }

    String getRequest(String urlPath) throws IOException {
        return getRequest(urlPath, HttpURLConnection.HTTP_OK);
    }

    String getRequest(String urlPath, int expectedResponseCode) throws IOException {
        String urlFull = mUrlBase + urlPath;
        Log.d("FooBar", "Making request to: " + urlFull);
        HttpURLConnection connection =(HttpURLConnection)new URL(urlFull).openConnection();
        connection.setConnectTimeout(TIMEOUT);
        connection.setRequestMethod("GET");
        connection.connect();

        int actualResponseCode = connection.getResponseCode();
        if (expectedResponseCode != actualResponseCode) {
            throw new IOException("Error from server: "
                                    + actualResponseCode
                                    + ". With Message: "
                                    + connection.getResponseMessage());
        }
        return readResponse(connection.getInputStream());
    }

    private String readResponse(@NonNull InputStream stream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        try {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } finally {
            stream.close();
        }
    }

    String formatQueryString(String... vars) {
        return "?" + TextUtils.join("&", vars);
    }
}
