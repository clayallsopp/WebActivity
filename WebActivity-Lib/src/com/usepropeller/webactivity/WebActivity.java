package com.usepropeller.webactivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by clayallsopp on 8/4/13.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WebActivity extends Activity {
    private WebFragment _fragment;

    public static Intent forUrl(String url, Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebActivityOptions.URL, url);
        return intent;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._fragment = this.createWebFragment();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (this._fragment.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public WebFragment getFragment() {
        return this._fragment;
    }

    public WebFragment createWebFragment() {
        WebFragment fragment = new WebFragment();
        fragment.setArguments(this.getIntent().getExtras());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.content, fragment);
        ft.commit();
        return fragment;
    }
}
