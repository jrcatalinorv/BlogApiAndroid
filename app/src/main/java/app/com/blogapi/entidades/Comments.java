package app.com.blogapi.entidades;

public class Comments {

    String body;
    String createdAt;
    int id;
    int postId;
    String userEmail;
    int userId;
    String UseName;

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public String getUseName() {
        return UseName;
    }
}
