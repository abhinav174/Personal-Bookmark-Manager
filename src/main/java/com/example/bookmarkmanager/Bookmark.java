package com.example.bookmarkmanager;

import java.util.Arrays;
import java.util.List;

public class Bookmark {
    private String title;
    private String url;
    private List<String> tags;

    public Bookmark(String title, String url, List<String> tags) {
        this.title = title;
        this.url = url;
        this.tags = tags;
    }

    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public List<String> getTags() { return tags; }

    public String toCSV() {
        return title + "," + url + "," + String.join(";", tags);
    }

    public static Bookmark fromCSV(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length < 3) return null;
        List<String> tags = Arrays.asList(parts[2].split(";"));
        return new Bookmark(parts[0], parts[1], tags);
    }

    @Override
    public String toString() {
        return title + " (" + url + ") Tags: " + String.join(", ", tags);
    }
}
