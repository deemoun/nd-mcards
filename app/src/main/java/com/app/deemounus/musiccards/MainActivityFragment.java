package com.app.deemounus.musiccards;

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
    String LOG_TAG = getClass().getSimpleName();
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

    private String[] cardsImagesArray = {"https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Wiktionary_small.svg/350px-Wiktionary_small.svg.png","https://instagram.fsjc1-2.fna.fbcdn.net/t51.2885-15/e35/13712710_1812879398936071_1672603043_n.jpg"};
    private String[] cardsMusicArray = {"file:///storage/emulated/0/Music/2016%20-%20Septima%20%E2%80%94%20%D0%A1%D0%BB%D0%BE%D1%82/03.%20%D0%A0%D1%83%D1%81%D1%81%D0%BA%D0%B0%D1%8F%20%D0%B4%D1%83%D1%88%D0%B0.mp3","file:///storage/emulated/0/Download/sample_song.mp3"};

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

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                pictureUrlList.add("file://" + cursor.getString(cursor.getColumnIndex(MusicCardsColumns.PICTURE)).replace("[", "").replace("]", ""));
                musicUrlList.add(cursor.getString(cursor.getColumnIndex(MusicCardsColumns.MUSIC)).replace("[", "").replace("]", ""));
                cursor.moveToNext();
            }
        } finally {
            cardsImgArray = pictureUrlList.toArray(new String [pictureUrlList.size()]);
            cardsMusArray = musicUrlList.toArray(new String[musicUrlList.size()]);
            cursor.close();
            //         If there is a data, create cards
            if(getView().findViewById(R.id.cardList).isEnabled() && activityHasData) {
                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.cardList);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(ctx);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                // Creating MusicCards inside RecyclerView
                MusicCardsAdapter cardsAdapter = new MusicCardsAdapter(createCards());
                recyclerView.setAdapter(cardsAdapter);
            }
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
