package com.app.deemounus.musiccards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.app.deemounus.musiccards.provider.McardsSQLiteOpenHelper;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsColumns;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsSelection;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WidgetDataFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext = null;
    List<String> pictureUrlList = new ArrayList<>();
    List<String> musicUrlList = new ArrayList<>();
    String FILE_APPEND = "file://";
    String[] cardsImgArray;
    String[] cardsMusArray;
    String LOG_TAG = getClass().getSimpleName();
    int xImgSize = 200;
    int yImgSize = 200;
    private Target mTarget;

    public WidgetDataFactory(Context context, Intent intent) {
        mContext = context;
    }


    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        pictureUrlList.clear();
        musicUrlList.clear();

        Cursor mCursor;

        McardsSQLiteOpenHelper mOpenHelper = new McardsSQLiteOpenHelper(mContext);

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        MusicCardsSelection where = new MusicCardsSelection();
        where.orderById();
        mCursor = db.query(MusicCardsColumns.TABLE_NAME, null, null, null, null, null, null);

        try {
            if (!mCursor.isClosed()) {
                mCursor.moveToFirst();
                try {
                    while (!mCursor.isAfterLast()) {
                        pictureUrlList.add(FILE_APPEND + mCursor.getString(mCursor.getColumnIndex(MusicCardsColumns.PICTURE)).replace("[", "").replace("]", ""));
                        musicUrlList.add(mCursor.getString(mCursor.getColumnIndex(MusicCardsColumns.MUSIC)).replace("[", "").replace("]", ""));
                        mCursor.moveToNext();
                    }
                } finally {
                    cardsImgArray = pictureUrlList.toArray(new String[pictureUrlList.size()]);
                    cardsMusArray = musicUrlList.toArray(new String[musicUrlList.size()]);
                    Log.v("WidgetPIC FROM ADAPTER:", cardsImgArray[0]);
                }
            } else {
                Log.v(LOG_TAG, "Cursor is closed");
            }
        } finally {
            mCursor.close();
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return pictureUrlList.size();
    }

    public Bitmap getBitmap(String url) throws IOException {
        Bitmap mBitmap;
        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });

        return mBitmap = builder.build().with(mContext).load(url).resize(xImgSize,yImgSize).centerCrop().get();
    }

    @Override
    public RemoteViews getViewAt(final int position) {
        final RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.list_item_widget);

        try {
            row.setImageViewBitmap(R.id.widget_photo_img, getBitmap(cardsImgArray[position]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
 }
