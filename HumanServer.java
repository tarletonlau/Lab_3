import java.util.function.Supplier;

class HumanServer extends Server {
    private final Supplier<Double> restTimesSupplier;

    HumanServer(int serverIndex, double serverAvailableTime, int qMax,
                QueueManager queueManager, Supplier<Double> restTimesSupplier) {
        super(serverIndex, serverAvailableTime, qMax, queueManager);
        this.restTimesSupplier = restTimesSupplier;
    }

    /*
    function of humanserver:
    extension of server, with randomised rest times

    variables:
    - everything else same as a regular server
    - restTimeSupplier
        - generates random rest times
    */

    // ======================= METHODS FOR HUMAN SERVER ========================
    @Override
    public Server addQueue(Customer customer) {
        return new HumanServer(this.serverIndex, this.serverAvailableTime, this.qMax,
                this.queueManager.addQueue(customer), this.restTimesSupplier);
    }

    @Override
    public Server use(double time) {
        double restTime = restTimesSupplier.get();
        time += restTime;
        return new HumanServer(this.serverIndex, time, this.qMax,
                this.queueManager, this.restTimesSupplier);
    }

    @Override
    public Server deQueue() {
        return new HumanServer(this.serverIndex, this.serverAvailableTime, this.qMax,
                this.queueManager.deQueue(), this.restTimesSupplier);
    }

    // ==========================================================================
}
