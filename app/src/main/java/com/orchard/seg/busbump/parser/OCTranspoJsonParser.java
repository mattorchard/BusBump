package com.orchard.seg.busbump.parser;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.LinkedList;

abstract class OCTranspoJsonParser {

    boolean isJsonArray(Object child) throws JSONException {
        if (child instanceof JSONArray) {
            return true;
        } else if (child instanceof JSONObject) {
            return false;
        }
        throw new JSONException("Expected JSON object or array but found neither");
    }

    Collection<JSONObject> getJsonCollection(Object json) throws JSONException {
        Collection<JSONObject> jsonCollection = new LinkedList<>();
        if (isJsonArray(json)) {
            JSONArray jsonArray = (JSONArray) json;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonCollection.add(jsonArray.getJSONObject(i));
            }
        } else {
            jsonCollection.add((JSONObject) json);
        }
        return jsonCollection;
    }

    void raiseOCTranspoError(JSONObject jsonResponse) throws JSONException, OCTranspoException {
        if (jsonResponse.has("Error")
                && !TextUtils.isEmpty(jsonResponse.getString("Error"))) {
            throw new OCTranspoException(jsonResponse.getString("Error"));
        }
    }
}
