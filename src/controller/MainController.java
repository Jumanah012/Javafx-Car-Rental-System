package controller;

import model.Administrator;
import model.RentalAgent;
import model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button adminButton;

    @FXML
    private Button rentalAgentButton;

    private User currentUser;

    public void setUser(User user){
        this.currentUser = user;

        if(currentUser instanceof Administrator){
            adminButton.setVisible(true);
            rentalAgentButton.setVisible(false);
        } else if(currentUser instanceof RentalAgent){
            adminButton.setVisible(false);
            rentalAgentButton.setVisible(true);
        } else {
            adminButton.setVisible(false);
            rentalAgentButton.setVisible(false);
        }
    }

    @FXML
    private void handleBrowseVehicles(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vehicles.fxml"));
            Scene scene = new Scene(loader.load());

            VehiclesController vehiclesController = loader.getController();
            vehiclesController.setUser(currentUser);

            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Available Vehicles");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the Vehicles view.");
        }
    }

    @FXML
    private void handleAdminDashboard(ActionEvent event) {
        if(currentUser instanceof Administrator){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin_dashboard.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Administrator Dashboard");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the Administrator Dashboard.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have administrator privileges.");
        }
    }

    @FXML
    private void handleRentalAgentDashboard(ActionEvent event) {
        if(currentUser instanceof RentalAgent){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/rental_agent_dashboard.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("RentalAgent Dashboard");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the RentalAgent Dashboard.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Access Denied", "You do not have RentalAgent privileges.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}