package com.mango.autumnleaves.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.mango.autumnleaves.R;

public class CustomLoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    public CustomLoadingDialog(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_loading,null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
