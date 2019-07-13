package com.williams.zalarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.williams.zalarm.R;
import com.williams.zalarm.service.MediaPlayerService;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    final static MediaPlayer mediaPlayer = new MediaPlayer();
    static MediaPlayerService mService = new MediaPlayerService(mediaPlayer);
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTime(null, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    protected Seconds diffBetweenTimes(DateTime startTime, DateTime endTime){
        return Seconds.secondsBetween(startTime, endTime);
    }

    protected void setTime(DateTime startTime, DateTime endTime){
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 99, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + diffBetweenTimes(startTime, endTime).getSeconds()* (1000), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +500, pendingIntent);

        Toast.makeText(this, "Alarm set in " + 1 + "min(s)", Toast.LENGTH_LONG).show();

    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaPlayerService.MediaPlayerBinder binder = (MediaPlayerService.MediaPlayerBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void stopAudio(View view) {
        d("stopAudio", "Entered stopAudio");
          mService.stopPlayer();
    }

    public void startAudio(View view) {
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            d("MyBroadcastReceiver", "Successfully called onReceive method inside MyBroadcastReceiver");
            mService.startPlayer(context, R.raw.analog_watch_alarm_daniel_simion, 10,10 , true, true);
        }
    }
}
