package controller;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Vehicle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateVehicleAvailabilityController implements Initializable {

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    private DatabaseHandler dbHandler;

    public UpdateVehicleAvailabilityController() {
        dbHandler = new DatabaseHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadVehicles();
        loadStatuses();
    }

    private void loadVehicles() {
        List<Vehicle> vehicles = dbHandler.getAllVehicles();
        ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList(vehicles);
        vehicleComboBox.setItems(vehicleList);
        vehicleComboBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Vehicle vehicle) {
                if (vehicle == null) {
                    return "";
                }
                return vehicle.getMake() + " " + vehicle.getModel() + " (" + vehicle.getYear() + ")";
            }

            @Override
            public Vehicle fromString(String string) {
                return null; 
            }
        });
    }

    private void loadStatuses() {
        ObservableList<String> statuses = FXCollections.observableArrayList("Available", "Rented", "Under Maintenance");
        statusComboBox.setItems(statuses);
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        Vehicle selectedVehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
        String newStatus = statusComboBox.getValue();

        if (selectedVehicle == null || newStatus == null || newStatus.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please select a vehicle and a new status.");
            return;
        }

        selectedVehicle.setAvailabilityStatus(newStatus);
        dbHandler.updateVehicle(selectedVehicle);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle availability updated successfully.");

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
        Stage stage = (Stage) vehicleComboBox.getScene().getWindow();
        stage.close();
    }
}