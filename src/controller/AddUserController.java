package controller;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Administrator;
import model.Customer;
import model.RentalAgent;
import model.User;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private DatabaseHandler dbHandler;

    public AddUserController() {
        dbHandler = new DatabaseHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTypeComboBox.setItems(FXCollections.observableArrayList("Customer", "RentalAgent", "Administrator"));
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        String userType = userTypeComboBox.getValue();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (userType == null || userType.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (dbHandler.getUserByEmail(email) != null) {
            showAlert(Alert.AlertType.ERROR, "Duplicate Email", "A user with this email already exists.");
            return;
        }

        User newUser = null;
        switch (userType) {
            case "Customer":
                newUser = new Customer(0, name, email, password);
                break;
            case "RentalAgent":
                newUser = new RentalAgent(0, name, email, password);
                break;
            case "Administrator":
                newUser = new Administrator(0, name, email, password);
                break;
            default:
                showAlert(Alert.AlertType.ERROR, "Invalid User Type", "Please select a valid user type.");
                return;
        }

        dbHandler.addUser(newUser);
        showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully.");

        userTypeComboBox.getSelectionModel().clearSelection();
        nameField.clear();
        emailField.clear();
        passwordField.clear();

        closeWindow();
    }

    private boolean isValidEmail(String email) {

        return email.matches("^(.+)@(.+)$");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}