public class BankAccount {

  private double bankAccountMoney = 0;

  public BankAccount() {
  }

  public double getAmount() {
    return bankAccountMoney;
  }

  public void put(double amountToPut) {
    if (amountToPut > 0) {
      bankAccountMoney += amountToPut;
    } else {
    }
  }

  public void take(double amountToTake) {
    if (amountToTake > 0 && bankAccountMoney > amountToTake) {
      bankAccountMoney = bankAccountMoney - amountToTake;
    } else {
    }
  }
}
