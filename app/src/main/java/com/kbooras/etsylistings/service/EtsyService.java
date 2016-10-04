package com.kbooras.etsylistings.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kbooras.etsylistings.model.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Makes Etsy API requests.
 */
public class EtsyService {

    private static final String TAG = EtsyService.class.getSimpleName();

    private static final String RESULTS_ATTRIBUTE = "results";

    private RequestQueue mQueue;

    public EtsyService(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public void findAllActiveListings(FindAllListingActiveRequest request,
                                      final FindAllListingActiveListener listingsListener) {
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                request.getSearchUrl(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray(RESULTS_ATTRIBUTE);
                            listingsListener.onResult(parseResults(results));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error while parsing findAllActiveListings response: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error while calling service: " + error);
                    }
                }
        );
        mQueue.add(getRequest);
    }

    public void findAllFeaturedListings(FindAllFeaturedListingsRequest request,
                                        final FindAllFeaturedListingsListener listingsListener) {
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                request.getFeaturedListingsUrl(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray(RESULTS_ATTRIBUTE);
                            listingsListener.onResult(parseResults(results));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error while parsing findAllFeaturedListings response: " + e);
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

    private ArrayList<Result> parseResults(JSONArray results) {
        ArrayList<Result> searchResults = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            try {
                JSONObject object = (JSONObject) results.get(i);
                searchResults.add(new Result(object));
            } catch (JSONException | NullPointerException e) {
                Log.e(TAG, "Error when parsing result object: " + e);
            }
        }
        return searchResults;
    }
}
