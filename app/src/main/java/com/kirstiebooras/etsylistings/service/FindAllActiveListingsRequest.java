package com.kirstiebooras.etsylistings.service;

import android.net.Uri;

import com.kirstiebooras.etsylistings.config.Config;

public class FindAllActiveListingsRequest extends EtsyBaseRequest {

    private static final String FIND_ALL_SHOP_LISTINGS_PATH = "listings/active";

    private String mSearchKey;
    private int mOffset;
    private int mLimit;

    private FindAllActiveListingsRequest(String searchKey, int offset, int limit) {
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

        public FindAllActiveListingsRequest build() {
            return new FindAllActiveListingsRequest(mSearchKey, mOffset, mLimit);
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
                .appendQueryParameter(KEYWORDS_QUERY_PARAM, mSearchKey.replaceAll("\\s+", "+"));
        return uriBuilder.build().toString();
    }

}
