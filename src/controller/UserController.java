package controller;

import database.DatabaseHandler;
import java.util.List;
import model.User;

public class UserController {
    private DatabaseHandler dbHandler;

    public UserController() {
        dbHandler = new DatabaseHandler();
    }

    public void addUser(User user){
        dbHandler.addUser(user);
    }

    public void removeUser(int userId){
        dbHandler.removeUser(userId);
    }

    public void updateUser(User user){
        dbHandler.updateUser(user);
    }

    public User getUser(int userId){
        return dbHandler.getUser(userId);
    }

    public List<User> getAllUsers(){
        return dbHandler.getAllUsers();
    }

    public void close(){
        dbHandler.closeConnection();
    }
}
