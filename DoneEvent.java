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
        // cop out for finding out what is a done/leave event
        // probably replace in the future
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
