package com.kirstiebooras.etsylistings.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kirstiebooras.etsylistings.R;
import com.kirstiebooras.etsylistings.adapter.ResultOnScrollListener;
import com.kirstiebooras.etsylistings.model.Result;
import com.kirstiebooras.etsylistings.adapter.ResultAdapter;
import com.kirstiebooras.etsylistings.service.EtsyService;
import com.kirstiebooras.etsylistings.service.FindAllListingActiveListener;
import com.kirstiebooras.etsylistings.service.FindAllListingActiveRequest;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final int NUM_COLUMNS = 2;
    private static final int OFFSET = 24;
    private static final int STARTING_OFFSET = 0;

    private EtsyService mService;
    private List<Result> mResults;
    private ResultAdapter mResultAdapter;
    private String mCurrentSearchKey;

    private EditText mSearchEditText;
    private TextWatcher mTextChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mService = new EtsyService(this);
        mResults = new ArrayList<>();
        mResultAdapter = new ResultAdapter(getApplicationContext(), mResults);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        mSearchEditText = (EditText) findViewById(R.id.search_edit_text);
        mTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                showSearchCancelButton(mSearchEditText, editable.length() > 0);
            }
        };
        setupSearchEditTextListeners();

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.results_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mResultAdapter);
        recyclerView.addOnScrollListener(new ResultOnScrollListener(mLayoutManager, OFFSET) {
            @Override
            public void onLoadMore(int currentOffset) {
                performSearch(mCurrentSearchKey, currentOffset);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchEditText.addTextChangedListener(mTextChangedListener);
    }

    private void setupSearchEditTextListeners() {
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mResults != null) {
                        mResults.clear();
                    }

                    mSearchEditText.clearFocus();
                    InputMethodManager inputManager =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);

                    mCurrentSearchKey = mSearchEditText.getText().toString();
                    performSearch(mCurrentSearchKey, STARTING_OFFSET);
                    return true;
                }
                return false;
            }
        });

        mSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showSearchCancelButton(mSearchEditText,
                        hasFocus && mSearchEditText.getText().length() > 0);
            }
        });

        mSearchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                if(event.getAction() == MotionEvent.ACTION_UP &&
                        event.getX() <= mSearchEditText
                                .getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()) {
                    mSearchEditText.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    private void showSearchCancelButton(EditText searchEditText, boolean show) {
        int drawableLeft;
        if (show) {
            drawableLeft = R.drawable.ic_cancel;
        } else {
            drawableLeft = R.drawable.ic_magnifying_glass;
        }
        searchEditText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
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

}
