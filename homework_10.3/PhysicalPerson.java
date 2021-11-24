public class PhysicalPerson extends Client {

    private double physicalPersonMoney = 0;

    @Override
    public double getAmount() {
        return physicalPersonMoney;
    }

    @Override
    public void put(double amountToPut) {
        if (amountToPut > 0) {
            physicalPersonMoney += amountToPut;
        } else {
        }
    }

    @Override
    public void take(double amountToTake) {
        if (amountToTake > 0 && physicalPersonMoney > amountToTake) {
            physicalPersonMoney = physicalPersonMoney - amountToTake;
        } else {
        }
    }
}
