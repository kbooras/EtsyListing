package com.kirstiebooras.etsylistings.service;

import android.net.Uri;

import com.kirstiebooras.etsylistings.config.Config;

/**
 * Request for Etsy findAllListingActive API. Returns an array of active listings.
 */
public class FindAllListingActiveRequest extends EtsyBaseRequest {

    private static final String FIND_ALL_SHOP_LISTINGS_PATH = "listings/active";

    private static final String KEYWORDS_QUERY_PARAM = "keywords";
    private static final String SORT_ON_QUERY_PARAM = "sort_on";
    private static final String SORT_ON_SCORE = "score";

    private String mSearchKey;
    private int mOffset;
    private int mLimit;

    private FindAllListingActiveRequest(String searchKey, int offset, int limit) {
        mSearchKey = searchKey;
        mOffset = offset;
        mLimit = limit;
    }

    public static class Builder {

        private static final int DEFAULT_OFFSET = 0;
        private static final int DEFAULT_LIMIT = 25;

        private String mSearchKey;
        private int mOffset;
        private int mLimit;

        public Builder(String searchKey) {
            mSearchKey = searchKey;
            mOffset = DEFAULT_OFFSET;
            mLimit = DEFAULT_LIMIT;
        }

        public Builder withOffset(int offset) {
            mOffset = offset;
            return this;
        }

        public Builder withLimit(int limit) {
            mLimit = limit;
            return this;
        }

        public FindAllListingActiveRequest build() {
            return new FindAllListingActiveRequest(mSearchKey, mOffset, mLimit);
        }
    }

    public String getSearchUrl() {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(SCHEME)
                .encodedAuthority(ENDPOINT)
                .encodedPath(FIND_ALL_SHOP_LISTINGS_PATH)
                .appendQueryParameter(INCLUDES_QUERY_PARAM, MAIN_IMAGE_ATTRIBUTE)
                .appendQueryParameter(API_KEY_QUERY_PARAM, Config.ETSY_API_KEY)
                .appendQueryParameter(LIMIT_QUERY_PARAM, String.valueOf(mLimit))
                .appendQueryParameter(OFFSET_QUERY_PARAM, String.valueOf(mOffset))
                .appendQueryParameter(SORT_ON_QUERY_PARAM, SORT_ON_SCORE)
                .appendQueryParameter(KEYWORDS_QUERY_PARAM, mSearchKey.replaceAll("\\s+", "+"));
        return uriBuilder.build().toString();
    }

}