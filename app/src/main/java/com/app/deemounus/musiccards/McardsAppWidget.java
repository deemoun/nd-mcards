package com.app.deemounus.musiccards;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

/**
 * Implementation of App Widget functionality.
 */

public class McardsAppWidget extends AppWidgetProvider {

//    private void initData(Context context) {
//        mMatchTime.clear();
//
//        Cursor mCursor;
//
//        McardsSQLiteOpenHelper mOpenHelper = new McardsSQLiteOpenHelper(context.getContentResolver());
//
//        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + DatabaseContract.SCORES_TABLE + " ORDER BY date DESC";
//        mCursor = db.rawQuery(selectQuery, null);
//
//        if (mCursor.getCount() > 0) {
//            mCursor.moveToFirst();
//        } try {
//            do {
//                mMatchTime.add(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.scores_table.TIME_COL)));
//
//            } while (mCursor.moveToNext());
//        } catch (CursorIndexOutOfBoundsException e){
//            Log.v("Football Scores Widget", "Problem loading football scores (connection issues?): " + e.getLocalizedMessage());
//        } finally {
//            if(!mCursor.isClosed())
//                mCursor.close();
//        }
//    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mcards_app_widget);
        setRemoteAdapter(context, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        context.stopService(new Intent(context, RemoteFactoryService.class));
    }

    private static void setRemoteAdapter (Context context, @NonNull final RemoteViews views){
        views.setRemoteAdapter(R.id.widget_list, new Intent(context,RemoteFactoryService.class));
    }
}

