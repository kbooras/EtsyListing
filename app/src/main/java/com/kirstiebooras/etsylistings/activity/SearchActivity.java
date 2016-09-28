package com.kirstiebooras.etsylistings.activity;

import android.net.Uri;
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
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private static final String SCHEME = "https";
    private static final String ENDPOINT = "api.etsy.com/v2";
    private static final String FIND_ALL_SHOP_LISTINGS_PATH = "listings/active";

    private static final String INCLUDES_QUERY_PARAM = "includes";
    private static final String API_KEY_QUERY_PARAM = "api_key";
    private static final String LIMIT_QUERY_PARAM = "limit";
    private static final String OFFSET_QUERY_PARAM = "offset";
    private static final String KEYWORDS_QUERY_PARAM = "keywords";

    private static final String RESULTS_ATTRIBUTE = "results";
    private static final String TITLE_ATTRIBUTE = "title";
    private static final String MAIN_IMAGE_ATTRIBUTE = "MainImage";
    private static final String MEDIUM_IMAGE_ATTRIBUTE = "url_fullxfull";

    private static final String STRING_SPLIT = "\\s+";
    private static final char SEARCH_KEY_SEPARATOR = '+';
    private static final int DEFAULT_LIMIT = 25;

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
        String url = buildSearchUrl(searchKey, DEFAULT_LIMIT, mOffset);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray(RESULTS_ATTRIBUTE);
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject object = results.getJSONObject(i);
                                String title = object.getString(TITLE_ATTRIBUTE);
                                String imageUrl = object.getJSONObject(MAIN_IMAGE_ATTRIBUTE).getString(MEDIUM_IMAGE_ATTRIBUTE);
                                mResults.add(new Result(title, imageUrl));
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

    private String buildSearchUrl(String searchKey, int limit, int offset) {
        String[] split = searchKey.split(STRING_SPLIT);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            stringBuilder.append(split[i]);
            stringBuilder.append(SEARCH_KEY_SEPARATOR);
        }
        stringBuilder.append(split[split.length - 1]);

        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(SCHEME)
                .encodedAuthority(ENDPOINT)
                .encodedPath(FIND_ALL_SHOP_LISTINGS_PATH)
                .appendQueryParameter(INCLUDES_QUERY_PARAM, MAIN_IMAGE_ATTRIBUTE)
                .appendQueryParameter(API_KEY_QUERY_PARAM, Config.ETSY_API_KEY)
                .appendQueryParameter(LIMIT_QUERY_PARAM, String.valueOf(limit))
                .appendQueryParameter(OFFSET_QUERY_PARAM, String.valueOf(offset))
                .appendQueryParameter(KEYWORDS_QUERY_PARAM, stringBuilder.toString());
        return uriBuilder.build().toString();
    }

}
