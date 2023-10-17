class SelfCheckOutServer extends Server {
    private final ImList<Customer> sharedQueue;
    SelfCheckOutServer(int serverIndex, double serverAvailableTime, int qMax, ImList<Customer> sharedQueue) {
        super(serverIndex,serverAvailableTime,qMax, new ImList<Customer>());
        this.sharedQueue = sharedQueue;
    }

    // ====================== UPDATE METHODS =================================================

    // add customer to queue
    @Override
    public Server addQueue(Customer customer) {
        //update the sharedQueue
        ImList<Customer> updatedQueue = this.sharedQueue.add(customer);
        return new SelfCheckOutServer(this.serverIndex, this.serverAvailableTime, this.qMax, updatedQueue);
    }

    @Override
    public Server deQueue() {
        //update the sharedQueue
        ImList<Customer> updatedQueue = this.sharedQueue.remove(0);
        return new SelfCheckOutServer(this.serverIndex, this.serverAvailableTime, this.qMax, updatedQueue);
    }

    @Override
    public Server use(double time) {
        return new SelfCheckOutServer(this.serverIndex, time, this.qMax, this.sharedQueue);
    }

    // ====================== QUEUE-related METHODS =================================================

    @Override
    public boolean isQueueAvail() {
        return this.sharedQueue.size() < this.qMax;
    }

    @Override
    public int queueIndex(Customer customer) {
        for (int i = 0; i < this.sharedQueue.size(); i++) {
            if (this.sharedQueue.get(i).equals(customer)) {
                return i;
            }
        }
        return -1;
    }

    // ============================================================================================

    @Override
    public String toString() {
        return String.format("self-check %d", this.serverIndex + 1);
    }
}
