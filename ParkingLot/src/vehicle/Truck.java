package model;

import enums.VehicleSize;

public class Truck implements Vehicle{
    private String licensePlate;

    public Truck(String licensePlate){
        this.licensePlate = licensePlate;
    }
    @Override
    public VehicleSize getVehicleSize() {
        return VehicleSize.MEDIUM;
    }

    @Override
    public String getLicensePlate() {
        return this .licensePlate;
    }
}
