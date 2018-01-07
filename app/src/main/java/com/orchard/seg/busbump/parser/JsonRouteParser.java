package com.orchard.seg.busbump.parser;


import com.orchard.seg.busbump.model.Route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JsonRouteParser extends OCTranspoJsonParser implements RouteParser {

    @Override
    public List<Route> readRoutes(String message) throws IOException {
        List<Route> routes = new LinkedList<>();
        try {
            JSONObject json = new JSONObject(message);
            json = json.getJSONObject("GetRouteSummaryForStopResult");
            raiseOCTranspoError(json);
            json = json.getJSONObject("Routes");
            for(JSONObject route : getJsonCollection(json.get("Route"))) {
                routes.add(routeFromJson(route));
            }
        } catch (JSONException ex) {
            throw new IOException(ex);
        }
        return routes;
    }

    private Route routeFromJson(JSONObject json) throws JSONException {
        return new Route(
                json.getInt("RouteNo"),
                json.getInt("DirectionID"),
                json.getString("Direction"),
                json.getString("RouteHeading"));
    }
}
