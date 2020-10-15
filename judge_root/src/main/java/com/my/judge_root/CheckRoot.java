package com.my.judge_root;

import android.util.Log;

import java.io.File;

public class CheckRoot {

    private static final String TAG = "10001";

    public static boolean isSuEnable() {
        File file = null;
        String[] paths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/"};
        try {
            for (String path : paths) {
                file = new File(path + "su");
                if (file.exists() && file.canExecute()) {
                    Log.i(TAG, "find su in : " + path);
                    return true;
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return false;
    }

}
