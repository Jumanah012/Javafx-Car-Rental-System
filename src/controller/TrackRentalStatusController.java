package controller;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListCell;
import model.Booking;
import model.User;
import model.Vehicle;

import java.util.List;

public class TrackRentalStatusController {

    @FXML
    private ListView<BookingDisplay> rentalStatusListView;

    private DatabaseHandler dbHandler;
    private ObservableList<BookingDisplay> bookingList;

    public TrackRentalStatusController() {
        dbHandler = new DatabaseHandler();
    }

    @FXML
    private void initialize() {
        loadRentalStatuses();

        rentalStatusListView.setCellFactory(listView -> new ListCell<>() {
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

    private void loadRentalStatuses() {
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

        rentalStatusListView.setItems(bookingList);
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
