public class LeaseContract extends Contract {

    private static final double ENDING_VALUE_RATE = 0.50;
    private static final double LEASE_FEE_RATE    = 0.07;
    private static final double LEASE_RATE        = 4.00;
    private static final int    LEASE_MONTHS      = 36;

    public LeaseContract(String date, String customerName,
                         String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    public double getExpectedEndingValue() {
        return getVehicleSold().getPrice() * ENDING_VALUE_RATE;
    }

    public double getLeaseFee() {
        return getVehicleSold().getPrice() * LEASE_FEE_RATE;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice()
                - getExpectedEndingValue()
                + getLeaseFee();
    }

    @Override
    public double getMonthlyPayment() {
        double principal = getTotalPrice();
        double monthlyRate = (LEASE_RATE / 100) / 12;

        return principal
                * (monthlyRate * Math.pow(1 + monthlyRate, LEASE_MONTHS))
                / (Math.pow(1 + monthlyRate, LEASE_MONTHS) - 1);
    }
}