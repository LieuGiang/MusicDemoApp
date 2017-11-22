package vn.com.jvit.musicdemoapp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PlayMusicFromLocal.DownloadListener {
    private EditText mEdt_url;
    private Button mBtn_download,mBtn_play_service;
    private ListView mLv_list_songs;
    private List<File> newList;
    private ArrayList<Song> listSong;
    private MediaPlayer mediaPlayer;
    private RowItemListSongAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        PlayMusicFromLocal download = new PlayMusicFromLocal(this);
        download.setListener(this);
        download.execute();

//        setPlayService();


    }


    private void setPlayService() {
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
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        song.setFlag(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    adapter.notifyDataSetChanged();

                }else{if (mediaPlayer != null) {
//                    mediaPlayer.stop();
                    mediaPlayer.release();
                    song.setFlag(false);
                    adapter.notifyDataSetChanged();
                }}
            }

        });
    }

    private void init() {
        mEdt_url = findViewById(R.id.main_activity_edt_url);
        mBtn_download = findViewById(R.id.main_activity_btn_download);
        mLv_list_songs = findViewById(R.id.main_activity_lv_song);
        mBtn_play_service = findViewById(R.id.main_activity_btn_play_service);

        mBtn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = mEdt_url.getText().toString();
                new LoadMP3FromURL(MainActivity.this).execute(url);
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
}


