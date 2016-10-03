package com.kirstiebooras.etsylistings.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.Validate;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * POJO encapsulating search result.
 */
public class Result implements Parcelable {

    private String mTitle;
    private String mCurrencyCode;
    private String mPrice;
    private MainImage mMainImage;
    private int mListingId;

    public Result(JSONObject object) throws JSONException, NullPointerException {
        String title = object.getString("title");
        String currencyCode = object.getString("currency_code");
        String price = object.getString("price");
        MainImage mainImage = new MainImage(object.getJSONObject("MainImage"));
        int listingId = object.getInt("listing_id");

        init(title, currencyCode, price, mainImage, listingId);
    }

    private void init(String title, String currencyCode, String price,
                      MainImage mainImage, int listingId)
            throws NullPointerException {
        Validate.notNull(title, "Title cannot be null");
        Validate.notNull(currencyCode, "CurrencyCode cannot be null");
        Validate.notNull(price, "Price cannot be null");
        Validate.notNull(mainImage, "MainImage cannot be null");
        Validate.notNull(listingId, "ListingId cannot be null");
        mTitle = title;
        mCurrencyCode = currencyCode;
        mPrice = price;
        mMainImage = mainImage;
        mListingId = listingId;
    }

    public String getTitle() {
        return  mTitle;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public String getPrice() {
        return mPrice;
    }

    public MainImage getMainImage() {
        return mMainImage;
    }

    public int getListingId() {
        return mListingId;
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
