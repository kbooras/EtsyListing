package com.kbooras.etsylistings.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.kbooras.etsylistings.R;
import com.kbooras.etsylistings.adapter.ResultOnScrollListener;
import com.kbooras.etsylistings.model.Result;
import com.kbooras.etsylistings.adapter.ResultAdapter;
import com.kbooras.etsylistings.service.EtsyService;
import com.kbooras.etsylistings.service.FindAllFeaturedListingsListener;
import com.kbooras.etsylistings.service.FindAllFeaturedListingsRequest;
import com.kbooras.etsylistings.service.FindAllListingActiveListener;
import com.kbooras.etsylistings.service.FindAllListingActiveRequest;
import com.kbooras.etsylistings.view.SearchEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String RESULTS_KEY = "results";
    private static final int NUM_COLUMNS = 2;
    private static final int OFFSET = 24;
    private static final int STARTING_OFFSET = 0;

    private EtsyService mService;
    private ArrayList<Result> mResults;
    private ResultAdapter mResultAdapter;
    private String mCurrentSearchKey;

    private SearchEditText mSearchEditText;
    private TextWatcher mTextChangedListener;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mService = new EtsyService(this);
        mResults = new ArrayList<>();
        mResultAdapter = new ResultAdapter(getApplicationContext(), mResults);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        mSearchEditText = (SearchEditText) findViewById(R.id.search_edit_text);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearchEditText.clearFocus();
                    InputMethodManager inputManager =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);

                    displayResultsForNewQuery();
                    return true;
                }
                return false;
            }
        });

        mTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mSearchEditText.showCancelButton(editable.length() > 0);
            }
        };

        mGridLayoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mRecyclerView = (RecyclerView) findViewById(R.id.results_view);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mResultAdapter);

        showFeaturedListings(STARTING_OFFSET);
        mRecyclerView.addOnScrollListener(getFeaturedListingsOnScrollListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Text changed listener must be added in onResume since rotation triggers onTextChanged()
        mSearchEditText.addTextChangedListener(mTextChangedListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RESULTS_KEY, mResults);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<Result> mResultList = savedInstanceState.getParcelableArrayList(RESULTS_KEY);
        mResults.addAll(mResultList);
        mResultAdapter.notifyDataSetChanged();
    }

    private void displayResultsForNewQuery() {
        if (mResults != null) {
            mResults.clear();
        }

        mCurrentSearchKey = mSearchEditText.getText().toString();
        if (mCurrentSearchKey.equals("")) {
            showFeaturedListings(STARTING_OFFSET);
            mRecyclerView.clearOnScrollListeners();
            mRecyclerView.addOnScrollListener(getFeaturedListingsOnScrollListener());
        } else {
            performSearch(mCurrentSearchKey, STARTING_OFFSET);
            mRecyclerView.clearOnScrollListeners();
            mRecyclerView.addOnScrollListener(getSearchOnScrollListener());
        }
    }

    private ResultOnScrollListener getFeaturedListingsOnScrollListener() {
        return new ResultOnScrollListener(mGridLayoutManager, OFFSET) {
            @Override
            public void onLoadMore(int currentOffset) {
                showFeaturedListings(currentOffset);
            }
        };
    }

    private ResultOnScrollListener getSearchOnScrollListener() {
        return new ResultOnScrollListener(mGridLayoutManager, OFFSET) {
            @Override
            public void onLoadMore(int currentOffset) {
                performSearch(mCurrentSearchKey, currentOffset);
            }
        };
    }

    private void performSearch(String searchKey, int offset) {
        FindAllListingActiveRequest.Builder builder =
                new FindAllListingActiveRequest.Builder(searchKey).withOffset(offset);
        FindAllListingActiveRequest request = builder.build();
        mService.findAllActiveListings(request, new FindAllListingActiveListener() {
            @Override
            public void onResult(List<Result> results) {
                mResults.addAll(results);
                mResultAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showFeaturedListings(int offset) {
        FindAllFeaturedListingsRequest request =
                new FindAllFeaturedListingsRequest.Builder().withOffset(offset).build();
        mService.findAllFeaturedListings(request, new FindAllFeaturedListingsListener() {
            @Override
            public void onResult(List<Result> results) {
                mResults.addAll(results);
                mResultAdapter.notifyDataSetChanged();
            }
        });
    }

}
