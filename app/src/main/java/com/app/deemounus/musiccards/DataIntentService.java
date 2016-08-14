package com.app.deemounus.musiccards;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.app.deemounus.musiccards.provider.musiccards.MusicCardsColumns;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsSelection;

import java.util.ArrayList;
import java.util.List;

public class DataIntentService extends IntentService {

    List<String> pictureUrlList = new ArrayList<>();
    List<String> musicUrlList = new ArrayList<>();
    String FILE_APPEND = "file://";
    String[] cardsImgArray;
    String[] cardsMusArray;
    CursorLoader mCursorLoader;
    String LOG_TAG = getClass().getSimpleName();

    public DataIntentService() {
        super("DataIntentService");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.v(LOG_TAG, "onCreate is executed from widget!");
        Uri CONTENT_URI = MusicCardsColumns.CONTENT_URI;
        MusicCardsSelection where = new MusicCardsSelection();
        where.orderById();
        mCursorLoader = new CursorLoader(getApplicationContext(), CONTENT_URI, null, where.sel(), where.args(), null);
        mCursorLoader.registerListener(2, new Loader.OnLoadCompleteListener<Cursor>() {

                    @Override
                    public void onLoadComplete(Loader<Cursor> loader, Cursor cursor) {
                        if(!cursor.isClosed()) {
                            cursor.moveToFirst();
                            try {
                                while (!cursor.isAfterLast()) {
                                    pictureUrlList.add(FILE_APPEND + cursor.getString(cursor.getColumnIndex(MusicCardsColumns.PICTURE)).replace("[", "").replace("]", ""));
                                    musicUrlList.add(cursor.getString(cursor.getColumnIndex(MusicCardsColumns.MUSIC)).replace("[", "").replace("]", ""));
                                    cursor.moveToNext();
                                }
                            } finally {
                                cardsImgArray = pictureUrlList.toArray(new String[pictureUrlList.size()]);
                                cardsMusArray = musicUrlList.toArray(new String[musicUrlList.size()]);
                            }
                        } else {
                            Log.v(LOG_TAG, "Cursor is closed");
                        }
                    }
                });
        mCursorLoader.startLoading();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onDestroy() {
        // Stop the cursor loader
        if (mCursorLoader != null) {
//            mCursorLoader.unregisterListener(this);
            mCursorLoader.reset();
            mCursorLoader.cancelLoad();
            mCursorLoader.stopLoading();
        }
    }
}
