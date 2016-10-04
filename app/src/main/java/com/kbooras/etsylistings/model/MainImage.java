package com.kbooras.etsylistings.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.Validate;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * POJO encapsulating search result's image.
 */
public class MainImage implements Parcelable {

    private String mFullUrl;

    public MainImage(JSONObject object) throws JSONException, NullPointerException {
        String fullUrl = object.getString("url_fullxfull");
        init(fullUrl);
    }

    private void init(String fullUrl) throws NullPointerException {
        Validate.notNull(fullUrl, "Full URL cannot be null");
        mFullUrl = fullUrl;
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
