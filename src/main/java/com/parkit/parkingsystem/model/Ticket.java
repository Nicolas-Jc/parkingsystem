package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;
    //FONCTIONNALITE_2 indicateur pour savoir si remise à appliquer ou pas;
    private boolean topDiscount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Correction Warning FindBugs
    public Date getInTime() {
        if (inTime != null) {
            return new Date(inTime.getTime());
        } else {
            return null;
        }
    }
    
    public void setInTime(Date inTime) {
        if (inTime != null) {
            this.inTime = new Date(inTime.getTime());
        } else {
            this.inTime = null;
        }
    }

    // Correction Warning FindBugs
    public Date getOutTime() {
        if (outTime != null) {
            return new Date(outTime.getTime());
        } else {
            return null;
        }
    }

    public void setOutTime(Date outTime) {
        if (outTime != null) {
            this.outTime = new Date(outTime.getTime());
        } else {
            this.outTime = null;
        }
    }

    //FONCTIONNALITE_2 AJOUT POUR GESTION TOP_REMISE
    public boolean isDiscount() {
        return topDiscount;
    }

    public void setDiscount(boolean topDiscount) {
        this.topDiscount = topDiscount;
    }
    // FIN FONCTIONNALITE_2
}
