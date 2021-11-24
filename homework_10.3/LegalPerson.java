public class LegalPerson extends Client {

    private double legalPersonMoney = 0;

    @Override
    public double getAmount() {
        return legalPersonMoney;
    }

    @Override
    public void put(double amountToPut) {
        if (amountToPut > 0) {
            legalPersonMoney += amountToPut;
        } else {
        }
    }

    @Override
    public void take(double amountToTake) {
        final double percent = 0.01;
        if (amountToTake > 0 && legalPersonMoney > amountToTake) {
            legalPersonMoney = legalPersonMoney - amountToTake - amountToTake*percent;
        } else {
        }
    }

}
