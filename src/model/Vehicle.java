package model;

public class Vehicle implements Displayable {
    private int vehicleId;
    private String make;
    private String model;
    private int year;
    private String availabilityStatus;
    private double rentalPrice;

    public Vehicle(int vehicleId, String make, String model, int year, String availabilityStatus, double rentalPrice){
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.availabilityStatus = availabilityStatus;
        this.rentalPrice = rentalPrice;
    }

    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    @Override
    public String displayInfo() {
        return "Vehicle [Make: " + make + ", Model: " + model + ", Year: " + year + ", Price: $" + rentalPrice + "]";
    }

    @Override
    public String toString(){
        return "Vehicle [vehicleId=" + vehicleId + ", make=" + make + ", model=" + model + ", year=" + year
                + ", availabilityStatus=" + availabilityStatus + ", rentalPrice=" + rentalPrice + "]";
    }
}