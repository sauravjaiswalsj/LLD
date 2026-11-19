package vehicle;

import enums.VehicleSize;

public class MotorCycle implements Vehicle{
    private String licensePlate;

    public MotorCycle(String licensePlate){
        this.licensePlate = licensePlate;
    }

    @Override
    public VehicleSize getVehicleSize() {
        return VehicleSize.SMALL;
    }

    @Override
    public String getLicensePlate() {
        return this.licensePlate;
    }
}
