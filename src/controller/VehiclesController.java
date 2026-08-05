package controller;

import database.DatabaseHandler;
import model.User;
import model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListCell;

import java.util.List;

public class VehiclesController {

    @FXML
    private ListView<Vehicle> vehiclesListView;

    private DatabaseHandler dbHandler;
    private User currentUser;

    public VehiclesController() {
        dbHandler = new DatabaseHandler();
    }

    public void setUser(User user) {
        this.currentUser = user;
        loadVehicles();
    }

    private void loadVehicles() {
        List<Vehicle> vehicles = dbHandler.getAllVehicles();
        ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList(vehicles);
        vehiclesListView.setItems(vehicleList);

        vehiclesListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Vehicle vehicle, boolean empty) {
                super.updateItem(vehicle, empty);
                if (empty || vehicle == null) {
                    setText(null);
                } else {
                    setText("Make: " + vehicle.getMake() + "\n"
                            + "Model: " + vehicle.getModel() + "\n"
                            + "Rental Price: $" + vehicle.getRentalPrice() + "/day\n"
                            + "Status: " + vehicle.getAvailabilityStatus());
                }
            }
        });
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        Vehicle selectedVehicle = vehiclesListView.getSelectionModel().getSelectedItem();
        if (selectedVehicle == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a vehicle to book.");
            return;
        }

        if (!selectedVehicle.getAvailabilityStatus().equalsIgnoreCase("Available")) {
            showAlert(Alert.AlertType.ERROR, "Unavailable", "Selected vehicle is not available for booking.");
            return;
        }

        java.sql.Date rentalDate = java.sql.Date.valueOf(java.time.LocalDate.now());
        java.sql.Date returnDate = java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(5));

        model.Booking booking = new model.Booking(0, currentUser.getUserId(), selectedVehicle.getVehicleId(), rentalDate, returnDate, "Confirmed");
        dbHandler.addBooking(booking);

        selectedVehicle.setAvailabilityStatus("Rented");
        dbHandler.updateVehicle(selectedVehicle);

        showAlert(Alert.AlertType.INFORMATION, "Booking Confirmed", "Your booking has been confirmed.");

        loadVehicles();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
