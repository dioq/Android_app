package com.myself.a07class;

import android.util.Log;

public class LogUtil {
    public static boolean isDebugMode = true;

    public static void logI(String tag, String msg) {
        if (isDebugMode){
            Log.i(tag, msg);
        }
    }

}
