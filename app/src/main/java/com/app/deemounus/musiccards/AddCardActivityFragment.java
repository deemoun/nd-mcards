package com.app.deemounus.musiccards;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.deemounus.musiccards.provider.musiccards.MusicCardsColumns;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsContentValues;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsSelection;
import com.gun0912.tedpicker.ImagePickerActivity;

public class AddCardActivityFragment extends Fragment {

    public AddCardActivityFragment() {
    }

    String [] projection;

    private static final int INTENT_REQUEST_GET_IMAGES = 13;

    private void getPictures() {

        Intent intent  = new Intent(getActivity(), ImagePickerActivity.class);
        startActivityForResult(intent,INTENT_REQUEST_GET_IMAGES);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_card, container, false);

        Button addPicture = (Button) v.findViewById(R.id.addPicture);
        Button addMusic = (Button) v.findViewById(R.id.addMusic);
        addPicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               getPictures();
            }
        });

        addMusic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
//                MusicCardsSelection where = new MusicCardsSelection();
//                where.music("John");
//                Cursor c = getContext().getContentResolver().query(MusicCardsColumns.CONTENT_URI, projection,
//                        where.sel(), where.args(), null);

                MusicCardsContentValues values = new MusicCardsContentValues();
                values.putMusic("http://music_link").putPicture("http://picture_link");
                getContext().getContentResolver().insert(MusicCardsColumns.CONTENT_URI, values.values());
//                getContext().getContentResolver().update(MusicCardsColumns.CONTENT_URI, values.values(), null, null);
            }
        });

        return v;
    }



}

