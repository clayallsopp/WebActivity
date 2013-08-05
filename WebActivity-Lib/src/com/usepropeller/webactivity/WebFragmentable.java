package com.usepropeller.webactivity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.Map;

/**
 * Created by clayallsopp on 8/4/13.
 */
public interface WebFragmentable {
    public void createSharedWebFragment();
    public Map<String,String> getHttpHeaders();

    public void onError(int errorCode, String description, String failingUrl);

    public void startIntent(Intent intent);

    public void setTitle(String title);

    public Activity getWebActivity();
}
