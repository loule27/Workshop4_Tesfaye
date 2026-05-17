public class SalesContract extends Contract {
    private boolean financeOption;

    private static final double SALES_TAX_RATE    = 0.05;
    private static final double RECORDING_FEE     = 100.00;
    private static final double PROCESSING_FEE_LOW  = 295.00;
    private static final double PROCESSING_FEE_HIGH = 495.00;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold,  boolean financeOption) {
        super(date, customerName, customerEmail, vehicleSold);
        this.financeOption = financeOption;
    }

    public boolean isFinanceOption() { return financeOption; }
    public void setFinanceOption(boolean financeOption) {
        this.financeOption = financeOption;
    }

    // Convenience getters — calculated from vehicle price
    public double getSalesTaxAmount() {
        return getVehicleSold().getPrice() * SALES_TAX_RATE;
    }

    public double getRecordingFee() {
        return RECORDING_FEE;
    }

    public double getProcessingFee() {
        return getVehicleSold().getPrice() < 10000
                ? PROCESSING_FEE_LOW
                : PROCESSING_FEE_HIGH;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice()
                + getSalesTaxAmount()
                + getRecordingFee()
                + getProcessingFee();
    }

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) {
            return 0.00;
        }

        double principal = getTotalPrice();
        double annualRate;
        int months;

        if (principal >= 10000) {
            annualRate = 4.25;
            months = 48;
        } else {
            annualRate = 5.25;
            months = 24;
        }

        double monthlyRate = (annualRate / 100) / 12;

       // M = P * [r(1+r)^n] / [(1+r)^n - 1]

        return principal * (monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);
    }
}
