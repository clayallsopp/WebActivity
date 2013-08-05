package com.usepropeller.webactivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by clayallsopp on 8/4/13.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
