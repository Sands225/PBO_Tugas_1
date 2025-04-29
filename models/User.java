package models;

public class User {
    private final String name;
    private final String password;

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}