class ArriveEvent implements Event {
    private final double eventTime;
    private final Customer customer;

    ArriveEvent(Customer customer, double eventTime) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    /*
    function of ArriveEvent:
    Event is tied to a customer
    EventTime is used to sort the priority queue

    - hold the event time
    - hold the customer

    process method
    - arriveevent when process will
        1. check whether there is an avail server
            - call the shop to check for avail server

        2. return serveevent if there is one
        3. check if got avail queue
            - call the shop to check for avail queue
        4. return waitevent if there is one
        5. else return leaveevent

     */

    // ==================  Event interface ==========================

    public double eventTime() {
        return this.eventTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Statistics updateStatistics(Statistics stats) {
        //empty method, can update for future features
        return stats;
    }

    // ======================== Main logic ============================

    public Pair<Shop, Event> process(Shop shop) {
        Event nextEvent = new LeaveEvent(this.customer,this.eventTime);

        int availableServerIndex = shop.findAvailableServerIndex(this.customer);

        if (availableServerIndex >= 0) {
            Server server = shop.getServer(availableServerIndex);

            //update server and shop
            Server updatedServer = server.updateAvailTime(this.customer.getArrivalTime());
            shop = shop.updateShop(availableServerIndex,updatedServer);

            // return ServeEvent
            nextEvent = new ServeEvent(this.customer,updatedServer, this.eventTime);
        
        } else {
    
            //logic to process whether wait or leave
            int availableQueueIndex = shop.findAvailableQueueIndex();

            if (availableQueueIndex >= 0) {
                Server server = shop.getServer(availableQueueIndex);

                //update server and shop
                Server updatedServer = server.addQueue(this.customer);
                shop = shop.updateShop(availableQueueIndex,updatedServer);

                // return WaitEvent
                nextEvent = new WaitEvent(this.customer, updatedServer, this.eventTime, true);
            }
        }
        //return LeaveEvent by default
        // Serve and Wait will allocated by the logic
        return new Pair<Shop,Event>(shop,nextEvent);
    }

    // ==============================================

    public String toString() {
        return String.format("%.3f %s arrives", this.eventTime, this.customer.toString());
    }
}
