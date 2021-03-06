package com.app.deemounus.musiccards;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.app.deemounus.musiccards.provider.musiccards.MusicCardsSelection;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.IOError;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class AddCardActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    String LOG_TAG = getClass().getSimpleName();
    Context ctx;

    public AddCardActivityFragment() {
    }


    String[] projection = null;
    String musicUrl;
    String pictureUrl;
    boolean DBhasdata;
    private Tracker mTracker;

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int FILE_CODE = 14;


    private void getPictureUrlIntent() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                Utils.sendMetricsForAction("getPictureIntentStarted", LOG_TAG, mTracker);
                Intent pictureIntent = new Intent(getActivity(), ImagePickerActivity.class);
                startActivityForResult(pictureIntent, INTENT_REQUEST_GET_IMAGES);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(getActivity(), "Please allow access for:\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        new TedPermission(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permissions, you will not be able to add cards\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void getMusicUrlIntent() {
        Intent musicIntent = new Intent(getActivity(), FilePickerActivity.class);
        Utils.sendMetricsForAction("getMusicIntentStarted", LOG_TAG, mTracker);
        // TODO: Fix the bug that user is able to select multiple pictures
        musicIntent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
        musicIntent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        musicIntent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        musicIntent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        startActivityForResult(musicIntent, FILE_CODE);
    }

    private void saveDBdata(String musicUrl, String pictureUrl){
        MusicCardsContentValues values = new MusicCardsContentValues();
        values.putMusic(musicUrl).putPicture(pictureUrl);
        try {
            ctx.getContentResolver().insert(MusicCardsColumns.CONTENT_URI, values.values());
        } catch (NullPointerException e){
            e.printStackTrace();
        } finally {
            Intent returnIntent = new Intent();
            getActivity().setResult(Activity.RESULT_OK, returnIntent);
            getActivity().finish();
        }
    }

    private void saveUrls() {
        if(musicUrl == null || pictureUrl == null || pictureUrl.equals("[]") || musicUrl.contains(", ") || pictureUrl.contains(", ") || musicUrl.contains(".jpg") || musicUrl.contains(".png") || musicUrl.contains(".tif") || musicUrl.contains(".gif")){
            Utils.showToast(ctx, getString(R.string.add_card_error));
            Utils.sendMetricsForAction("saveDatabaseDataFailure", LOG_TAG, mTracker);
        } else  {
            try {
                saveDBdata(musicUrl, pictureUrl);
                Utils.setSharedPrefsBooleanValue("activityHasData", true, getContext());
            } catch (IOError e){
                e.getLocalizedMessage();
            }
            Utils.sendMetricsForAction("saveDatabaseDataSuccess", LOG_TAG, mTracker);
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ctx = getContext();
        // Obtain the shared Tracker instance.
        AnalyticsTracker application = (AnalyticsTracker) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onResume(){
        super.onResume();
        Utils.prepareMetricsForActivity(LOG_TAG, mTracker);
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
            Utils.sendMetricsForAction("saveDatabaseDataButtonPressed", LOG_TAG, mTracker);
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
                getPictureUrlIntent();
                Utils.sendMetricsForAction("addPictureButtonPressed", LOG_TAG, mTracker);
            }
        });

        addMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicCardsSelection where = new MusicCardsSelection();

                getMusicUrlIntent();
                Utils.sendMetricsForAction("addMusicButtonPressed", LOG_TAG, mTracker);
            }
        });

        return v;


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        if (requestCode == INTENT_REQUEST_GET_IMAGES && resultCode == Activity.RESULT_OK) {

            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

            pictureUrl = image_uris.toString();
//            Log.v(LOG_TAG + "PIC PATH: ", pictureUrl);

        }

        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (intent.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                    ClipData clip = intent.getClipData();
                        if(clip.getItemCount() >= 0) {
                            Uri uri = clip.getItemAt(0).getUri();
                            // Do something with the URI
                            musicUrl = uri.toString();
                        } else {
//                            Log.v(LOG_TAG, "Url is null");
                        }
                }
            }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri CONTENT_URI = MusicCardsColumns.CONTENT_URI;
        return new CursorLoader(getContext(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.getCount() == 0){
            DBhasdata = false;
        } else if (cursor.getCount() > 0) {
            DBhasdata = true;
        }
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
