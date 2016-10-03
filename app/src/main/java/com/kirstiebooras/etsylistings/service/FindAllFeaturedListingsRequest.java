package com.kirstiebooras.etsylistings.service;

import android.net.Uri;

import com.kirstiebooras.etsylistings.config.Config;

/**
 * Request for Etsy findAllFeaturedListings API. Returns an array of featured listings.
 */
public class FindAllFeaturedListingsRequest extends EtsyBaseRequest {

    private static final String FIND_ALL_FEATURED_LISTINGS_PATH = "featured_treasuries/listings";

    private FindAllFeaturedListingsRequest(int offset, int limit) {
        super(offset, limit);
    }

    public static class Builder {

        private static final int DEFAULT_OFFSET = 0;
        private static final int DEFAULT_LIMIT = 24;

        private int mOffset;
        private int mLimit;

        public Builder() {
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

        public FindAllFeaturedListingsRequest build() {
            return new FindAllFeaturedListingsRequest(mOffset, mLimit);
        }
    }

    public String getFeaturedListingsUrl() {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(SCHEME)
                .encodedAuthority(ENDPOINT)
                .encodedPath(FIND_ALL_FEATURED_LISTINGS_PATH)
                .appendQueryParameter(INCLUDES_QUERY_PARAM, MAIN_IMAGE_ATTRIBUTE)
                .appendQueryParameter(API_KEY_QUERY_PARAM, Config.ETSY_API_KEY)
                .appendQueryParameter(LIMIT_QUERY_PARAM, String.valueOf(mLimit))
                .appendQueryParameter(OFFSET_QUERY_PARAM, String.valueOf(mOffset));
        return uriBuilder.build().toString();
    }
}
