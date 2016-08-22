package com.app.deemounus.musiccards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyDetailViewFragment extends Fragment {

    String LOG_TAG = getClass().getSimpleName();

    public EmptyDetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(LOG_TAG, "Empty detailview fragment for tablet loaded");
        return inflater.inflate(R.layout.fragment_empty_detail_view, container, false);
    }
}
