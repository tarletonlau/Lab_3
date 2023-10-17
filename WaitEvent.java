class WaitEvent implements ServerAssociatedEvent {
    private final double eventTime;
    private final Customer customer;
    private final Server allocatedServer;
    private final boolean firstTimeInQueue;

    WaitEvent(Customer customer, Server server, double eventTime, boolean firstTimeInQueue) {
        this.eventTime = eventTime;
        this.customer = customer;
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

    public double eventTime() {
        return this.eventTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Statistics updateStatistics(Statistics stats) {
        return stats;
    }

    // ======================== Main logic ==========================

    public Pair<Shop,Event> process(Shop shop) {
        Event nextEvent;
        int serverIndex = this.allocatedServer.getServerIndex();
        Server server = shop.getServer(serverIndex);

        int queueIndex = server.queueIndex(this.customer);

        //check if customer is at front of queue, if yes, can serve them
        if (queueIndex == 0) {
            double nextEventTime = server.getAvailableTime();

            //return a ServeEvent at the serversNextAvail time
            nextEvent = new ServeEvent(this.customer, server, nextEventTime);

        //if not front of queue, keep queueing
        } else {
            //move their time to the next time server is available to check again
            double nextAvailTime = server.getAvailableTime();

            nextEvent = new WaitEvent(this.customer, server, nextAvailTime, false);
        }

        return new Pair<Shop,Event>(shop,nextEvent);
    }

    // =======================================================================

    public String toString() {
        if (firstTimeInQueue) {
            return String.format("%.3f %s waits at %s",
                    this.eventTime, this.customer.toString(), 
                    this.allocatedServer.toString());
        }
        return "";
    }
}
