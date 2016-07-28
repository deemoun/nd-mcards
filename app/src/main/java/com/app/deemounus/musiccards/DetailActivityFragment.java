package com.app.deemounus.musiccards;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements View.OnClickListener {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        Button play = (Button) v.findViewById(R.id.playSong);
        play.setOnClickListener(this);
        Button stop = (Button) v.findViewById(R.id.stopSong);
        ImageView image = (ImageView) v.findViewById(R.id.imageView);
        stop.setOnClickListener(this);

        Picasso.with(getContext())
                .load("https://instagram.fsjc1-2.fna.fbcdn.net/t51.2885-15/e35/13712710_1812879398936071_1672603043_n.jpg")
                .into(image);
        return v;
    }

    @Override
    public void onClick(View v) {
        MediaPlayback mp = new MediaPlayback(getContext());
        switch (v.getId()) {
            case R.id.playSong:
                mp.playMedia();
                break;
            case R.id.stopSong:
                    mp.stopMedia();
        }
    }
}
