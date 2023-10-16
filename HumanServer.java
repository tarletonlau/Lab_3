import java.util.function.Supplier;

class HumanServer extends Server {
    private final int serverIndex;
    private final double serverAvailableTime;
    private final ImList<Customer> serverQueue;
    private final int qMax;
    private final Supplier<Double> restTimesSupplier;

    HumanServer(int serverIndex, double serverAvailableTime, int qMax, ImList<Customer> serverQueue, Supplier<Double> restTimesSupplier) {
        super(serverIndex, serverAvailableTime, qMax, serverQueue);

        this.serverIndex = serverIndex;
        this.serverAvailableTime = serverAvailableTime;
        this.qMax = qMax;
        this.serverQueue = serverQueue;
        this.restTimesSupplier = restTimesSupplier;
    }

    @Override
    public Server use(double time) {
        double restTime = restTimesSupplier.get();
        time += restTime;

        System.out.println(restTime);

        return new HumanServer(this.serverIndex, time, this.qMax, this.serverQueue, this.restTimesSupplier);
    }

    public double getRestTime() {
        return restTimesSupplier.get();
    }
}
