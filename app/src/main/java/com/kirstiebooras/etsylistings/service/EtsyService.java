package com.kirstiebooras.etsylistings.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirstiebooras.etsylistings.model.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Makes Etsy API requests.
 */
public class EtsyService {

    private static final String TAG = EtsyService.class.getSimpleName();

    private static final String RESULTS_ATTRIBUTE = "results";

    private RequestQueue mQueue;
    private ObjectMapper mMapper;

    public EtsyService(Context context) {
        mQueue = Volley.newRequestQueue(context);
        mMapper = new ObjectMapper();
    }

    public void findAllActiveListings(FindAllActiveListingsRequest request,
                                      final FindAllActiveListingsListener listingsListener) {
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                request.getSearchUrl(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray(RESULTS_ATTRIBUTE);
                            List<Result> searchResults = mMapper.readValue(results.toString(),
                                    new TypeReference<List<Result>>(){});
                            listingsListener.onResult(searchResults);
                        } catch (JSONException | IOException e) {
                            Log.e(TAG, "Exception while parsing JSON: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error while calling service: " + error.toString());
                    }
                }
        );
        mQueue.add(getRequest);
    }
}
