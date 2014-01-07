package com.usepropeller.webactivity.support;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by clayallsopp on 8/4/13.
 */
public abstract class RetryDialogFragment extends DialogFragment {
    public static String ID = "RETRY";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(this.getActivity())
                .setTitle("Error")
                .setMessage("An error occured while loading the page; retry?")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onRetry();
                        dismiss();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();
    }

    public abstract void onRetry();
}

