//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Create parking manager
        ParkingManager parkingManager = new ParkingManager();

        // 2. Create vehicles
        Vehicle car1 = new Car("KA-01-AB-1234");
        Vehicle car2 = new Car("KA-02-CD-5678");
        Vehicle bike1 = new MotorCycle("KA-05-EF-9876");
        Vehicle truck1 = new Truck("TN-09-GH-1111");

        // 3. Park vehicles
        parkingManager.parkVehicle(car1);
        parkingManager.parkVehicle(car2);
        parkingManager.parkVehicle(bike1);
        parkingManager.parkVehicle(truck1);

        // 4. Print status / ticket / slot details
        System.out.println("Vehicle parked: " + car1.getVehicleNumber());
        System.out.println("Vehicle parked: " + bike1.getVehicleNumber());

        // 5. Unpark vehicle
        parkingManager.unParkVehicle(car1);

        // 6. Try parking again / check availability
        Vehicle car3 = new Car("MH-12-XY-4321");
        parkingManager.parkVehicle(car3);

        // 7. Final display or print
        System.out.println("Parking flow completed.");
        }
    }
}