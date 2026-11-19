package Fare;

public class BaseFareStrategy implements FareStrategy{
    private final double SMALL_VEHICLE_RATE = 10.0;
    private final double MEDIUM_VEHICLE_RATE = 20.0;
    private final double LARGE_VEHICLE_RATE = 30.0;

    @Override
    public Double calculateFare(Ticket ticket){
        return 0.0;
    }
}
