package com.kirstiebooras.etsylistings.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kirstiebooras.etsylistings.R;
import com.kirstiebooras.etsylistings.model.Result;
import com.kirstiebooras.etsylistings.adapter.ResultAdapter;
import com.kirstiebooras.etsylistings.service.EtsyService;
import com.kirstiebooras.etsylistings.service.FindAllActiveListingsListener;
import com.kirstiebooras.etsylistings.service.FindAllActiveListingsRequest;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EtsyService mService;
    private List<Result> mResults;
    private ResultAdapter mResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mService = new EtsyService(this);

        final EditText searchBar = (EditText) findViewById(R.id.search_bar);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResults != null) {
                    mResults.clear();
                }
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
        FindAllActiveListingsRequest.Builder builder =
                new FindAllActiveListingsRequest.Builder(searchKey);
        FindAllActiveListingsRequest request = builder.build();
        mService.findAllActiveListings(request, new FindAllActiveListingsListener() {
            @Override
            public void onResult(List<Result> results) {
                mResults.addAll(results);
                mResultAdapter.notifyDataSetChanged();
            }
        });
    }

}
