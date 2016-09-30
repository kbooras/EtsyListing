package com.kirstiebooras.etsylistings.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirstiebooras.etsylistings.R;
import com.kirstiebooras.etsylistings.model.Result;

import java.util.List;

/**
 * Recucler view adapter for search results.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private Context mContext;
    private List<Result> mResultList;

    public ResultAdapter(Context context, List<Result> resultList) {
        mContext = context;
        mResultList = resultList;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_row, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.bindView(mContext, mResultList.get(position));
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }
}
