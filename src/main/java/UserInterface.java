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
            System.out.println("║  10 - Sell / Lease a vehicle             ║");
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
                case "10": processSellLeaseVehicleRequest();
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

    private void processSellLeaseVehicleRequest() {
        int vin = getValidInt("  Enter VIN of vehicle to sell/lease: ");

        Vehicle selected = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                selected = v;
                break;
            }
        }

        if (selected == null) {
            System.out.println("  No vehicle found with VIN: " + vin);
            return;
        }

        System.out.println("\n  Vehicle found:");
        System.out.println("  " + selected);

        System.out.print("\n  Customer name: ");
        String customerName = kb.nextLine().trim();
        System.out.print("  Customer email: ");
        String customerEmail = kb.nextLine().trim();


        System.out.print("  Sale or Lease? (S/L): ");
        String contractType = kb.nextLine().trim().toUpperCase();

        // Can't lease a vehicle over 3 years old
        int currentYear = java.time.LocalDate.now().getYear();
        int vehicleAge  = currentYear - selected.getYear();

        if (contractType.equals("L") && vehicleAge > 3) {
            System.out.println("  Cannot lease a vehicle over 3 years old.");
            return;
        }

        String date = java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter
                        .ofPattern("yyyyMMdd"));

        Contract contract;

        if (contractType.equals("S")) {
            System.out.print("  Finance the vehicle? (yes/no): ");
            String financeInput = kb.nextLine().trim().toLowerCase();
            boolean finance = financeInput.equals("yes");

            contract = new SalesContract(date, customerName,
                    customerEmail, selected, finance);

            System.out.printf("%n  --- Sale Summary ---%n");
            System.out.printf("  Vehicle Price:   $%.2f%n",
                    selected.getPrice());
            System.out.printf("  Sales Tax:       $%.2f%n",
                    ((SalesContract) contract).getSalesTaxAmount());
            System.out.printf("  Recording Fee:   $%.2f%n",
                    ((SalesContract) contract).getRecordingFee());
            System.out.printf("  Processing Fee:  $%.2f%n",
                    ((SalesContract) contract).getProcessingFee());
            System.out.printf("  Total Price:     $%.2f%n",
                    contract.getTotalPrice());

            if (finance) {
                System.out.printf("  Monthly Payment: $%.2f%n",
                        contract.getMonthlyPayment());
            } else {
                System.out.println("  Payment: Cash / No financing");
            }

        } else if (contractType.equals("L")) {
            contract = new LeaseContract(date, customerName,
                    customerEmail, selected);

            System.out.printf("%n  --- Lease Summary ---%n");
            System.out.printf("  Vehicle Price:        $%.2f%n",
                    selected.getPrice());
            System.out.printf("  Expected Ending Value:$%.2f%n",
                    ((LeaseContract) contract).getExpectedEndingValue());
            System.out.printf("  Lease Fee:            $%.2f%n",
                    ((LeaseContract) contract).getLeaseFee());
            System.out.printf("  Total Lease Cost:     $%.2f%n",
                    contract.getTotalPrice());
            System.out.printf("  Monthly Payment:      $%.2f%n",
                    contract.getMonthlyPayment());

        } else {
            System.out.println("  Invalid option. Enter S or L.");
            return;
        }


        ContractFileManager.saveContract(contract);
        dealership.removeVehicle(selected);
        DealershipFileManager.saveDealership(dealership);

        System.out.println("\n  Contract saved. Vehicle removed from inventory.");
    }
}
