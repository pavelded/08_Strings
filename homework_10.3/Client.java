public abstract class Client {

    private double clientMoney;

    public double getAmount() {
        return clientMoney;
    }

    public void put(double amountToPut) {
        if (amountToPut > 0) {
            clientMoney += amountToPut;
        } else {
        }
    }

    public void take(double amountToTake) {
        if (amountToTake > 0 && clientMoney > amountToTake) {
            clientMoney = clientMoney - amountToTake;
        } else {
        }
    }

}
