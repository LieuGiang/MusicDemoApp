package vn.com.jvit.musicdemoapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class BackGroundSoundService extends Service implements RowItemListSongAdapter.PlayServiceListener {
    private static final String TAG = "BackGroundSoundService";
    private static final int FOREGROUND_ID = 1;
    private MediaPlayer mediaPlayer;
    private String mPath, mName;
    private String data;
    private Context mContext;


    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        data = intent.getStringExtra("songPath");
        mName = intent.getStringExtra("songName");
        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(data);
            mediaPlayer.prepare();
            mediaPlayer.start();
            startForeground(FOREGROUND_ID, getNotification(mName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startForeground(FOREGROUND_ID, getNotification(mName));

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onCreate() {
        super.onCreate();


    }


    @Override
    public void onDestroy() {
        mediaPlayer.release();
    }


    @Override
    public void sendData(String path) {
        mPath = path;


    }

    public Notification getNotification(String nameSong) {


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        android.support.v4.app.NotificationCompat.Builder foregroundNotification = new android.support.v4.app.NotificationCompat.Builder(this);
        foregroundNotification.setOngoing(true);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music_picture);
        foregroundNotification.setContentTitle("Music Demo App !")
                .setContentText(nameSong)
                .setSmallIcon(R.drawable.ic_music_note_black_24dp)
                .setContentIntent(pendingIntent)
                .setStyle(new android.support.v4.app.NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setLargeIcon(bitmap);
        return foregroundNotification.build();

    }
}







