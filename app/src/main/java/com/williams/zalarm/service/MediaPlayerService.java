package com.williams.zalarm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

import static android.util.Log.d;

public class MediaPlayerService extends Service {

    private final IBinder binder = new MediaPlayerBinder();
    private MediaPlayer mediaPlayer;

    public MediaPlayerService(MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
    }

    public class MediaPlayerBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void startPlayer(Context context, int musicType, float volumeLeft, float volumeRight, boolean repeat, boolean vibrate){
        d("MediaPlayerService:sp", "Starting player");
        mediaPlayer = MediaPlayer.create(context, musicType);
        mediaPlayer.setLooping(repeat);
        mediaPlayer.setVolume(volumeLeft, volumeRight);
        mediaPlayer.start();

        if(vibrate){
            // TODD Implement vibration
        }
    }

    public void stopPlayer(){
        d("MediaPlayerService:sp", "Stopping player");
        mediaPlayer.stop();

    }
}
