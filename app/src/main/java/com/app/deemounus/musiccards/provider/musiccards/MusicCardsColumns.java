package com.app.deemounus.musiccards.provider.musiccards;

import android.net.Uri;
import android.provider.BaseColumns;

import com.app.deemounus.musiccards.provider.McardsProvider;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsColumns;

/**
 * Music cards. Path to picture and path to music
 */
public class MusicCardsColumns implements BaseColumns {
    public static final String TABLE_NAME = "music_cards";
    public static final Uri CONTENT_URI = Uri.parse(McardsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Path to picture.
     */
    public static final String PICTURE = "picture";

    /**
     * Path to music file.
     */
    public static final String MUSIC = "music";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            PICTURE,
            MUSIC
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(PICTURE) || c.contains("." + PICTURE)) return true;
            if (c.equals(MUSIC) || c.contains("." + MUSIC)) return true;
        }
        return false;
    }

}
