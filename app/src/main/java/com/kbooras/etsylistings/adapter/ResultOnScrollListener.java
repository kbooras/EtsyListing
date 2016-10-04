package com.kbooras.etsylistings.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Scroll listener for the result RecyclerView implementing infinite scroll.
 */
public abstract class ResultOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int LOADING_THRESHOLD = 10;

    private GridLayoutManager mLayoutManager;
    private int mOffset;
    private int mCurrentOffset;
    private int mPreviousTotalItemCount;
    private boolean mIsLoading;

    public ResultOnScrollListener(GridLayoutManager layoutManager, int offset) {
        mLayoutManager = layoutManager;
        mOffset = offset;
        mCurrentOffset = 0;
        mPreviousTotalItemCount = 0;
        mIsLoading = true;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = mLayoutManager.getChildCount();
        int currentTotalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();

        if (mIsLoading && currentTotalItemCount > mPreviousTotalItemCount) {
                mIsLoading = false;
                mPreviousTotalItemCount = currentTotalItemCount;
        } else if (!mIsLoading && (currentTotalItemCount - visibleItemCount) <=
                (firstVisibleItemPosition + LOADING_THRESHOLD)) {
            mIsLoading = true;
            mCurrentOffset += mOffset;
            onLoadMore(mCurrentOffset);
        }
    }

    public abstract void onLoadMore(int currentOffset);

}
