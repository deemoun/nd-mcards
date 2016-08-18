package com.app.deemounus.musiccards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd interstitial;
    String LOG_TAG = getClass().getSimpleName();
    private Tracker mTracker;

    public boolean ismTwoPane() {
        if (findViewById(R.id.fragmentDetail) != null) {
            Log.v(LOG_TAG, "Layout is two pane (tablet)");
            return true;
        } else {
            Log.v(LOG_TAG, "Layout is single pain (phone)");
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the shared Tracker instance.
        AnalyticsTracker application = (AnalyticsTracker) getApplication();
        mTracker = application.getDefaultTracker();

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView);

        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.ads_test_id))
                .build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);

        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest);

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // To show the full Ad window on start
//                displayInterstitial();
                Utils.sendMetricsForAction("AdLoaded", LOG_TAG, mTracker);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Utils.prepareMetricsForActivity(LOG_TAG, mTracker);
    }

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }

        if (findViewById(R.id.fragmentDetail) != null) {
            if (ismTwoPane()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentDetail, new EmptyDetailViewFragment(), "TAG")
                        .commit();
            }
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), AddCardActivity.class);
                    startActivity(intent);
                    Utils.sendMetricsForAction("AddCard", LOG_TAG, mTracker);
                }
            });
        } else {
            Log.v(LOG_TAG, "FAB object is null");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about){
            Intent i = new Intent(getBaseContext(), AboutApp.class);
            startActivity(i);
        } else if (id == R.id.action_add_card){
            Intent i = new Intent(getBaseContext(), AddCardActivity.class);
            startActivity(i);
            Utils.sendMetricsForAction("AddCard", LOG_TAG, mTracker);
        }

        return super.onOptionsItemSelected(item);
    }
}
