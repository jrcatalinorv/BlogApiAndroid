package app.com.blogapi.entidades;

import androidx.annotation.NonNull;

public class Entradas {

    int comments;
    int id;
    int likes;
    int userId;
    int views;

    String body;
    String createdAt;
    Boolean liked;
    String[] tags;
    String title;
    String userEmail;
    String userName;

    public Entradas(int userId, String body, String[] tags, String title, String userEmail, String userName) {
        this.userId = userId;
        this.body = body;
        this.tags = tags;
        this.title = title;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getBody() {
        return body;
    }

    public int getComments() {
        return comments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public Boolean getLiked() {
        return liked;
    }

    public int getLikes() {
        return likes;
    }

    public String[] getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getViews() {
        return views;
    }


}
