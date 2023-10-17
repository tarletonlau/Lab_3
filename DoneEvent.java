class DoneEvent implements ServerAssociatedEvent {
    private final double eventTime;
    private final Customer customer;
    private final Server allocatedServer;

    DoneEvent(Customer customer, Server server, double eventTime) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.allocatedServer = server;
    }

    // ==========  Event interface ==================
    
    public double eventTime() {
        return this.eventTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Statistics updateStatistics(Statistics stats) {
        return stats.incrementServed();
    }

    // ============================== Main logic =======================================

    public Pair<Shop, Event> process(Shop shop) {
        // cop out for finding out what is a done/leave event
        // probably replace in the future
        return new Pair<Shop,Event>(shop, this);
    }

    // ==================================================

    public String toString() {
        return String.format("%.3f %s done serving by %s",
        this.eventTime, this.customer.toString(), this.allocatedServer.toString());
    }
}
