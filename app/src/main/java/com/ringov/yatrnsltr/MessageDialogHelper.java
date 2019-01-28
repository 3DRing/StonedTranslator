package com.ringov.yatrnsltr;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import kotlin.Unit;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public class MessageDialogHelper {
    public static MaterialDialog getErrorDialog(Context context, String title, String message) {
        MaterialDialog md = new MaterialDialog(context);
        return md
                .title(null, title)
                .message(null, message, false, 1)
                .positiveButton(R.string.ok, "", (dialog) -> {
                    dialog.dismiss();
                    return Unit.INSTANCE;
                })
                .cancelable(true);
    }
}
