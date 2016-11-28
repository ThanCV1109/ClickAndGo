package vn.techplus.clickandgo.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.Display;

import vn.techplus.clickandgo.activity.SplashScreenActivity;

/**
 * Created by ThanCV on 10/23/2015.
 */
public class MyAlertDiaglog {
    private Context context;

    public MyAlertDiaglog(Context context) {
        this.context = context;
    }

    //Alert Dialog
    public void showAlert(String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}
