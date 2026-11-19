package parking;

import enums.VehicleSize;
import vehicle.Vehicle;

public class OverSizeSpot implements ParkingSpot{
    private int spotNumber;
    private Vehicle vehicle;
    public OverSizeSpot(int spotNumber, Vehicle vehicle){
        this.spotNumber = spotNumber;
        this.vehicle = vehicle;
    }
    @Override
    public boolean isSpaceAvailable() {
        return false;
    }

    @Override
    public int getSpotNumber() {
        return 0;
    }

    @Override
    public void occupySpot(Vehicle vehicle) {

    }

    @Override
    public void vacateSpot() {

    }

    @Override
    public VehicleSize getVehicleSize() {
        return VehicleSize.LARGE;
    }
}
