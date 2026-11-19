package core;

import parking.ParkingSpot;
import vehicle.Vehicle;
import java.util.Map;
import java.util.List;
public class ParkingManager {
    private Map<Vehicle, List<ParkingSpot>> availableSpots;
    private Map<Vehicle, ParkingSpot> vehicleToParkingSpot;

    public ParkingManager(){

    }

    public ParkingSpot findParkingSpotForVehicle(Vehicle vehicle){

        return null;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {
        return null;
    }

    public void unParkVehicle(Vehicle vehicle){

    }
}
