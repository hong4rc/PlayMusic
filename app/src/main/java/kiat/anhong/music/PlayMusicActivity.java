package kiat.anhong.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import kiat.anhong.music.model.Song;
import kiat.anhong.music.services.MusicService;
import kiat.anhong.music.ui.adapter.SongAdapter;
import kiat.anhong.music.utils.MusicUtils;

public class PlayMusicActivity extends AppCompatActivity {
    private RecyclerView songView;
    private ArrayList<Song> songList;
    private SongAdapter songAdapter;
    private LinearLayoutManager layoutManager;

    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        init();
        getWidget();
        setWidget();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(PlayMusicActivity.this, MusicService.class);
            bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    private void init() {
        songList = MusicUtils.getSongList(this);
        songAdapter = new SongAdapter(songList);
        layoutManager = new LinearLayoutManager(getApplicationContext());

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) iBinder;
                musicService = binder.getService();

                musicBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                musicBound = false;
            }
        };
    }

    private void getWidget() {
        songView = findViewById(R.id.songView);
    }

    private void setWidget() {
        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onClick(int i) {
                musicService.playSong(songList.get(i));
            }
        });
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songView.setLayoutManager(layoutManager);
        songView.setAdapter(songAdapter);
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicService = null;
        super.onDestroy();
    }
}
