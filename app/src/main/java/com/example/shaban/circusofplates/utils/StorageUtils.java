package com.example.shaban.circusofplates.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shaban on 7/16/2018.
 */

public class StorageUtils {

    public static void saveTextToPref(Context context , String text) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.SAVE_GAME_KEY,text);
        editor.commit();
    }

    public static String getTextFromPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE);
        return sharedPref.getString(Constants.SAVE_GAME_KEY,null);
    }
}
