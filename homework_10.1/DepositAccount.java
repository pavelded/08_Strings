import java.time.LocalDate;

public class DepositAccount extends BankAccount {

    private double depositAccountMoney = 0;
    private LocalDate lastIncome;

    public DepositAccount() {
    }

    @Override
    public double getAmount() {
        return depositAccountMoney;
    }

    @Override
    public void put(double amountToPut) {
        if (amountToPut > 0) {
            lastIncome = LocalDate.now();
            depositAccountMoney += amountToPut;
        } else {
        }
    }

    @Override
    public void take(double amountToTake) {
        LocalDate currentTime = LocalDate.now();
        LocalDate daysDifference = currentTime.minusMonths(1);
        int compareDates = daysDifference.compareTo(lastIncome);

        if (amountToTake > 0 && depositAccountMoney > amountToTake && compareDates >= 0) {
            depositAccountMoney = depositAccountMoney - amountToTake;
        } else {
        }
    }

}
