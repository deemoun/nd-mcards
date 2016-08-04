package com.app.deemounus.musiccards;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.deemounus.musiccards.provider.musiccards.MusicCardsColumns;
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsContentValues;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.util.ArrayList;

public class AddCardActivityFragment extends Fragment {

    String LOG_TAG = getClass().getSimpleName();
    Context ctx;

    public AddCardActivityFragment() {
    }


    String[] projection = null;
    String musicUrl;
    String pictureUrl;

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int FILE_CODE = 14;

    private void getPictureUrl() {
        Intent pictureIntent = new Intent(getActivity(), ImagePickerActivity.class);
        startActivityForResult(pictureIntent, INTENT_REQUEST_GET_IMAGES);
    }

    private void getMusicUrl() {
        Intent musicIntent = new Intent(getActivity(), FilePickerActivity.class);
        musicIntent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
        musicIntent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        musicIntent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        musicIntent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        startActivityForResult(musicIntent, FILE_CODE);
    }

    private void saveUrls() {
        MusicCardsContentValues values = new MusicCardsContentValues();
        if(musicUrl == null || pictureUrl == null){
            Utils.showToast(ctx, "Please choose both picture and music files!");
            Log.v(LOG_TAG, "Either one of the values or both are empty!");
        } else  {
            values.putMusic(musicUrl).putPicture(pictureUrl);
            ctx.getContentResolver().insert(MusicCardsColumns.CONTENT_URI, values.values());
            ctx.getContentResolver().update(MusicCardsColumns.CONTENT_URI, values.values(), null, null);
            getActivity().finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ctx = getContext();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_card, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
         // Saving both image and music URLs and closing activity
            saveUrls();
        }

        return super.onOptionsItemSelected(item);
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
                getPictureUrl();
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

                getMusicUrl();
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
                            Log.v(LOG_TAG, "Url is null");
                        }
                }
            }
    }

}
