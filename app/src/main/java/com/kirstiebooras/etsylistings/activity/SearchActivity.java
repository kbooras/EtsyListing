package com.kirstiebooras.etsylistings.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kirstiebooras.etsylistings.R;
import com.kirstiebooras.etsylistings.adapter.Result;
import com.kirstiebooras.etsylistings.adapter.ResultAdapter;
import com.kirstiebooras.etsylistings.config.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final String ENDPOINT = "https://api.etsy.com/v2/listings/active?includes=MainImage&api_key=%s";
    private static final int LIMIT = 2;

    private int mOffset = 0; // update on scroll

    private List<Result> mResults;
    private ResultAdapter mResultAdapter;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mQueue = Volley.newRequestQueue(this);

        final EditText searchBar = (EditText) findViewById(R.id.search_bar);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(searchBar.getText().toString());
            }
        });

        mResults = new ArrayList<>();
        mResultAdapter = new ResultAdapter(mResults);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.results_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mResultAdapter);
    }

    private void performSearch(String searchKey) {
        String url = String.format(ENDPOINT, Config.ETSY_API_KEY);
        JSONObject parameters = getParameters(searchKey, LIMIT, mOffset);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject object = results.getJSONObject(i);
                                String title = object.getString("title");
                                String imageUrl = object.getJSONObject("MainImage").getString("url_75x75");
                                mResults.add(new Result(title, imageUrl));
                                Log.d(TAG, title + " " + imageUrl);
                            }
                            mResultAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
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

    /**
     * Formats the search key to be compatible with the "keywords" header.
     * @param searchKey the search key
     * @return the formatted search key
     */
    private JSONObject getParameters(String searchKey, int limit, int offset) {
        String[] split = searchKey.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            builder.append(split[i]);
            builder.append('+');
        }
        builder.append(split[split.length - 1]);

        Map<String, String> params = new HashMap<>();
        params.put("keywords", builder.toString());
        params.put("limit", String.valueOf(limit));
        params.put("offset", String.valueOf(offset));

        return new JSONObject(params);
    }
}
