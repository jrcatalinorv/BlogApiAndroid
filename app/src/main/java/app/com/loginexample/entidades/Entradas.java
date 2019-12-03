package app.com.loginexample.entidades;

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

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
