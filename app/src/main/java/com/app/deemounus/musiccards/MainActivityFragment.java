package com.app.deemounus.musiccards;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.deemounus.musiccards.provider.musiccards.MusicCardsColumns;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsSelection;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public MainActivityFragment() {
    }

    boolean activityHasData;
    private List<String> pictureUrlList = new ArrayList<String>();
    private List<String> musicUrlList = new ArrayList<String>();
    private String[] cardsImgArray;
    private String[] cardsMusArray;
    private boolean ismTwoPane;
    String LOG_TAG = getClass().getSimpleName();
    String FILE_APPEND = "file://";
    Context ctx;

    private View populateFragmentData(LayoutInflater inflater, ViewGroup container){
        //TODO: Add loader that checks if there is data
        activityHasData = true;

        // Showing the layout depending if user has data for cards or not

        if(activityHasData){
            Log.v(LOG_TAG,"Activity has cards data");
            return inflater.inflate(R.layout.fragment_main_data, container, false);
        } else {
            Log.v(LOG_TAG,"Activity has no cards data");
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getBoolean(R.bool.isTablet)){
            ismTwoPane = true;
            Log.v("Is tablet layout?", Boolean.toString(ismTwoPane));
        } else {
            ismTwoPane = false;
            Log.v("Is tablet layout?", Boolean.toString(ismTwoPane));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initiating the loader
        getLoaderManager().initLoader(1, null, this);
        ctx = getContext();
        View v = populateFragmentData(inflater, container);
        super.onCreate(savedInstanceState);
        return v;
    }

    private List<MusicCardsData> createCards() {

        List<MusicCardsData> result = new ArrayList<>();
        for (int i=0; i <= cardsImgArray.length-1; i++) {
            MusicCardsData cd = new MusicCardsData();
            cd.cardPicture = cardsImgArray[i];
            cd.cardMusic = cardsMusArray[i];
            result.add(cd);
            Log.v(LOG_TAG, cd.cardPicture);
            Log.v(LOG_TAG, cd.cardMusic);
        }
        return result;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri CONTENT_URI = MusicCardsColumns.CONTENT_URI;
        MusicCardsSelection where = new MusicCardsSelection();
        where.orderById();
        return new CursorLoader(getContext(), CONTENT_URI, null, where.sel(), where.args(), null);
    }

    public void startLoadingCards(){
        if(getView().findViewById(R.id.cardList).isEnabled() && activityHasData) {
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.cardList);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(ctx);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            // Creating MusicCards inside RecyclerView
            MusicCardsAdapter cardsAdapter = new MusicCardsAdapter(createCards(), ismTwoPane);
            recyclerView.setAdapter(cardsAdapter);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
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
                //  If there is a data, create cards
                startLoadingCards();
            }
        } else {
            Log.v(LOG_TAG, "Cursor is closed");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
