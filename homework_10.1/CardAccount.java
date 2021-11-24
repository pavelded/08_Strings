public class CardAccount extends BankAccount {
    // не забывайте, обращаться к методам и конструкторам родителя
    // необходимо используя super, например, super.put(10D);
    private double cardAccountMoney = 0;
    private double percent = 0.01;

    public CardAccount() {
    }

    @Override
    public double getAmount() {
        return cardAccountMoney;
    }

    @Override
    public void put(double amountToPut) {
        if (amountToPut > 0) {
            cardAccountMoney += amountToPut;
        } else {
        }
    }

    @Override
    public void take(double amountToTake) {
        if (amountToTake > 0 && cardAccountMoney > amountToTake) {
            cardAccountMoney = cardAccountMoney - amountToTake - amountToTake*percent;
        } else {
        }
    }
}
