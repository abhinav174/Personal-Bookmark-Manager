# Personal Bookmark Manager (Java Desktop App)

A simple desktop Java application to manage your favorite website bookmarks, built with Java Swing.

## ✨ Features
- Add new bookmarks with title, URL, and tags
- View saved bookmarks
- Search bookmarks by title or tags
- Double-click to open bookmarks in your default browser
- Bookmarks stored in a local CSV file

## 🛠 Technologies
- Java SE 8+
- Swing GUI
- Maven

## 🚀 How to build & run

Clone this repository, then:

```bash
mvn clean package
java -cp target/bookmarkmanager-1.0.jar com.example.bookmarkmanager.MainUI
```

## 📦 Structure
- `Bookmark.java` : data model
- `BookmarkManager.java` : handles loading & saving bookmarks
- `MainUI.java` : Swing GUI

## 🖼 Screenshots

1. Home Screen
   <img width="984" height="617" alt="image" src="https://github.com/user-attachments/assets/7122cc8d-3987-4d3b-b5bd-26b9b656e875" />

2. Adding a bookmark
   <img width="984" height="617" alt="image" src="https://github.com/user-attachments/assets/c83aa55f-d8f3-4af6-9a96-ff9bb566fff2" />
 
3. Searching a bookmark
   <img width="984" height="617" alt="image" src="https://github.com/user-attachments/assets/93b8d136-691d-4799-a4ae-d377dc4e30f7" />

4. List of all available bookmarks
   <img width="984" height="617" alt="image" src="https://github.com/user-attachments/assets/483954bd-be50-4ddd-910b-c2b3199ad8b7" />

5. CSV File Contents:
   <img width="1232" height="637" alt="image" src="https://github.com/user-attachments/assets/8a153795-a46b-43fa-a112-08d47144a75f" />


---

Built by G.Abhinav.
