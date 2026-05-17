import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {

    private static final String FILE_PATH =
            "src/main/resources/contracts.csv";

    private static String buildCommonFields(Contract contract) {
        Vehicle v = contract.getVehicleSold();
        return String.format("%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f",
                contract.getDate(),
                contract.getCustomerName(),
                contract.getCustomerEmail(),
                v.getVin(),
                v.getYear(),
                v.getMake(),
                v.getModel(),
                v.getVehicleType(),
                v.getColor(),
                v.getOdometer(),
                v.getPrice());
    }

    public static void saveContract(Contract contract) {
        try {
            File file = new File(FILE_PATH);
            FileWriter writer = new FileWriter(file, true);

            // adds new line before if the file already has content
            if (file.length() > 0) {
                writer.write(System.lineSeparator());
            }

            String common = buildCommonFields(contract);
            String contractLine;

            if (contract instanceof SalesContract) {
                SalesContract sc = (SalesContract) contract;
                contractLine = String.format("SALE|%s|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                        common,
                        sc.getSalesTaxAmount(),
                        sc.getRecordingFee(),
                        sc.getProcessingFee(),
                        sc.getTotalPrice(),
                        sc.isFinanceOption() ? "YES" : "NO",
                        sc.getMonthlyPayment());

            } else if (contract instanceof LeaseContract) {
                LeaseContract lc = (LeaseContract) contract;
                contractLine = String.format("LEASE|%s|%.2f|%.2f|%.2f|%.2f",
                        common,
                        lc.getExpectedEndingValue(),
                        lc.getLeaseFee(),
                        lc.getTotalPrice(),
                        lc.getMonthlyPayment());
            } else {
                writer.close();
                return;
            }

            writer.write(contractLine);
            writer.close();

        } catch (IOException e) {
            System.out.println("Problem saving contract.");
        }
    }
}
