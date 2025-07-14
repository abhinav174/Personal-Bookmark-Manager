package com.example.bookmarkmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkManager {
    private static final String FILE_NAME = "bookmarks.csv";

    public static List<Bookmark> loadBookmarks() {
        List<Bookmark> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Bookmark bm = Bookmark.fromCSV(line);
                if (bm != null) list.add(bm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveBookmarks(List<Bookmark> bookmarks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Bookmark bm : bookmarks) {
                bw.write(bm.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
