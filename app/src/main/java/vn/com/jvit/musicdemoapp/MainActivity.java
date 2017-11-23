package vn.com.jvit.musicdemoapp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.com.jvit.utils.AppLog;

public class MainActivity extends BaseActivity implements PlayMusicFromLocal.DownloadListener, NetworkChangeReceiver.NetworkChangeListener, LoadMP3FromURL.DownloadurlListener {
    private EditText mEdt_url;
    private Button mBtn_download, mBtn_play_service, mBtn_count_down;
    private ListView mLv_list_songs;
    private List<File> newList;
    private ArrayList<Song> listSong;
    private MediaPlayer mediaPlayer;
    private RowItemListSongAdapter adapter;
    public static NetworkChangeReceiver.NetworkChangeListener sListener;
    private String url;
    private LoadMP3FromURL loadMP3FromURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        PlayMusicFromLocal download = new PlayMusicFromLocal(this);
        download.setListener(this);
        download.execute();

//        setPlayServiceCountDown();


    }


    private void setPlayServiceCountDown() {



    }

    private void setOnItemclick() {
        adapter = new RowItemListSongAdapter<Song>(this);
        adapter.setData(listSong);
        mLv_list_songs.setAdapter(adapter);

        mLv_list_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String s = listSong.get(i).getSongPath();
                Song song = listSong.get(i);
                if (song.isFlag() == false) {
                    if (mediaPlayer != null) {
//                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }

                    for (int j = 0; j < listSong.size(); j++) {
                        listSong.get(j).setFlag(false);
                    }


                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    try {
                        mediaPlayer.setDataSource(s);
                        AppLog.d("AAA", s);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        song.setFlag(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    adapter.notifyDataSetChanged();

                } else {
                    if (mediaPlayer != null) {
//                    mediaPlayer.stop();
                        mediaPlayer.release();
                        song.setFlag(false);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

        });
    }

    private void init() {


        mBtn_count_down = findViewById(R.id.main_activity_btn_countdown);
        mEdt_url = findViewById(R.id.main_activity_edt_url);
        mBtn_download = findViewById(R.id.main_activity_btn_download);
        mLv_list_songs = findViewById(R.id.main_activity_lv_song);
        mBtn_play_service = findViewById(R.id.main_activity_btn_play_service);

        url = mEdt_url.getText().toString();
        sListener = this;


        mBtn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadMP3FromURL = new LoadMP3FromURL(MainActivity.this);
                loadMP3FromURL.setDownUrlListener(MainActivity.this);
                loadMP3FromURL.execute(url);
            }
        });
        mBtn_play_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, BackGroundSoundService.class));

            }
        });


    }

    @Override
    public void onStartLoadFile() {
        showLoadingDialog();
    }

    @Override
    public void onLoadFileComplete(List<File> arrFiles) {


        newList = new ArrayList<>();

        listSong = new ArrayList<>();
        for (int i = 0; i < arrFiles.size(); i++) {

            listSong.add(new Song(arrFiles.get(i).getName().toString(), arrFiles.get(i).getPath().toString()));
        }
        setOnItemclick();
        hideLoadingDialog();
    }

    @Override
    public void onFailed() {
        hideLoadingDialog();
    }

    @Override
    public void onRetryDownload() {
        loadMP3FromURL = new LoadMP3FromURL(MainActivity.this);
        loadMP3FromURL.setDownUrlListener(MainActivity.this);
        loadMP3FromURL.execute(url);
        //AppLog.d("AAA","ABC");
    }

    @Override
    public void onStartDownLoadFile() {
        showLoadingDialog();

    }

    @Override
    public void onDownLoadFileComplete() {
        hideLoadingDialog();

    }

    @Override
    public void onFailedDownload() {
        hideLoadingDialog();

    }

    public void CountDownClick(View view) {
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                mBtn_count_down.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {

                Intent intent = new Intent(MainActivity.this, BackGroundSoundService.class);
                intent.putExtra("songPath", "/storage/emulated/0/SoundHelix-Song-1.mp3");
                intent.putExtra("songName", "Song Music");
                startService(intent);
            }
        }.start();
    }
}


