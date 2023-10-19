class QueueManager {
    private final ImList<Customer> queue;

    QueueManager(ImList<Customer> queue) {
        this.queue = queue;
    }

    public QueueManager addQueue(Customer customer) {
        return new QueueManager(this.queue.add(customer));
    }

    public QueueManager deQueue() {
        return new QueueManager(this.queue.remove(0));
    }

    // ==================================================================

    public int queueSize() {
        return this.queue.size();
    }

    public int customerQueueIndex(Customer customer) {
        for (int i = 0; i < this.queueSize(); i++) {
            if (this.queue.get(i).equals(customer)) {
                return i;
            }
        }
        return -1;
    }


}
