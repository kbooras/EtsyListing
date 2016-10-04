package com.kbooras.etsylistings.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kbooras.etsylistings.R;
import com.kbooras.etsylistings.model.Result;

import java.util.Currency;

/**
 * View holder for a search result.
 */
public class ResultViewHolder extends RecyclerView.ViewHolder {

    private RelativeLayout mContainer;
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mPrice;

    public ResultViewHolder(View itemView) {
        super(itemView);
        mContainer = (RelativeLayout) itemView.findViewById(R.id.container);
        mImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mPrice = (TextView) itemView.findViewById(R.id.price);
    }

    public RelativeLayout getContainer() {
        return mContainer;
    }

    public void bindView(Context context, Result result) {
        mTitle.setText(result.getTitle());
        String currencySymbol = Currency.getInstance(result.getCurrencyCode())
                .getSymbol(context.getResources().getConfiguration().locale);
        mPrice.setText(currencySymbol + result.getPrice());
        Glide.with(context)
                .load(result.getMainImage().getFullUrl())
                .into(mImageView);
    }
}
