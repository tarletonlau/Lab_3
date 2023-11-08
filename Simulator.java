import java.util.function.Supplier;

class Simulator {
    private final Integer numOfServers;
    private final Integer numOfSelfChecks;
    private final Integer qMax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimeSupplier;
    private final Supplier<Double> restTimeSupplier;

    Simulator(Integer numOfServers, Integer numOfSelfChecks, Integer qMax,
              ImList<Double> arrivalTimes, Supplier<Double> serviceTimeSupplier,
              Supplier<Double> restTimesSupplier) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qMax = qMax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.restTimeSupplier = restTimesSupplier;
    }

    public String simulate() {
        Statistics stats = new Statistics(0,0,0.0);

        StringBuilder output = new StringBuilder();

        // =================== Constructors =======================================

        // Creating the shop
        Shop shop = new Shop(this.numOfServers, this.numOfSelfChecks,
                this.qMax, this.restTimeSupplier);

        // Creating PQ for events
        PQ<Event> eventQueue = new PQ<Event>(new EventComp());

        // =======================================================================

        // running through the arrival times and adding each customer as an arrive event
        for (int i = 0; i < this.arrivalTimes.size(); i++) {

            // creating the customer - (arrivalTime, serviceTimeSupplier, id)
            Customer currCustomer = new Customer(this.arrivalTimes.get(i), 
                    this.serviceTimeSupplier, i + 1);

            // adding the customer as an arrive event into the eventQueue
            eventQueue = eventQueue.add(new ArriveEvent(currCustomer, this.arrivalTimes.get(i)));
        }

        // ====================== Algorithm ====================================

        /* curr event will be processed
        if it is arrive (three outcomes - serve, wait or leave)
        if it is wait (find non-empty queue, two outcomes, wait or leave)
        if it is serve (one outcome - done)
        if it is done or leave (remove from queue)

        processing of logic is done within event classes
        */

        while (!eventQueue.isEmpty()) {
            Event currEvent = eventQueue.poll().first();
            eventQueue = eventQueue.poll().second();

            //adding currEvent to output print
            // "" is for repeating WaitEvents
            if (!currEvent.toString().isEmpty()) {
                output.append(currEvent).append("\n");
            }

            //update stats
            stats = currEvent.updateStatistics(stats);
            //process the currEvent
            Pair<Shop, Event> processed = currEvent.process(shop);        

            //shop is updated
            shop = processed.first();
            Event nextEvent = processed.second();

            //if nextEvent is not Leave or Done
            if (currEvent != nextEvent) {
                eventQueue = eventQueue.add(nextEvent);
            }
        }

        int numServed = stats.getNumServed();
        int numLeft = stats.getNumLeft();
        double averageWaitTime = stats.getAverageWaitTime();

        output.append(String.format("[%.3f %d %d]", averageWaitTime, numServed, numLeft));
        return output.toString();
    }

}
