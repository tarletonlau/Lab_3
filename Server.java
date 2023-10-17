class Server {
    protected final int serverIndex;
    protected final double serverAvailableTime;
    protected final ImList<Customer> serverQueue;
    protected final int qMax;

    Server(int serverIndex, double serverAvailableTime, int qMax, ImList<Customer> serverQueue) {
        this.serverIndex = serverIndex;
        this.serverAvailableTime = serverAvailableTime;
        this.qMax = qMax;
        this.serverQueue = serverQueue;
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

    // ====================== UPDATE METHODS =================================================

    // add customer to queue
    public Server addQueue(Customer customer) {
        ImList<Customer> updatedQueue = this.serverQueue.add(customer);
        return new Server(this.serverIndex, this.serverAvailableTime, this.qMax, updatedQueue);
    }

    public Server deQueue() {
        ImList<Customer> updatedQueue = this.serverQueue.remove(0);
        return new Server(this.serverIndex, this.serverAvailableTime, this.qMax, updatedQueue);
    }

    //returns index of customer in queue, else return -1 if not there
    public int queueIndex(Customer customer) {
        for (int i = 0; i < serverQueue.size(); i++) {
            if (serverQueue.get(i).equals(customer)) {
                return i;
            }
        }
        return -1;
    }

    // update server time
    // for when server is used / blocked out for queueing
    public Server use(double time) {
        return new Server(this.serverIndex, time, this.qMax, this.serverQueue);
    }

    // =========================== CHECKS =========================================

    // check if server is full
    public boolean isQueueAvail() {
        return this.serverQueue.size() < this.qMax;
    }

    // check if server is avail
    public boolean isServerAvail(Customer customer) {
        return customer.getArrivalTime() >= this.serverAvailableTime;
    }

    // =================================================================================

    public int getServerIndex() {
        return this.serverIndex;
    }

    public double getAvailableTime() {
        return this.serverAvailableTime;
    }

    // ===================================================

    public String toString() {
        return String.format("%.3f", this.serverAvailableTime);
    }
}




