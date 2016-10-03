package com.kirstiebooras.etsylistings.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO encapsulating search result.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeValue(mMainImage);
        dest.writeString(mCurrencyCode);
        dest.writeString(mPrice);
        dest.writeInt(mListingId);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    private Result(Parcel in) {
        mTitle = in.readString();
        mMainImage = (MainImage) in.readValue(MainImage.class.getClassLoader());
        mCurrencyCode = in.readString();
        mPrice = in.readString();
        mListingId = in.readInt();
    }
}
