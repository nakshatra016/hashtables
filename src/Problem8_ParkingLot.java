import java.util.*;

public class Problem8_ParkingLot {

    private String[] spots;
    private int size;

    public Problem8_ParkingLot(int size) {
        this.size = size;
        spots = new String[size];
    }

    private int hash(String license) {
        return Math.abs(license.hashCode()) % size;
    }

    public void parkVehicle(String license) {
        int index = hash(license);
        int start = index;

        while (spots[index] != null) {
            index = (index + 1) % size;
            if (index == start) {
                System.out.println("Parking Full");
                return;
            }
        }

        spots[index] = license;
        System.out.println("Parked at spot " + index);
    }

    public void exitVehicle(String license) {
        for (int i = 0; i < size; i++) {
            if (license.equals(spots[i])) {
                spots[i] = null;
                System.out.println("Vehicle exited from spot " + i);
                return;
            }
        }
    }

    public static void main(String[] args) {
        Problem8_ParkingLot lot = new Problem8_ParkingLot(10);

        lot.parkVehicle("ABC123");
        lot.parkVehicle("XYZ999");
        lot.exitVehicle("ABC123");
    }
}