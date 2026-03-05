import java.util.*;

class ParkingSpot {
    String plate;
    long entryTime;
}

public class ParkingLot {

    static int SIZE = 500;
    static ParkingSpot[] table = new ParkingSpot[SIZE];

    static int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    public static void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % SIZE;
            probes++;
        }

        ParkingSpot spot = new ParkingSpot();
        spot.plate = plate;
        spot.entryTime = System.currentTimeMillis();

        table[index] = spot;

        System.out.println("Assigned spot #" + index + " (" + probes + " probes)");
    }

    public static void exitVehicle(String plate) {

        int index = hash(plate);

        while (table[index] != null) {

            if (table[index].plate.equals(plate)) {

                long duration = System.currentTimeMillis() - table[index].entryTime;
                table[index] = null;

                System.out.println("Spot #" + index + " freed. Duration: " + duration / 1000 + " seconds");
                return;
            }

            index = (index + 1) % SIZE;
        }

        System.out.println("Vehicle not found");
    }

    public static void main(String[] args) {

        parkVehicle("ABC1234");
        parkVehicle("ABC1235");
        parkVehicle("XYZ9999");

        exitVehicle("ABC1234");
    }
}