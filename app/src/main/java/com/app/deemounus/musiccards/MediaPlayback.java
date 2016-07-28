package com.app.deemounus.musiccards;

import android.app.Application;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class MediaPlayback extends Application {

    static Context ctx;

    public MediaPlayback (Context context) {
        ctx = context;
    }

    public void playMedia() {
        MediaPlayer mediaPlayer = MediaPlayer.create(ctx, R.raw.sample_song);
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void stopMedia() {
    }
}
