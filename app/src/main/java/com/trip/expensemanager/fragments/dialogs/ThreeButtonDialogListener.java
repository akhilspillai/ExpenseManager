package com.trip.expensemanager.fragments.dialogs;

import android.support.v4.app.DialogFragment;


public interface ThreeButtonDialogListener {
    public void onDialogFirstButtonClick(DialogFragment dialog);

    public void onDialogSecondButtonClick(DialogFragment dialog);

    public void onDialogThirdButtonClick(DialogFragment dialog);
}
