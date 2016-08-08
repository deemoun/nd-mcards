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

    private static MediaPlayer mediaInstance = null;
    private boolean playPressed;
    public static MediaPlayer getInstance() {
        if(mediaInstance == null) {
            mediaInstance = new MediaPlayer();
        }
        return mediaInstance;
    }

    public void playMedia() {

        //String filePath = "file:///storage/emulated/0/Music/2016%20-%20Septima%20%E2%80%94%20%D0%A1%D0%BB%D0%BE%D1%82/03.%20%D0%A0%D1%83%D1%81%D1%81%D0%BA%D0%B0%D1%8F%20%D0%B4%D1%83%D1%88%D0%B0.mp3";
        if (mediaInstance == null && !playPressed) {
            try {
                MediaPlayback.getInstance();
                mediaInstance.setDataSource(mUrl);
                mediaInstance.prepare();
                mediaInstance.start();
                playPressed = true;
            } catch (IOException e) {
                e.getLocalizedMessage();
            }
        } else if (!playPressed ) {
            stopMedia();
            restartStream();
        }
    }

    public void stopMedia() {
        if (mediaInstance != null) {
            mediaInstance.stop();
            playPressed = false;
        }
    }

    public void restartStream(){
        try {
            mediaInstance = null;
            MediaPlayback.getInstance();
            mediaInstance.setDataSource(mUrl);
            mediaInstance.prepare();
            mediaInstance.start();
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

}
