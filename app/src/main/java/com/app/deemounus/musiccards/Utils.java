package com.app.deemounus.musiccards;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils extends Application {

     public static void showToast(Context ctx, String toastText){
        Toast.makeText(ctx, toastText, Toast.LENGTH_SHORT).show();
    }

    public static void prepareMetricsForActivity(String LOG_TAG, Tracker mTracker){
        Log.i(LOG_TAG, "Setting screen name: " + LOG_TAG);
        mTracker.setScreenName("Screen name: " + LOG_TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static void sendMetricsForAction(String actionMetric, String LOG_TAG, Tracker mTracker) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(LOG_TAG)
                .setAction(actionMetric)
                .build());
    }

    public static void setSharedPrefsBooleanValue(String booleanName, Boolean value, Context ctx){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(booleanName, value);
        editor.apply();
    }

    public static String readRawTextFile(Context ctx, int resId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    public static void showLicenseDialog(Context ctx, int filename){
        new AlertDialog.Builder(ctx)
                .setTitle(R.string.license_header)
                .setMessage(Utils.readRawTextFile(ctx, filename))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static Boolean getSharedPrefsBooleanValue(String booleanName, Context ctx){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        Boolean booleanValue = preferences.getBoolean(booleanName, false);
        Log.v("Utils", "Preference value" + booleanValue);
        return booleanValue;
    }
}
