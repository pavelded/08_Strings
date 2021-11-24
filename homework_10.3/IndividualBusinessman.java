public class IndividualBusinessman extends Client {

    private double iBMoney = 0;

    @Override
    public double getAmount() {
        return iBMoney;
    }

    @Override
    public void put(double amountToPut) {
        if (amountToPut > 0 && amountToPut < 1000) {
            final double percent1 = 0.01;
            iBMoney += amountToPut - amountToPut*percent1;
        } else if (amountToPut >= 1000) {
            final double percent2 = 0.005;
            iBMoney += amountToPut - amountToPut*percent2;
        }
    }

    @Override
    public void take(double amountToTake) {
        if (amountToTake > 0 && iBMoney > amountToTake) {
            iBMoney = iBMoney - amountToTake;
        } else {
        }
    }

}
