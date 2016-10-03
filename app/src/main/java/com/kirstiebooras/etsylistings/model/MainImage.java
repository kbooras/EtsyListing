package com.kirstiebooras.etsylistings.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO encapsulating search result's image.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainImage implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFullUrl);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MainImage createFromParcel(Parcel in) {
            return new MainImage(in);
        }

        public MainImage[] newArray(int size) {
            return new MainImage[size];
        }
    };

    private MainImage(Parcel in) {
        mFullUrl = in.readString();
    }
}
