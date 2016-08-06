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
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements View.OnClickListener  {

    String LOG_TAG = getClass().getSimpleName();
    Context ctx;

    public DetailActivityFragment() {
    }

    public Intent getIntentValue(){
        return getActivity().getIntent();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getContext();
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getIntentValue();
        String imgUrl = intent.getStringExtra("imgUrl");
        Log.v("Image URL is: ", imgUrl);
        Button play = (Button) v.findViewById(R.id.playSong);
        play.setOnClickListener(this);
        Button stop = (Button) v.findViewById(R.id.stopSong);
        ImageView image = (ImageView) v.findViewById(R.id.imageView);
        stop.setOnClickListener(this);

        Picasso.with(ctx)
                .load(imgUrl)
                .into(image);
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntentValue();
        String musicUrl = intent.getStringExtra("musicUrl");
        Log.v("Media URL is: ", musicUrl);
        MediaPlayback mp = new MediaPlayback(ctx, musicUrl);
        switch (v.getId()) {
            case R.id.playSong:
                mp.playMedia();
                break;
            case R.id.stopSong:
                    mp.stopMedia();
        }
    }
}
