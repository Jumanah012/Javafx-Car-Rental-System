package controller;

import database.DatabaseHandler;
import model.Administrator;
import model.RentalAgent;
import model.Customer;
import model.RentalAgent;
import model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private DatabaseHandler dbHandler;

    public LoginController(){
        dbHandler = new DatabaseHandler();
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if(email.isEmpty() || password.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Email and Password");
            return;
        }

        User user = dbHandler.getUserByEmail(email);

        if(user != null && user.getPassword().equals(password)){

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
                Scene scene = new Scene(loader.load());

                MainController mainController = loader.getController();
                mainController.setUser(user);

                Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("JDS Car Rentals - Dashboard");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load the dashboard.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Email or Password");
        }
    }

    @FXML
    private void handleAgentLogin(ActionEvent event) {

        showAlert(Alert.AlertType.INFORMATION, "Info", "Please enter your Agent credentials.");
    }

    @FXML
    private void handleAdminLogin(ActionEvent event) {

        showAlert(Alert.AlertType.INFORMATION, "Info", "Please enter your Admin credentials.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}