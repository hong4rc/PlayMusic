package kiat.anhong.music.utils;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 29
 */

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kiat.anhong.music.model.Song;

public class MusicUtils {
    public static void sortSong(ArrayList<Song> songList, final int type){
        Collections.sort(songList, new Comparator<Song>(){
            @Override
            public int compare(Song a, Song b){
                return a.get(type).compareTo(b.get(type));
            }
        });
    }
    private static Cursor getCursorMusic(Context context){
        ContentResolver musicResolver = context.getContentResolver();
        Uri mUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return musicResolver.query(mUri, null, null, null, null);
    }
    public static ArrayList<Song> getSongList(Context context) {
        ArrayList<Song> songList = new ArrayList<>();
        Cursor mCursor = MusicUtils.getCursorMusic(context);
        if (mCursor == null || !mCursor.moveToFirst()) {
            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
            return null;
        }
        int titleCol = mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int idCol = mCursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int artistCol = mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

        do {
            long id = mCursor.getLong(idCol);
            String title = mCursor.getString(titleCol);
            String artist = mCursor.getString(artistCol);

            songList.add(new Song(id, title, artist));
        } while (mCursor.moveToNext());
        mCursor.close();
        sortSong(songList, Song.TITLE);
        return songList;
    }
}
