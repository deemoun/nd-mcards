package com.app.deemounus.musiccards;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.deemounus.musiccards.provider.musiccards.MusicCardsColumns;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsContentValues;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsSelection;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.util.ArrayList;

public class AddCardActivityFragment extends Fragment {

    public AddCardActivityFragment() {
    }


    String[] projection;
    String musicUrl;
    String pictureUrl;

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int FILE_CODE = 14;

    private void getPictures() {
        Intent pictureIntent = new Intent(getActivity(), ImagePickerActivity.class);
        startActivityForResult(pictureIntent, INTENT_REQUEST_GET_IMAGES);
    }

    private void getMusic() {
        Intent musicIntent = new Intent(getActivity(), FilePickerActivity.class);
        musicIntent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
        musicIntent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        musicIntent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        musicIntent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        startActivityForResult(musicIntent, FILE_CODE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_add_card, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_card, container, false);

        Button addPicture = (Button) v.findViewById(R.id.addPicture);
        Button addMusic = (Button) v.findViewById(R.id.addMusic);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPictures();
            }
        });

        addMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
//                MusicCardsSelection where = new MusicCardsSelection();
//                where.music("John");
//                Cursor c = getContext().getContentResolver().query(MusicCardsColumns.CONTENT_URI, projection,
//                        where.sel(), where.args(), null);

//                MusicCardsContentValues values = new MusicCardsContentValues();
//                values.putMusic("http://music_link").putPicture("http://picture_link");
//                getContext().getContentResolver().insert(MusicCardsColumns.CONTENT_URI, values.values());
//                getContext().getContentResolver().update(MusicCardsColumns.CONTENT_URI, values.values(), null, null);

                getMusic();
            }
        });

        return v;


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        if (requestCode == INTENT_REQUEST_GET_IMAGES && resultCode == Activity.RESULT_OK) {

            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

            Log.v("Image URL: ", image_uris.toString());
            pictureUrl = image_uris.toString();
        }

        // TODO: Fix the bug that user is able to select multiple pictures
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (intent.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                    ClipData clip = intent.getClipData();
                        if(clip.getItemCount() >= 0) {
                            Uri uri = clip.getItemAt(0).getUri();
                            // Do something with the URI
                            Log.v("Music URL: ", uri.toString());
                            musicUrl = uri.toString();
                        } else {
                            Log.v("AddActivityFragment", "Url is null");
                        }
                }
            }
    }

}
