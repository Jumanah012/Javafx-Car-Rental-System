package controller;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> updateTypeComboBox;

    private DatabaseHandler dbHandler;

    public UpdateUserController() {
        dbHandler = new DatabaseHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateTypeComboBox.setItems(FXCollections.observableArrayList("Customer", "RentalAgent", "Administrator"));
    }

    @FXML
    private void handleUpdateUser(ActionEvent event) {
        String email = emailField.getText().trim();
        String newUserType = updateTypeComboBox.getValue();

        if (email.isEmpty() || newUserType == null || newUserType.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        User user = dbHandler.getUserByEmail(email);
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "User Not Found", "No user found with the provided email.");
            return;
        }

        String oldRole = user.getRole();
        if (oldRole.equals(newUserType)) {
            showAlert(Alert.AlertType.INFORMATION, "No Change", "User already has the selected role.");
            return;
        }

        user.setRole(newUserType);
        dbHandler.updateUser(user);
        showAlert(Alert.AlertType.INFORMATION, "Success", "User role updated successfully.");

        emailField.clear();
        updateTypeComboBox.getSelectionModel().clearSelection();

        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }
}