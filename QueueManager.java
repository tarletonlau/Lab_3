import java.util.stream.IntStream;

class QueueManager {
    private final ImList<Customer> queue;

    QueueManager(ImList<Customer> queue) {
        this.queue = queue;
    }

    public QueueManager addQueue(Customer customer) {
        return new QueueManager(this.queue.add(customer));
    }

    public QueueManager deQueue() {
        return (!this.queue.isEmpty()) ? new QueueManager(this.queue.remove(0)) : this;
    }

    // =========================== helpers ===============================

    public int queueSize() {
        return this.queue.size();
    }

    public int customerQueueIndex(Customer customer) {
        return IntStream.range(0, this.queueSize())
                .filter(i -> this.queue.get(i).equals(customer))
                .findFirst()
                .orElse(-1);
    }

}
