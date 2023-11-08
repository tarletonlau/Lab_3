abstract class Server {
    protected final int serverIndex;
    protected final double serverAvailableTime;
    protected final QueueManager queueManager;
    protected final int qMax;

    Server(int serverIndex, double serverAvailableTime, int qMax, QueueManager queueManager) {
        this.serverIndex = serverIndex;
        this.serverAvailableTime = serverAvailableTime;
        this.qMax = qMax;
        this.queueManager = queueManager;
    }

    /*
    function of the server:
    server is where the customer will use it

    variables:
    - hold its index
    - hold the next avail time
    - hold the queue
        - queue method
    - hold max queue size

    methods:
    - return server availability /
    - return queue availability /

    - update next avail time /
    - add customer to queue /
    - remove customer from queue /
    - check if customer is the first in queue /

    */

    // ====================== abstract METHODS =================================================

    // add customer to queue
    public abstract Server addQueue(Customer customer);

    public abstract Server deQueue();

    // update server time
    // for when server is used / blocked out for queueing
    public abstract Server use(double time);

    // =========================== helper methods =========================================

    //returns index of customer in queue, else return -1 if not there
    public int queueIndex(Customer customer) {
        return this.queueManager.customerQueueIndex(customer);
    }

    // check if server is full
    public boolean isQueueAvail() {
        return this.queueManager.queueSize() < this.qMax;
    }

    // check if server is avail
    public boolean isServerAvail(Customer customer) {
        return customer.getArrivalTime() >= this.serverAvailableTime;
    }

    // =========================== getters =========================================

    public int getServerIndex() {
        return this.serverIndex;
    }

    public double getAvailableTime() {
        return this.serverAvailableTime;
    }

    public int getQMax() {
        return this.qMax;
    }

    // ===================================================

    public String toString() {
        return String.format("%d", this.serverIndex + 1);
    }
}




