class LeaveEvent extends Event {


    LeaveEvent(Customer customer, double eventTime) {
        super(customer,eventTime);
    }

    // ================= Event Interface =================

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats.incrementLeft();
    }

    // ============================== Main logic =======================================

    @Override
    public Pair<Shop, Event> process(Shop shop) {
        return new Pair<Shop, Event>(shop, this);
    }

    // =================================================

    @Override
    public String toString() {
        return String.format("%s leaves", super.toString());
    }
}
