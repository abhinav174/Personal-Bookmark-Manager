package com.example.bookmarkmanager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainUI extends JFrame {
    private List<Bookmark> bookmarks;

    private JTextField titleField = new JTextField(20);
    private JTextField urlField = new JTextField(20);
    private JTextField tagsField = new JTextField(20);
    private JTextField searchField = new JTextField(20);
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> bookmarkList = new JList<>(listModel);

    public MainUI() {
        super("Personal Bookmark Manager");
        bookmarks = BookmarkManager.loadBookmarks();
        initUI();
        refreshList(bookmarks);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Add Panel
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Add New Bookmark", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        addPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; addPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        addPanel.add(new JLabel("URL:"), gbc);
        gbc.gridx = 1; addPanel.add(urlField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        addPanel.add(new JLabel("Tags (comma separated):"), gbc);
        gbc.gridx = 1; addPanel.add(tagsField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("➕ Add Bookmark");
        addButton.setPreferredSize(new Dimension(200, 30));
        addPanel.add(addButton, gbc);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Search Bookmarks", TitledBorder.LEFT, TitledBorder.TOP));
        searchPanel.add(new JLabel("Keyword:"));
        searchPanel.add(searchField);
        JButton searchButton = new JButton("🔍 Search");
        searchPanel.add(searchButton);

        // Bookmark List
        bookmarkList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane listScroll = new JScrollPane(bookmarkList);
        listScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Saved Bookmarks", TitledBorder.LEFT, TitledBorder.TOP));

        // Add panels to frame
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(addPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(listScroll, BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String url = urlField.getText().trim();
            String tagsText = tagsField.getText().trim();
            if (!title.isEmpty() && !url.isEmpty()) {
                List<String> tags = Arrays.stream(tagsText.split(","))
                                          .map(String::trim)
                                          .filter(s -> !s.isEmpty())
                                          .collect(Collectors.toList());
                Bookmark bm = new Bookmark(title, url, tags);
                bookmarks.add(bm);
                BookmarkManager.saveBookmarks(bookmarks);
                refreshList(bookmarks);
                titleField.setText("");
                urlField.setText("");
                tagsField.setText("");
            }
        });

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                refreshList(bookmarks);
            } else {
                List<Bookmark> filtered = bookmarks.stream()
                        .filter(bm -> bm.getTitle().toLowerCase().contains(keyword) ||
                                      bm.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(keyword)))
                        .collect(Collectors.toList());
                refreshList(filtered);
            }
        });

        bookmarkList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = bookmarkList.getSelectedIndex();
                    if (index >= 0) {
                        String selected = listModel.get(index);
                        Bookmark bm = findBookmarkByDisplay(selected);
                        if (bm != null) {
                            try {
                                Desktop.getDesktop().browse(new java.net.URI(bm.getUrl()));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center window
        setVisible(true);
    }

    private void refreshList(List<Bookmark> list) {
        listModel.clear();
        for (Bookmark bm : list) {
            listModel.addElement(bm.toString());
        }
    }

    private Bookmark findBookmarkByDisplay(String display) {
        return bookmarks.stream()
                .filter(bm -> bm.toString().equals(display))
                .findFirst().orElse(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }
}
