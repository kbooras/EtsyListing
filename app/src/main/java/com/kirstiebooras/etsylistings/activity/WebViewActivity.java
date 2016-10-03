package com.kirstiebooras.etsylistings.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kirstiebooras.etsylistings.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String LISTING_ID_EXTRA = "listing_id";
    private static final String ETSY_URL = "https://www.etsy.com/listing/%d";

    private WebView mWebView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        int listingId = getIntent().getIntExtra(LISTING_ID_EXTRA, -1);
        String url = String.format(ETSY_URL, listingId);
        mWebView.loadUrl(url);

    }

}
