class SelfCheckOutServer extends Server {
    SelfCheckOutServer(int serverIndex, double serverAvailableTime, int qMax, QueueManager sharedQueue) {
        super(serverIndex,serverAvailableTime,qMax,sharedQueue);
    }

    // ====================== UPDATE METHODS =================================================

    // ====================== QUEUE-related METHODS =================================================
    @Override
    public boolean isQueueAvail() {
        return true;
    }

    // ============================================================================================

    @Override
    public String toString() {
        return String.format("self-check %d", this.serverIndex + 1);
    }
}
