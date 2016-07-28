package com.app.deemounus.musiccards.provider.musiccards;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.deemounus.musiccards.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code music_cards} table.
 */
public class MusicCardsCursor extends AbstractCursor implements MusicCardsModel {
    public MusicCardsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MusicCardsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Path to picture.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getPicture() {
        String res = getStringOrNull(MusicCardsColumns.PICTURE);
        if (res == null)
            throw new NullPointerException("The value of 'picture' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Path to music file.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getMusic() {
        String res = getStringOrNull(MusicCardsColumns.MUSIC);
        if (res == null)
            throw new NullPointerException("The value of 'music' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
