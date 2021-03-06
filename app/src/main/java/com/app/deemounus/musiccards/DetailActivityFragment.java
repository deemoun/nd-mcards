package com.app.deemounus.musiccards;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements View.OnClickListener  {

    public DetailActivityFragment() {
    }

    String LOG_TAG = getClass().getSimpleName();
    Context ctx;
    private MediaPlayback mp;
    private Tracker mTracker;
    private String imgURL;
    private String musicURL;

    public Intent getIntentValue(){
        return getActivity().getIntent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ctx = getContext();
        // Obtain the shared Tracker instance.
        AnalyticsTracker application = (AnalyticsTracker) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        if(getResources().getBoolean(R.bool.isTablet)){
            Bundle bundle = this.getArguments();
            imgURL = bundle.getString("imgUrl");
            musicURL = bundle.getString("musicUrl");
        } else {
            Intent intent = getIntentValue();
            imgURL = intent.getStringExtra("imgUrl");
            musicURL = intent.getStringExtra("musicUrl");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Utils.prepareMetricsForActivity(LOG_TAG, mTracker);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getContext();
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
//        Log.v("Image URL is: ", imgUrl);
        ImageButton play = (ImageButton) v.findViewById(R.id.playSong);
        play.setOnClickListener(this);
        ImageButton stop = (ImageButton) v.findViewById(R.id.stopSong);
        ImageView image = (ImageView) v.findViewById(R.id.imageView);
        stop.setOnClickListener(this);

        Picasso.with(ctx)
                .load(imgURL)
                .resize(400,400)
                .error(R.drawable.image_placeholder)
                .centerCrop()
                .into(image);
        return v;
    }

    @Override
    public void onClick(View v) {
//        Log.v("Media URL is: ", musicUrl);
        mp = new MediaPlayback(ctx, musicURL);
        switch (v.getId()) {
            case R.id.playSong:
                Utils.sendMetricsForAction("playSongButtonPressed", LOG_TAG, mTracker);
                mp.playMedia();
                break;
            case R.id.stopSong:
                Utils.sendMetricsForAction("stopSongButtonPressed", LOG_TAG, mTracker);
                mp.stopMedia();
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mp != null){
            mp.stopMedia();
            mp = null;
        }
    }
}
