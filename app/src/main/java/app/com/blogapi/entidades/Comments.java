package app.com.blogapi.entidades;

public class Comments {

    String body;
    long createdAt;
    int id;
    int postId;
    String userEmail;
    int userId;
    String userName;

    public Comments(String body, int postId, String userEmail, int userId, String userName) {
        this.body = body;
        this.postId = postId;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
    }

    public String getBody() {
        return body;
    }

    public long getCreatedAt() {
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

    public String getUserName() {
        return userName;
    }
}
