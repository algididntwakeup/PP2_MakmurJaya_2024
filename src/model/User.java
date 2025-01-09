package model;

public class User {

    private int id;
    private String email;
    private String password;
    private String role;

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    // Getter dan Setter
    
    //id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
