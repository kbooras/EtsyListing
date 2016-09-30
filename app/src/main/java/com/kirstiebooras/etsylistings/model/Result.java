package com.kirstiebooras.etsylistings.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO encapsulating search result.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("title")
    private String mTitle;

    @JsonProperty("MainImage")
    private MainImage mMainImage;

    @JsonProperty("currency_code")
    private String mCurrencyCode;

    @JsonProperty("price")
    private String mPrice;

    @JsonProperty("listing_id")
    private int mListingId;

    public Result() {
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return  mTitle;
    }

    public void setMainImage(MainImage mainImage) {
        mMainImage = mainImage;
    }

    public MainImage getMainImage() {
        return mMainImage;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        mCurrencyCode = currencyCode;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getPrice() {
        return mPrice;
    }

    public int getListingId() {
        return mListingId;
    }

    public void setListingId(int listingId) {
        mListingId = listingId;
    }
}
