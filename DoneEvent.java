class DoneEvent extends Event {

    private final Server allocatedServer;

    DoneEvent(Customer customer, Server server, double eventTime) {
        super(customer,eventTime);
        this.allocatedServer = server;
    }

    // ==========  Event interface ==================

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats.incrementServed();
    }

    // ============================== Main logic =======================================

    @Override
    public Pair<Shop, Event> process(Shop shop) {
        shop = shop.serverRest(this.allocatedServer);
        return new Pair<Shop,Event>(shop, this);
    }

    // ==================================================

    @Override
    public String toString() {
        return String.format("%s done serving by %s",
                super.toString(),
                this.allocatedServer.toString());
    }
}
