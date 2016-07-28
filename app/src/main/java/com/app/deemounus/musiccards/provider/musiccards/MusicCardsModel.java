package com.app.deemounus.musiccards.provider.musiccards;

import com.app.deemounus.musiccards.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Music cards. Path to picture and path to music
 */
public interface MusicCardsModel extends BaseModel {

    /**
     * Path to picture.
     * Cannot be {@code null}.
     */
    @NonNull
    String getPicture();

    /**
     * Path to music file.
     * Cannot be {@code null}.
     */
    @NonNull
    String getMusic();
}
