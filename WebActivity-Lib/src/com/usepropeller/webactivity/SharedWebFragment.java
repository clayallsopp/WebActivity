package com.usepropeller.webactivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by clayallsopp on 8/4/13.
 */
public class SharedWebFragment {
    private WebView _webView;
    private boolean _webViewLoading;

    // Used to track what URLs/HTML needs loading
    private String _pendingUrl;
    private String _pendingHtml;

    private Menu _menu;

    private WebFragmentable _fragment;

    public SharedWebFragment(WebFragmentable fragment) {
        super();
        this._fragment = fragment;
    }

    public void loadFromBundle(Bundle extras) {
        if (extras.containsKey(WebActivityOptions.URL)) {
            this.setPendingUrl(extras.getString(WebActivityOptions.URL));
        }
        else if (extras.containsKey(WebActivityOptions.HTML)) {
            this.setPendingHtml(extras.getString(WebActivityOptions.HTML));
        }
    }

    public void onResume() {
        this.getWebView().setWebViewClient(new WebFragmentClient(this));

        if (this.getPendingHtml() != null) {
            this.setHtml(this.getPendingHtml());
            this.setPendingHtml(null);
        }
        else if (this.getPendingUrl() != null) {
            this.setUrl(this.getPendingUrl());
            this.setPendingUrl(null);
        }
    }

    public void onPause() {
        this.getWebView().setWebViewClient(null);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.web_fragment, menu);
        this.setMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            return true;
        }
        else if (id == R.id.web_menu_back) {
            this.getWebView().goBack();
            return true;
        }
        else if (id == R.id.web_menu_forward) {
            this.getWebView().goForward();
            return true;
        }
        else if (id == R.id.web_menu_cancel) {
            this.cancelOrReload();
            return true;
        }
        else if (id == R.id.web_menu_share) {
            this.openShareAction(item);
            return true;
        }
        else if (id == R.id.web_menu_copy) {
            this.copyUrl();
            return true;
        }
        else if (id == R.id.web_menu_browser) {
            this.openInBrowser();
            return true;
        }

        return false;
    }

    public void openShareAction(MenuItem item) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, this.getWebView().getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, this.getWebView().getUrl());
        this._fragment.startIntent(Intent.createChooser(shareIntent, "Share to..."));
    }

    public void copyUrl() {
        Activity activity = this._fragment.getWebActivity();
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Activity.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("url", this.getWebView().getUrl()));
        Toast.makeText(activity, "URL copied", Toast.LENGTH_SHORT).show();
    }

    public void openInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.getWebView().getUrl()));
        this._fragment.startIntent(intent);
    }

    public void cancelOrReload() {
        if (this.isLoading()) {
            this.getWebView().stopLoading();
        }
        else {
            this.getWebView().reload();
        }
    }

    public void updateButtonState() {
        Menu menu = this.getMenu();
        WebView webView = this.getWebView();
        if (menu == null) {
            return;
        }

        MenuItem backButton = menu.findItem(R.id.web_menu_back);
        backButton.setEnabled(webView.canGoBack());

        MenuItem forwardButton = menu.findItem(R.id.web_menu_forward);
        forwardButton.setEnabled(webView.canGoForward());

        MenuItem cancelButton = menu.findItem(R.id.web_menu_cancel);
        if (this.isLoading()) {
            cancelButton.setIcon(R.drawable.ic_1_navigation_cancel);
        }
        else {
            cancelButton.setIcon(R.drawable.ic_menu_refresh);
        }

        if (!this.isLoading()) {
            this._fragment.setTitle(this.getWebView().getTitle());
        }
        else {
            this._fragment.setTitle("Loading");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout contentView = (FrameLayout) inflater.inflate(R.layout.web_fragment, container, false);

        this.setWebView((WebView) contentView.findViewById(R.id.web_view));

        return contentView;
    }

    /////////////////////////////////
    // Getters
    public boolean isLoading() {
        return this._webViewLoading;
    }

    public String getPendingHtml() {
        return this._pendingHtml;
    }

    public String getPendingUrl() {
        return this._pendingUrl;
    }

    public Menu getMenu() {
        return this._menu;
    }

    public WebView getWebView() {
        return this._webView;
    }

    /////////////////////////////////
    // Setters
    public void setPendingUrl(String pendingUrl) {
        this._pendingUrl = pendingUrl;
    }

    public void setUrl(String url) {
        if (this.getWebView() != null) {
            this.getWebView().loadUrl(url, this._fragment.getHttpHeaders());
        }
    }

    public void setPendingHtml(String pendingHtml) {
        this._pendingHtml = pendingHtml;
    }

    public void setHtml(String html) {
        this.getWebView().loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    public void setIsLoading(boolean isLoading) {
        this._webViewLoading = isLoading;
        this.updateButtonState();
    }

    public void setMenu(Menu menu) {
        this._menu = menu;
    }

    public void setWebView(WebView webView) {
        this._webView = webView;
    }

    public void onError(int errorCode, String description, String failingUrl) {
        this._fragment.onError(errorCode, description, failingUrl);
    }
}