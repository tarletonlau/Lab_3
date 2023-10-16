import java.util.function.Supplier;

class Customer {
    private final double arrivalTime;
    private final Supplier<Double> serviceTimeSupplier;
    private final int id;

    Customer(double arrivalTime, Supplier<Double> serviceTimeSupplier, Integer id) {
        this.arrivalTime = arrivalTime;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.id = id;
    }

    /*
    function of customer:
    - hold the arrival time
    - hold the service time
    - hold the id

     */

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServiceTime() {
        return this.serviceTimeSupplier.get();
    }

    public String toString() {
        return String.format("%d",this.id);
    }
}
