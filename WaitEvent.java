class WaitEvent extends Event {

    private final Server allocatedServer;
    private final boolean firstTimeInQueue;

    WaitEvent(Customer customer, Server server, double eventTime, boolean firstTimeInQueue) {
        super(customer,eventTime);
        this.allocatedServer = server;
        this.firstTimeInQueue = firstTimeInQueue;
    }

    /*
       function of WaitEvent
       waitevent is tied to customer
       waitevent is tied to a server
       waitevent has an event time of customer arrival time

       variables:
       - hold the event time
       - hold the customer
       - hold the allocated server

       process method
       - arriveevent creates a new waitevent

       - waitevent will be at the front of the queue when created
       1. waitevent will create a serveevent with event time of server next avail time
       - update the server with the current server avail time + customer service time

       2. update counter for wait time
     */

    // ========================  Event interface ==========================

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats;
    }

    // ======================== Main logic ==========================

    @Override
    public Pair<Shop,Event> process(Shop shop) {
        Server updatedServer = shop.getServer(this.allocatedServer.getServerIndex());
        double serverAvailableTime = updatedServer.getAvailableTime();

        Event nextEvent = ((updatedServer.queueIndex(this.customer) == 0)
                && (serverAvailableTime <= this.eventTime))
                ? new ServeEvent(this.customer, updatedServer, serverAvailableTime)
                : new WaitEvent(this.customer, updatedServer, serverAvailableTime,
                false);

        return new Pair<Shop,Event>(shop,nextEvent);
    }

    // =======================================================================

    @Override
    public String toString() {
        if (firstTimeInQueue) {
            return String.format("%s waits at %s",
                    super.toString(),
                    this.allocatedServer.toString());
        }
        return "";
    }
}
