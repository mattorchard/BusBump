package com.orchard.seg.busbump.parser;


import android.text.TextUtils;

import com.orchard.seg.busbump.model.Arrivals;
import com.orchard.seg.busbump.model.BusInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;


public class JsonArrivalParser implements ArrivalParser {

    private static final int MS_IN_MIN = 60000;

    private BusInfo mInterest;

    public JsonArrivalParser(BusInfo interest) {
        this.mInterest = interest;
    }

    @Override
    public Arrivals readArrivals(String message) throws IOException {
        try {
            JSONObject json = new JSONObject(message);
            json = json.getJSONObject("GetRouteSummaryForStopResult");
            checkForError(json);
            json = json.getJSONObject("Routes");

            Object routeObject = json.get("Route");

            if (routeObject instanceof JSONObject) {
                return arrivalsFromJson((JSONObject) routeObject);
            } else if (routeObject instanceof JSONArray) {
                return arrivalsFromJsonArray((JSONArray) routeObject);
            } else {
                throw new JSONException("Route data in unexpected format");
            }
        } catch (JSONException ex) {
            throw new IOException(ex);
        }
    }

    private void checkForError(JSONObject jsonResponse) throws JSONException, OCTranspoException {
        if (jsonResponse.has("Error")
                && !TextUtils.isEmpty(jsonResponse.getString("Error"))) {
            throw new OCTranspoException(jsonResponse.getString("Error"));
        }
    }


    private Arrivals arrivalsFromJsonArray(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject arrivalJson = jsonArray.getJSONObject(i);
            if (arrivalJson.getInt("RouteNo") == mInterest.getBusNumber()
                && arrivalJson.getInt("DirectionID") == mInterest.getDirectionNumber()) {
                return arrivalsFromJson(arrivalJson);
            }
        }
        throw new JSONException("No bus route found for given stop and direction");
    }

    private Arrivals arrivalsFromJson(JSONObject routeJson) throws JSONException {
        Date[] arrivalTimes;

        Object tripsObject = routeJson.get("Trips");
        if (tripsObject instanceof JSONObject
                && routeJson.getJSONObject("Trips").has("Trip")) {
                tripsObject = routeJson.getJSONObject("Trips").get("Trip");
        }
        if (tripsObject instanceof JSONObject) {
            arrivalTimes = new Date[]{dateFromTrip((
                    (JSONObject) tripsObject).getJSONObject("Trip"))};
        } else if (tripsObject instanceof JSONArray) {
            JSONArray tripArray = (JSONArray) tripsObject;
            arrivalTimes = new Date[tripArray.length()];
            for (int i = 0; i < tripArray.length(); i++) {
                arrivalTimes[i] = dateFromTrip(tripArray.getJSONObject(i));
            }
        } else {
            throw new JSONException("Arrival data in unexpected format");
        }
        return new Arrivals(new Date(), arrivalTimes);
    }

    private Date dateFromTrip(JSONObject trip) throws JSONException{
        int minutesFromNow = trip.getInt("AdjustedScheduleTime");
        Date now = new Date();
        return new Date(now.getTime() + minutesFromNow * MS_IN_MIN);
    }
}
