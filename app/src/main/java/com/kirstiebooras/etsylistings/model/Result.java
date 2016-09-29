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

}
