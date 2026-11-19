package parking;

import enums.VehicleSize;
import vehicle.Vehicle;

public interface ParkingSpot {
    boolean isSpaceAvailable();

    int getSpotNumber();

    void occupySpot(Vehicle vehicle);
    void vacateSpot();

    VehicleSize getVehicleSize();

}
