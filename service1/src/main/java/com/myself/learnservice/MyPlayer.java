package com.myself.learnservice;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class MyPlayer extends MediaPlayer {

    private static MyPlayer myPlayer;

    public static MyPlayer getInstance() {
        if (myPlayer == null) {
            myPlayer = new MyPlayer();
        }
        return myPlayer;
    }

    private MyPlayer() {

    }

    public void play_music(Context context, String musicName) {
        AssetManager assetManager;
        assetManager = context.getResources().getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(musicName);
            myPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
            myPlayer.prepare();
            myPlayer.start();
            myPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop_music() {
        myPlayer.stop();
    }

}
