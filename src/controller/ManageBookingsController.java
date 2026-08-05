package controller;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import model.Booking;
import model.User;
import model.Vehicle;

import java.util.List;

public class ManageBookingsController {

    @FXML
    private ListView<BookingDisplay> bookingsListView;

    private DatabaseHandler dbHandler;
    private ObservableList<BookingDisplay> bookingList;

    public ManageBookingsController() {
        dbHandler = new DatabaseHandler();
    }

    @FXML
    private void initialize() {
        loadBookings();

        bookingsListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(BookingDisplay booking, boolean empty) {
                super.updateItem(booking, empty);
                if (empty || booking == null) {
                    setText(null);
                } else {
                    setText("Booking ID: " + booking.getBookingId() + "\n"
                            + "Customer Email: " + booking.getCustomerEmail() + "\n"
                            + "Vehicle: " + booking.getVehicle() + "\n"
                            + "Rental Date: " + booking.getRentalDate() + "\n"
                            + "Return Date: " + booking.getReturnDate() + "\n"
                            + "Status: " + booking.getStatus());
                }
            }
        });
    }

    private void loadBookings() {
        List<Booking> bookings = dbHandler.getAllBookings();
        bookingList = FXCollections.observableArrayList();

        for (Booking b : bookings) {
            User customer = dbHandler.getUser(b.getCustomerId());
            Vehicle vehicle = dbHandler.getVehicle(b.getVehicleId());

            String customerEmail = (customer != null) ? customer.getEmail() : "Unknown";
            String vehicleInfo = (vehicle != null) ? vehicle.getMake() + " " + vehicle.getModel() + " (" + vehicle.getYear() + ")" : "Unknown";

            BookingDisplay bd = new BookingDisplay(
                    b.getBookingId(),
                    customerEmail,
                    vehicleInfo,
                    b.getRentalDate().toString(),
                    b.getReturnDate().toString(),
                    b.getStatus()
            );
            bookingList.add(bd);
        }

        bookingsListView.setItems(bookingList);
    }

    @FXML
    private void handleUpdateStatus(ActionEvent event) {
        BookingDisplay selectedBooking = bookingsListView.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a booking to update.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(selectedBooking.getStatus(), "Confirmed", "Completed", "Cancelled");
        dialog.setTitle("Update Booking Status");
        dialog.setHeaderText(null);
        dialog.setContentText("Select new status:");

        dialog.showAndWait().ifPresent(newStatus -> {
            Booking booking = dbHandler.getBooking(selectedBooking.getBookingId());
            if (booking != null) {
                booking.setStatus(newStatus);
                dbHandler.updateBooking(booking);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking status updated successfully.");
                loadBookings();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch booking details.");
            }
        });
    }

    @FXML
    private void handleDeleteBooking(ActionEvent event) {
        BookingDisplay selectedBooking = bookingsListView.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a booking to delete.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Booking");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Are you sure you want to delete this booking?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dbHandler.removeBooking(selectedBooking.getBookingId());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking deleted successfully.");
                loadBookings();
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class BookingDisplay {
        private final javafx.beans.property.SimpleIntegerProperty bookingId;
        private final javafx.beans.property.SimpleStringProperty customerEmail;
        private final javafx.beans.property.SimpleStringProperty vehicle;
        private final javafx.beans.property.SimpleStringProperty rentalDate;
        private final javafx.beans.property.SimpleStringProperty returnDate;
        private final javafx.beans.property.SimpleStringProperty status;

        public BookingDisplay(int bookingId, String customerEmail, String vehicle, String rentalDate, String returnDate, String status) {
            this.bookingId = new javafx.beans.property.SimpleIntegerProperty(bookingId);
            this.customerEmail = new javafx.beans.property.SimpleStringProperty(customerEmail);
            this.vehicle = new javafx.beans.property.SimpleStringProperty(vehicle);
            this.rentalDate = new javafx.beans.property.SimpleStringProperty(rentalDate);
            this.returnDate = new javafx.beans.property.SimpleStringProperty(returnDate);
            this.status = new javafx.beans.property.SimpleStringProperty(status);
        }

        public int getBookingId() {
            return bookingId.get();
        }

        public String getCustomerEmail() {
            return customerEmail.get();
        }

        public String getVehicle() {
            return vehicle.get();
        }

        public String getRentalDate() {
            return rentalDate.get();
        }

        public String getReturnDate() {
            return returnDate.get();
        }

        public String getStatus() {
            return status.get();
        }
    }
}
