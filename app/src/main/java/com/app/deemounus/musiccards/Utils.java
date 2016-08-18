package com.app.deemounus.musiccards;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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
}
