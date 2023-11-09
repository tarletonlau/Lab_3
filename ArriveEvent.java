class ArriveEvent extends Event {
    ArriveEvent(Customer customer, double eventTime) {
        super(customer,eventTime);
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

    // ==================  abstract methods - Event  ==========================

    @Override
    public Statistics updateStatistics(Statistics stats) {
        //empty method, can update for future features
        return stats;
    }

    // ======================== Main logic ============================

    @Override
    public Pair<Shop, Event> process(Shop shop) {
        Event nextEvent = new LeaveEvent(this.customer,this.eventTime);

        int availableServerIndex = shop.findAvailableServerIndex(this.customer);

        if (availableServerIndex >= 0) {
            //return ServeEvent
            Server server = shop.getServer(availableServerIndex);
            nextEvent = new ServeEvent(this.customer, server, this.eventTime);
        
        } else {
            //logic to process whether wait or leave
            int availableQueueIndex = shop.findAvailableQueueIndex();

            if (availableQueueIndex >= 0) {
                //update shop by adding customer
                Server server = shop.getServer(availableQueueIndex);
                shop = shop.addCustomerToServerQueue(this.customer,server);

                // return WaitEvent
                Server updatedServer = shop.getServer(availableQueueIndex);
                nextEvent = new WaitEvent(this.customer, updatedServer, this.eventTime, true);
            }
        }
        //return LeaveEvent by default
        // Serve and Wait will allocated by the logic
        return new Pair<Shop,Event>(shop,nextEvent);
    }

    // ==============================================

    @Override
    public String toString() {
        return String.format("%s arrives", super.toString());
    }
}
