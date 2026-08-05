package model;

import java.sql.Date;

public class Booking {
    private int bookingId;
    private int customerId;
    private int vehicleId;
    private Date rentalDate;
    private Date returnDate;
    private String status;

    public Booking(int bookingId, int customerId, int vehicleId, Date rentalDate, Date returnDate, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getBookingId(){
        return bookingId;
    }

    public int getCustomerId(){
        return customerId;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public int getVehicleId(){
        return vehicleId;
    }

    public void setVehicleId(int vehicleId){
        this.vehicleId = vehicleId;
    }

    public Date getRentalDate(){
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate){
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate(){
        return returnDate;
    }

    public void setReturnDate(Date returnDate){
        this.returnDate = returnDate;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "Booking [bookingId=" + bookingId + ", customerId=" + customerId + ", vehicleId=" + vehicleId
                + ", rentalDate=" + rentalDate + ", returnDate=" + returnDate + ", status=" + status + "]";
    }
}