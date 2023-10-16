class LeaveEvent implements Event {
    private final double eventTime;
    private final Customer customer;

    LeaveEvent(Customer customer, double eventTime) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    // ================= Event Interface =================

    public double eventTime() {
        return this.eventTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Statistics updateStatistics(Statistics stats) {
        return stats.incrementLeft();
    }

    // ============================== Main logic =======================================

    public Pair<Shop, Event> process(Shop shop) {
        // cop out for finding out what is a done/leave event
        // probably replace in the future
        return new Pair<Shop, Event>(shop, this);
    }

    // =================================================

    public String toString() {
        return String.format("%.3f %s leaves", this.eventTime, this.customer.toString());
    }
}
