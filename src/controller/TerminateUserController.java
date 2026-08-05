package controller;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

public class TerminateUserController {

    @FXML
    private TextField reasonField;

    @FXML
    private TextField emailField;

    private DatabaseHandler dbHandler;

    public TerminateUserController(){
        dbHandler = new DatabaseHandler();
    }

    @FXML
    private void handleTerminateUser(ActionEvent event) {
        String reason = reasonField.getText().trim();
        String email = emailField.getText().trim();

        if(reason.isEmpty() || email.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        if(!isValidEmail(email)){
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        User user = dbHandler.getUserByEmail(email);
        if(user == null){
            showAlert(Alert.AlertType.ERROR, "User Not Found", "No user found with the provided email.");
            return;
        }

        dbHandler.removeUser(user.getUserId());
        showAlert(Alert.AlertType.INFORMATION, "Success", "User terminated successfully.");

        reasonField.clear();
        emailField.clear();

        closeWindow();
    }

    private boolean isValidEmail(String email){

        return email.matches("^(.+)@(.+)$");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow(){
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }
}