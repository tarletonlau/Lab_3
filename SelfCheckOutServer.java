class SelfCheckOutServer extends Server {
    private final QueueManager queueManager;

    SelfCheckOutServer(int serverIndex, double serverAvailableTime,
                       int qMax, QueueManager sharedQueue) {
        super(serverIndex,serverAvailableTime,qMax,sharedQueue);
        this.queueManager = sharedQueue;
    }

    /*
    function of selfcheckout server
    same as a regular server

    only difference:
    - shared queue for all selfcheckout servers

    variables:
    - everything else the same
    - shared queue
    */

    // ====================== METHODS FOR SELF CHECK OUT ===================================

    @Override
    public Server addQueue(Customer customer) {
        return new SelfCheckOutServer(this.serverIndex, this.serverAvailableTime,
                this.qMax, this.queueManager.addQueue(customer));
    }

    @Override
    public Server use(double time) {
        return new SelfCheckOutServer(this.serverIndex, time,
                this.qMax, this.queueManager);
    }

    @Override
    public Server deQueue() {
        return new SelfCheckOutServer(this.serverIndex, this.serverAvailableTime,
                this.qMax, this.queueManager.deQueue());
    }

    public Server rest() {
        return this;
    }

    // ====================================================================================

    @Override
    public String toString() {
        return String.format("self-check %s", super.toString());
    }
}
