package com.usepropeller.webactivity;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by clayallsopp on 8/4/13.
 */
public class WebFragmentClient extends WebViewClient {
    SharedWebFragment _shared;

    public WebFragmentClient(SharedWebFragment shared) {
        super();
        this._shared = shared;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        this._shared.setIsLoading(true);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        this._shared.setIsLoading(false);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // Always load the URL with our headers
        this._shared.setUrl(url);
        return true;
    }

    @Override
    public void onReceivedError (WebView view, int errorCode, String description, String failingUrl) {
        this._shared.setIsLoading(false);
        this._shared.onError(errorCode, description, failingUrl);
    }
}
