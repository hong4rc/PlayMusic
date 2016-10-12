package com.anhhong.playmusic;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayMusic extends AppCompatActivity {
    private boolean isLocal =true;
    ImageButton previous,play,next;
    private double timeNow,timeAll;
    private SeekBar seekBar;
    private TextView textTimeNow, textTimeAll;
    private MediaPlayer mediaPlayer ;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        anhXaCacThanhPhan();

        mediaPlayer = MediaPlayer.create(this,R.raw.npdb);
        handler.postDelayed(sekkbarConnectToMediaPlayer,100);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    play.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });
    }
    private Runnable sekkbarConnectToMediaPlayer = new Runnable() {

        @Override
        public void run() {
            timeNow = mediaPlayer.getCurrentPosition();

            timeAll = mediaPlayer.getDuration();
            if(timeAll <= timeNow){
                timeNow =0;
                mediaPlayer.pause();
            }
            textTimeNow.setText(convert(timeNow));
            textTimeAll.setText(convert(timeAll));

            seekBar.setMax((int) timeAll);
            seekBar.setProgress((int) timeNow);


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                private boolean a ;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            });

            handler.postDelayed(this,100);
        }
    };

    private String convert(double time ){
        time/=1000;
        return time(time/60) + ":" + time(time%60);
    }
    private String time(double time){
        if(time < 10) return "0" + (int)time;
        return "" + (int) time;
    }



    private void anhXaCacThanhPhan(){
        //  anh xa cac button
        previous   = (ImageButton)findViewById(R.id.previous);
        play       = (ImageButton)findViewById(R.id.play);
        next       = (ImageButton)findViewById(R.id.next);

        //  anh xa seekbar
        seekBar    = (SeekBar)    findViewById(R.id.seekBar);

        // anh xa cac textView
        textTimeAll=(TextView)findViewById(R.id.timeAll);
        textTimeNow=(TextView)findViewById(R.id.timeNow);
    }
}
