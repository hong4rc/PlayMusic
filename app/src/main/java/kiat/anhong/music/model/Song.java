package kiat.anhong.music.model;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 29
 */

import java.util.Locale;

public class Song {
    public static final int TITLE = 0;
    public static final int ARTIST = 1;
    private static final int FIELD_COUNT = 2;
    private long id;
    private String[] fields;

    public Song(long id, String title, String artist) {
        fields = new String[FIELD_COUNT];
        this.id = id;
        fields[TITLE] = title;
        fields[ARTIST] = artist;
    }

    public long getId() {
        return id;
    }

    public String get(int field) {
        return fields[field];
    }
}