class ServeEvent extends Event {

    private final Server allocatedServer;

    ServeEvent(Customer customer, Server server, double eventTime) {
        super(customer,eventTime);
        this.allocatedServer = server;
    }

    /*
    function of serveevent
    serve the server to the customer

    variable:
    - hold the customer
    - hold the allocated server
    - hold the event time

    process method:
    1. serveevent will call the shop to update the server
        with the customer arrival + service time
    2. return doneevent

     */

    // =================  Event interface ==========================

    @Override
    public Statistics updateStatistics(Statistics stats) {
        // waitTime = ServeTime - customer ArrivalTime
        double waitTime = this.eventTime - this.customer.getArrivalTime();
        stats = stats.increaseWaitTime(waitTime);
        return stats;
    }

    // ============================== Main logic =======================================

    @Override
    public Pair<Shop,Event> process(Shop shop) {

        /* two process to complete
           1. update shop with served server - serverAvailTime = customer.endTime()
           2. return DoneEvent
        */

        double serviceTime = this.customer.getServiceTime();
        double updatedTime = this.eventTime + serviceTime;

        shop = shop.removeCustomerFromServerQueue(this.allocatedServer);
        shop = shop.useServer(this.allocatedServer,updatedTime);

        // 2. return DoneEvent
        Server updatedServer = shop.getServer(this.allocatedServer.getServerIndex());
        Event nextEvent = new DoneEvent(this.customer, updatedServer, updatedTime);

        //return updated shop with DoneEvent
        return new Pair<Shop, Event>(shop,nextEvent);
    }

    // =======================================================

    @Override
    public String toString() {
        return String.format("%s serves by %s",
                super.toString(),
                this.allocatedServer.toString());
    }
}

