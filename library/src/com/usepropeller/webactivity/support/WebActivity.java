package com.usepropeller.webactivity.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import com.usepropeller.webactivity.WebActivityOptions;

/**
 * Created by clayallsopp on 8/4/13.
 */
public class WebActivity extends ActionBarActivity {
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, fragment);
        ft.commit();
        return fragment;
    }
}
