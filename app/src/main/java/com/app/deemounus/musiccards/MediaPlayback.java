package com.app.deemounus.musiccards;

import android.app.Application;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

public class MediaPlayback extends Application {

    static Context ctx;
    static String mUrl;

    public MediaPlayback (Context context, String musicUrl) {
        ctx = context;
        mUrl = musicUrl;
    }

    public void playMedia() {
//        MediaPlayer mediaPlayer = MediaPlayer.create(ctx, Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/Music/2016%20-%20Septima%20%E2%80%94%20%D0%A1%D0%BB%D0%BE%D1%82/02.%20%D0%9C%D0%BE%D1%87%D0%B8%D1%82%20%D0%BA%D0%B0%D0%BA%20%D1%85%D0%BE%D1%87%D0%B5%D1%82!.mp3"));
//        if(!mediaPlayer.isPlaying()) {
//            mediaPlayer.start();
//        } else if (mediaPlayer.isPlaying()) {
//            mediaPlayer.stop();
//        }
        MediaPlayer mediaPlayer = new  MediaPlayer();
        //String filePath = "file:///storage/emulated/0/Music/2016%20-%20Septima%20%E2%80%94%20%D0%A1%D0%BB%D0%BE%D1%82/03.%20%D0%A0%D1%83%D1%81%D1%81%D0%BA%D0%B0%D1%8F%20%D0%B4%D1%83%D1%88%D0%B0.mp3";
        try {
            mediaPlayer.setDataSource(mUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

    public void stopMedia() {
    }
}
