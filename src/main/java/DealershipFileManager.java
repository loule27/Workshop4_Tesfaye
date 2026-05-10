import java.io.*;

public class DealershipFileManager {

    private static final String FILE_PATH =
            "src/main/resources/inventory.csv";

    public static Dealership getDealership() {
        Dealership dealership = null;

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(FILE_PATH));

            String firstLine = reader.readLine();
            String[] dealerParts = firstLine.split("\\|");
            dealership = new Dealership(
                    dealerParts[0].trim(),
                    dealerParts[1].trim(),
                    dealerParts[2].trim());

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                int vin = Integer.parseInt(parts[0].trim());
                int year = Integer.parseInt(parts[1].trim());
                String make = parts[2].trim();
                String model = parts[3].trim();
                String vehicleType = parts[4].trim();
                String color = parts[5].trim();
                int odometer = Integer.parseInt(parts[6].trim());
                double price = Double.parseDouble(parts[7].trim());

                dealership.addVehicle(new Vehicle(vin, year, make, model,
                        vehicleType, color, odometer, price));
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Issue reading the file.");
        }

        return dealership;
    }

    public static void saveDealership(Dealership dealership) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);

            writer.write(String.format("%s|%s|%s",
                    dealership.getName(),
                    dealership.getAddress(),
                    dealership.getPhone()));

            for (Vehicle v : dealership.getAllVehicles()) {
                writer.write(System.lineSeparator());
                writer.write(String.format("%d|%d|%s|%s|%s|%s|%d|%.2f",
                        v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                        v.getVehicleType(), v.getColor(),
                        v.getOdometer(), v.getPrice()));
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Issue saving the file.");
        }
    }
}
