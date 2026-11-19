package Fare;

import parking.ParkingSpot;

import java.time.LocalDateTime;

/*
ticketId - String
    - vehicle - Vehicle
    - parkingSpot - ParkingSpot
    - entryTime - LocalDateTime
    - exitTime - LocalDateTime
    + calculateParkingDuration() - Double

 */
public class Ticket {
    private String ticketId;
    private ParkingSpot parkingSpot;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Double calculateParkingDuration(){
        // TODO:

        return 0.0;
    }
}
