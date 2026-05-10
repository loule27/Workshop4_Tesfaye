import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner kb = new Scanner(System.in);

    private void init() {
        dealership = DealershipFileManager.getDealership();
    }

    public void display() {
        init();

        while (true) {
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.printf( "║  %-40s║%n", dealership.getName());
            System.out.printf( "║  %-40s║%n", dealership.getAddress());
            System.out.printf( "║  %-40s║%n", dealership.getPhone());
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║  1  - Search by price range              ║");
            System.out.println("║  2  - Search by make and model           ║");
            System.out.println("║  3  - Search by year range               ║");
            System.out.println("║  4  - Search by color                    ║");
            System.out.println("║  5  - Search by mileage range            ║");
            System.out.println("║  6  - Search by vehicle type             ║");
            System.out.println("║  7  - View all vehicles                  ║");
            System.out.println("║  8  - Add a vehicle                      ║");
            System.out.println("║  9  - Remove a vehicle                   ║");
            System.out.println("║  99 - Exit                               ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("Enter option: ");

            String userInput = kb.nextLine().trim();

            switch (userInput) {
                case "1":  processGetByPriceRequest();
                break;
                case "2":  processGetByMakeModelRequest();
                break;
                case "3":  processGetByYearRequest();
                break;
                case "4":  processGetByColorRequest();
                break;
                case "5":  processGetByMileageRequest();
                break;
                case "6":  processGetByVehicleTypeRequest();
                break;
                case "7":  processGetAllVehiclesRequest();
                break;
                case "8":  processAddVehicleRequest();
                break;
                case "9":  processRemoveVehicleRequest();
                break;
                case "99":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println(
                            "\"" + userInput + "\" is not a valid option.");
            }
        }
    }

    private double getValidDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = kb.nextLine().trim();
            try {
                return Double.parseDouble(raw);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    private int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = kb.nextLine().trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a whole number.");
            }
        }
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("\n  No vehicles  found.");
            return;
        }

        System.out.printf("%n  %-8s %-6s %-12s %-12s %-8s %-10s %-12s %s%n",
                "VIN", "Year", "Make", "Model",
                "Type", "Color", "Odometer", "Price");
        System.out.println("  " + "─".repeat(80));

        for (Vehicle v : vehicles) {
            System.out.println("  " + v);
        }

        System.out.println("  " + "─".repeat(80));
        System.out.printf("  Results:               %d vehicle(s)%n",
                vehicles.size());
    }

    private void processGetByPriceRequest() {
        double min = getValidDouble("  Min price ($): ");
        double max = getValidDouble("  Max price ($): ");

        if (min > max) {
            System.out.println("  Min price cannot be greater than max price.");
            return;
        }

        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    private void processGetByMakeModelRequest() {
        System.out.print("  Make: ");
        String make = kb.nextLine().trim();
        System.out.print("  Model: ");
        String model = kb.nextLine().trim();

        if (make.isEmpty() || model.isEmpty()) {
            System.out.println("  Make and model cannot be blank.");
            return;
        }

        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    private void processGetByYearRequest() {
        int min = getValidInt("  From year: ");
        int max = getValidInt("  To year: ");

        if (min > max) {
            System.out.println("  Start year cannot be after end year.");
            return;
        }

        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    private void processGetByColorRequest() {
        System.out.print("  Color: ");
        String color = kb.nextLine().trim();

        if (color.isEmpty()) {
            System.out.println("  Color cannot be blank.");
            return;
        }

        displayVehicles(dealership.getVehiclesByColor(color));
    }

    private void processGetByMileageRequest() {
        int min = getValidInt("  Min mileage: ");
        int max = getValidInt("  Max mileage: ");

        if (min > max) {
            System.out.println("  Min mileage cannot exceed max mileage.");
            return;
        }

        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    private void processGetByVehicleTypeRequest() {
        System.out.print("  Type (car / truck / SUV / van): ");
        String type = kb.nextLine().trim();

        if (type.isEmpty()) {
            System.out.println("  Type cannot be blank.");
            return;
        }

        displayVehicles(dealership.getVehiclesByType(type));
    }

    private void processGetAllVehiclesRequest() {
        System.out.println("\n  Full inventory:");
        displayVehicles(dealership.getAllVehicles());
    }

    private void processAddVehicleRequest() {
        int vin = getValidInt("  VIN: ");
        int year = getValidInt("  Year: ");
        System.out.print("  Make: ");
        String make  = kb.nextLine().trim();
        System.out.print("  Model: ");
        String model = kb.nextLine().trim();
        System.out.print("  Type (car / truck / SUV / van): ");
        String type  = kb.nextLine().trim();
        System.out.print("  Color: ");
        String color = kb.nextLine().trim();
        int odometer = getValidInt("  Odometer: ");
        double price = getValidDouble("  Price ($): ");

        Vehicle newVehicle = new Vehicle(vin, year, make, model,
                type, color, odometer, price);

        dealership.addVehicle(newVehicle);
        DealershipFileManager.saveDealership(dealership);
        System.out.println("  Vehicle added successfully.");
    }

    private void processRemoveVehicleRequest() {
        int targetVin = getValidInt("  Enter VIN to remove: ");

        Vehicle toRemove = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == targetVin) {
                toRemove = v;
                break;
            }
        }

        if (toRemove == null) {
            System.out.println("  No vehicle found with VIN: " + targetVin);
            return;
        }

        System.out.println("\n  Found:");
        System.out.println("  " + toRemove);
        System.out.print("\n  Are you sure you want to remove this vehicle? (yes/no): ");
        String confirm = kb.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            dealership.removeVehicle(toRemove);
            DealershipFileManager.saveDealership(dealership);
            System.out.println("  Vehicle successfully removed.");
        } else {
            System.out.println("  Remove cancelled.");
        }
    }
}
