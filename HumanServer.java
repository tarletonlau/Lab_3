import java.util.function.Supplier;

class HumanServer extends Server {
    private final Supplier<Double> restTimesSupplier;

    HumanServer(int serverIndex, double serverAvailableTime, int qMax, ImList<Customer> serverQueue, Supplier<Double> restTimesSupplier) {
        super(serverIndex, serverAvailableTime, qMax, serverQueue);
        this.restTimesSupplier = restTimesSupplier;
    }

    // ============================== UPDATE METHODS FOR HUMAN SERVER =========================================
    @Override
    public Server addQueue(Customer customer) {
        ImList<Customer> updatedQueue = this.serverQueue.add(customer);
        return new HumanServer(this.serverIndex, this.serverAvailableTime, this.qMax, updatedQueue, this.restTimesSupplier);
    }

    @Override
    public Server deQueue() {
        ImList<Customer> updatedQueue = this.serverQueue.remove(0);
        return new HumanServer(this.serverIndex, this.serverAvailableTime, this.qMax, updatedQueue, this.restTimesSupplier);
    }

    // ============================================================================================

    @Override
    public Server use(double time) {
        double restTime = restTimesSupplier.get();
        time += restTime;

        return new HumanServer(this.serverIndex, time, this.qMax, this.serverQueue, this.restTimesSupplier);
    }

}
