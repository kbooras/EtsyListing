package com.kirstiebooras.etsylistings.adapter;

/**
 * POJO encapsulating search result.
 */
public class Result {

    private String mTitle;
    private String mImageUrl;

    public Result(String title, String mImageUrl) {
        mTitle = title;
    }

    public String getTitle() {
        return  mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
