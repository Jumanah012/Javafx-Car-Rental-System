package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RentalAgentController {

    @FXML
    private void handleUpdateVehicleAvailability(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_vehicle_availability.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Vehicle Availability");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleManageBookings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/manage_bookings.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Manage Bookings");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleTrackRentalStatus(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/track_rental_status.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Track Rental Status");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}