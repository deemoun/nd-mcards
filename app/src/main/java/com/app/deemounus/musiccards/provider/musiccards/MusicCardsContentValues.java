package com.app.deemounus.musiccards.provider.musiccards;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.deemounus.musiccards.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code music_cards} table.
 */
public class MusicCardsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MusicCardsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MusicCardsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MusicCardsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Path to picture.
     */
    public MusicCardsContentValues putPicture(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("picture must not be null");
        mContentValues.put(MusicCardsColumns.PICTURE, value);
        return this;
    }


    /**
     * Path to music file.
     */
    public MusicCardsContentValues putMusic(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("music must not be null");
        mContentValues.put(MusicCardsColumns.MUSIC, value);
        return this;
    }

}
