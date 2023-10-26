abstract class Event {

    protected final double eventTime;
    protected final Customer customer;

    Event(Customer customer, double eventTime) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    public abstract Pair<Shop,Event> process(Shop shop);

    public abstract Statistics updateStatistics(Statistics stats);

    public int compareTo(Event other) {
        if (this.eventTime < other.eventTime) {
            return -1;
        } else if (this.eventTime > other.eventTime) {
            return 1;
        } else {
            return Double.compare(this.customer.getArrivalTime(), other.customer.getArrivalTime());
        }
    }

    public String toString() {
        return String.format("%.3f %s", this.eventTime,this.customer);
    }
}

