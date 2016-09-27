package com.kirstiebooras.etsylistings.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirstiebooras.etsylistings.R;

/**
 * View holder for a search result.
 */
public class ResultViewHolder extends RecyclerView.ViewHolder {

    private ImageView mThumbnail;
    private TextView mTitle;

    public ResultViewHolder(View itemView) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        mTitle = (TextView) itemView.findViewById(R.id.title);
    }

    public void bindView(Result result) {
        mTitle.setText(result.getTitle());
    }
}
