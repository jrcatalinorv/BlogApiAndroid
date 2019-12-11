package app.com.blogapi.entidades;

public class Login {


    public Login(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private String email, password, name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
