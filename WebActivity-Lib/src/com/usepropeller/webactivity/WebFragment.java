package com.usepropeller.webactivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.Map;

/**
 * Created by clayallsopp on 8/4/13.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WebFragment extends Fragment implements WebFragmentable {

    private SharedWebFragment _shared;

    public WebFragment() {
        super();
        this.createSharedWebFragment();
    }

    public void createSharedWebFragment() {
        this._shared = new SharedWebFragment(this);
    }

    /////////////////////////////////
    // Fragment Lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        View contentView = this._shared.onCreateView(inflater, container, savedInstanceState);

        return contentView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle extras = this.getArguments();
        this._shared.loadFromBundle(extras);
    }

    @Override
    public void onResume() {
        super.onResume();

        this._shared.onResume();
    }

    @Override
    public void onPause() {
        this._shared.onPause();

        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        this._shared.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this._shared.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /////////////////////////////////
    // Actions

    public void startIntent(Intent intent) {
        startActivity(intent);
    }

    public void onError(int errorCode, String description, final String failingUrl) {
        Log.e("WebFragment", description + " " + failingUrl);
        final WebView webView = this._shared.getWebView();
        webView.setVisibility(View.INVISIBLE);

        RetryDialogFragment fragment = new RetryDialogFragment() {
            @Override
            public void onRetry() {
                webView.setVisibility(View.VISIBLE);
                WebFragment.this._shared.setUrl(failingUrl);
            }
        };
        if (this.getFragmentManager() != null) {
            fragment.show(this.getFragmentManager(), RetryDialogFragment.ID);
        }

        this.setTitle("Error");
    }

    /////////////////////////////////
    // Getters

    public Map<String, String> getHttpHeaders() {
        return null;
    }

    @Override
    public Activity getWebActivity() {
        return this.getActivity();
    }

    /////////////////////////////////
    // Setters

    @Override
    public void setTitle(String title) {
        ActionBar actionBar = this.getActivity().getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);

        this.getActivity().setTitle(title);
    }
}
