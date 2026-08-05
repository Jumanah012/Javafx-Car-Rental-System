package model;

public class Administrator extends User {

    public Administrator(int userId, String name, String email, String password) {
        super(userId, name, email, password, "Administrator");
    }

}