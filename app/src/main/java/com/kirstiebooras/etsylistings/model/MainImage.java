package com.kirstiebooras.etsylistings.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO encapsulating search result's image.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainImage {

    @JsonProperty("url_fullxfull")
    private String mFullUrl;

    public MainImage() {
    }

    public void setFullUrl(String url) {
        mFullUrl = url;
    }

    public String getFullUrl() {
        return mFullUrl;
    }

}
