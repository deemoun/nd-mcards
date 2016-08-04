package com.app.deemounus.musiccards;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by deemounus on 8/4/16.
 */
public class Utils {
     public static void showToast(Context ctx, String toastText){
        Toast.makeText(ctx, toastText, Toast.LENGTH_SHORT).show();
    }
}
