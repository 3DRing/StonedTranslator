package com.ringov.yatrnsltr;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class MessageDialogHelper {
    public static MaterialDialog getErrorDialog(Context context, String title, String message) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .onPositive((dialog, which) -> dialog.dismiss())
                .positiveText(R.string.ok)
                .cancelable(true)
                .build();
    }
}
