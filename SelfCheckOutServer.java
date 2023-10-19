class SelfCheckOutServer extends Server {
    SelfCheckOutServer(int serverIndex, double serverAvailableTime,
                       int qMax, QueueManager sharedQueue) {
        super(serverIndex,serverAvailableTime,qMax,sharedQueue);
    }

    // ====================== UPDATE METHODS =================================================

    @Override
    public Server addQueue(Customer customer) {
        return new SelfCheckOutServer(this.serverIndex, this.serverAvailableTime,
                this.qMax, this.queueManager.addQueue(customer));
    }

    @Override
    public Server deQueue() {
        return new SelfCheckOutServer(this.serverIndex, this.serverAvailableTime,
                this.qMax, this.queueManager.deQueue());
    }

    @Override
    public Server use(double time) {
        return new SelfCheckOutServer(this.serverIndex, time,
                this.qMax, this.queueManager);
    }

    // ====================== QUEUE-related METHODS =======================================
    @Override
    public boolean isQueueAvail() {
        return true;
    }


    // =======================================================================================

    @Override
    public String toString() {
        return String.format("self-check %d", this.serverIndex + 1);
    }
}
