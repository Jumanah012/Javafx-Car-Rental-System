package controller;

import database.DatabaseHandler;
import java.util.List;
import model.Booking;

public class BookingController {
    private DatabaseHandler dbHandler;

    public BookingController() {
        dbHandler = new DatabaseHandler();
    }

    public void addBooking(Booking booking){
        dbHandler.addBooking(booking);
    }

    public void removeBooking(int bookingId){
        dbHandler.removeBooking(bookingId);
    }

    public void updateBooking(Booking booking){
        dbHandler.updateBooking(booking);
    }

    public Booking getBooking(int bookingId){
        return dbHandler.getBooking(bookingId);
    }

    public List<Booking> getAllBookings(){
        return dbHandler.getAllBookings();
    }

    public void close(){
        dbHandler.closeConnection();
    }
}