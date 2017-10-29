package kiat.anhong.music.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import kiat.anhong.music.R;
import kiat.anhong.music.model.Song;

/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 29
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private ArrayList<Song> songList;
    private OnItemClickListener listener;

    public SongAdapter(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song song = songList.get(position);
        String title = song.get(Song.TITLE);
        holder.tvTitle.setText(title);
        holder.tvArtist.setText(song.get(Song.ARTIST));
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(title.substring(0, 1), ColorGenerator.MATERIAL.getRandomColor());
        holder.imgChar.setImageDrawable(textDrawable);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvArtist;
        ImageView imgChar;

        SongViewHolder(View itemView) {
            super(itemView);
            imgChar = itemView.findViewById(R.id.imgChar);
            tvTitle = itemView.findViewById(R.id.titleSong);
            tvArtist = itemView.findViewById(R.id.artistSong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            assert listener != null;
            listener.onClick(this.getLayoutPosition());
        }
    }

    public interface OnItemClickListener {
        void onClick(int i);
    }
}
