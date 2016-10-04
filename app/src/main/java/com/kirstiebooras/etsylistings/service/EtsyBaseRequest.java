package com.kirstiebooras.etsylistings.service;

/**
 * A base Etsy API request.
 */
class EtsyBaseRequest {

    static final String SCHEME = "https";
    static final String ENDPOINT = "api.etsy.com/v2";
    static final String INCLUDES_QUERY_PARAM = "includes";
    static final String API_KEY_QUERY_PARAM = "api_key";
    static final String LIMIT_QUERY_PARAM = "limit";
    static final String OFFSET_QUERY_PARAM = "offset";
    static final String MAIN_IMAGE_ATTRIBUTE = "MainImage";

    int mOffset;
    int mLimit;

    EtsyBaseRequest(int offset, int limit) {
        mOffset = offset;
        mLimit = limit;
    }
}
