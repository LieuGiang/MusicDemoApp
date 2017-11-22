package vn.com.jvit.utils;

import android.util.Log;

import vn.com.jvit.musicdemoapp.BuildConfig;

/**
 * Created by User on 11/21/2017.
 */

public class AppLog {

    public static void d(String TAG, String message){

        if(BuildConfig.DEBUG){
            Log.d(TAG, message);
        }
    }
}
