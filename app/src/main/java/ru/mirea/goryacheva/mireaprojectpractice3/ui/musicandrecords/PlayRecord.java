package ru.mirea.goryacheva.mireaprojectpractice3.ui.musicandrecords;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class PlayRecord extends Thread {
    private final Context context;
    private int musicResId;
    private String filePath = null;

    public PlayRecord(Context context, int musicResId){
        this.musicResId = musicResId;
        this.context = context;
    }

    public PlayRecord(Context context, String filePath) {
        this.filePath = filePath;
        this.context = context;
    }

    @Override
    public void run() {
        MediaPlayer mediaPlayer = null;

        if (filePath != null){
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer = MediaPlayer.create(context, musicResId);
            mediaPlayer.start();
        }
        mediaPlayer.setLooping(true);

        while (true){
            if (isInterrupted()) break;
        }

        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
    }
}
