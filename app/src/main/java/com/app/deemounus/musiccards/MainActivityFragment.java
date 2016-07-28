package com.app.deemounus.musiccards;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String LOG_TAG = getClass().getSimpleName();

    public MainActivityFragment() {
    }

    boolean activityHasData;

    private View populateFragmentData(LayoutInflater inflater, ViewGroup container){
        activityHasData = true;

        // Showing the layout depending if user has data for cards or not

        if(activityHasData){
            return inflater.inflate(R.layout.fragment_main_data, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = populateFragmentData(inflater, container);
        super.onCreate(savedInstanceState);
//         If there is a data, create cards
        if(v.findViewById(R.id.cardList).isEnabled() && activityHasData) {
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.cardList);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            // Creating MusicCards inside RecyclerView
            MusicCardsAdapter cardsAdapter = new MusicCardsAdapter(createCards(3));
            recyclerView.setAdapter(cardsAdapter);
        } else {
            Log.v(LOG_TAG, "WARNING: There is no data, skipping adding cards");
        }
        return v;
    }

    private List<MusicCardsData> createCards(int size) {

        List<MusicCardsData> result = new ArrayList<MusicCardsData>();
        for (int i=1; i <= size; i++) {
            MusicCardsData cd = new MusicCardsData();
            cd.cardPicture = "R.drawable.placeholder";

            result.add(cd);

        }

        return result;
    }
}
