package com.app.deemounus.musiccards.provider.musiccards;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.app.deemounus.musiccards.provider.base.AbstractSelection;

/**
 * Selection for the {@code music_cards} table.
 */
public class MusicCardsSelection extends AbstractSelection<MusicCardsSelection> {
    @Override
    protected Uri baseUri() {
        return MusicCardsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MusicCardsCursor} object, which is positioned before the first entry, or null.
     */
    public MusicCardsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MusicCardsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MusicCardsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MusicCardsCursor} object, which is positioned before the first entry, or null.
     */
    public MusicCardsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MusicCardsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MusicCardsCursor query(Context context) {
        return query(context, null);
    }


    public MusicCardsSelection id(long... value) {
        addEquals("music_cards." + MusicCardsColumns._ID, toObjectArray(value));
        return this;
    }

    public MusicCardsSelection idNot(long... value) {
        addNotEquals("music_cards." + MusicCardsColumns._ID, toObjectArray(value));
        return this;
    }

    public MusicCardsSelection orderById(boolean desc) {
        orderBy("music_cards." + MusicCardsColumns._ID, desc);
        return this;
    }

    public MusicCardsSelection orderById() {
        return orderById(false);
    }

    public MusicCardsSelection picture(String... value) {
        addEquals(MusicCardsColumns.PICTURE, value);
        return this;
    }

    public MusicCardsSelection pictureNot(String... value) {
        addNotEquals(MusicCardsColumns.PICTURE, value);
        return this;
    }

    public MusicCardsSelection pictureLike(String... value) {
        addLike(MusicCardsColumns.PICTURE, value);
        return this;
    }

    public MusicCardsSelection pictureContains(String... value) {
        addContains(MusicCardsColumns.PICTURE, value);
        return this;
    }

    public MusicCardsSelection pictureStartsWith(String... value) {
        addStartsWith(MusicCardsColumns.PICTURE, value);
        return this;
    }

    public MusicCardsSelection pictureEndsWith(String... value) {
        addEndsWith(MusicCardsColumns.PICTURE, value);
        return this;
    }

    public MusicCardsSelection orderByPicture(boolean desc) {
        orderBy(MusicCardsColumns.PICTURE, desc);
        return this;
    }

    public MusicCardsSelection orderByPicture() {
        orderBy(MusicCardsColumns.PICTURE, false);
        return this;
    }

    public MusicCardsSelection music(String... value) {
        addEquals(MusicCardsColumns.MUSIC, value);
        return this;
    }

    public MusicCardsSelection musicNot(String... value) {
        addNotEquals(MusicCardsColumns.MUSIC, value);
        return this;
    }

    public MusicCardsSelection musicLike(String... value) {
        addLike(MusicCardsColumns.MUSIC, value);
        return this;
    }

    public MusicCardsSelection musicContains(String... value) {
        addContains(MusicCardsColumns.MUSIC, value);
        return this;
    }

    public MusicCardsSelection musicStartsWith(String... value) {
        addStartsWith(MusicCardsColumns.MUSIC, value);
        return this;
    }

    public MusicCardsSelection musicEndsWith(String... value) {
        addEndsWith(MusicCardsColumns.MUSIC, value);
        return this;
    }

    public MusicCardsSelection orderByMusic(boolean desc) {
        orderBy(MusicCardsColumns.MUSIC, desc);
        return this;
    }

    public MusicCardsSelection orderByMusic() {
        orderBy(MusicCardsColumns.MUSIC, false);
        return this;
    }
}
