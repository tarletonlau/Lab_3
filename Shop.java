import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class Shop {
    private final ImList<Server> servers;
    private final QueueManager sharedQueue;
    private final int numOfServers;
    private final int numOfSelfChecks;

    Shop(ImList<Server> servers, QueueManager sharedQueue, int numOfServers, int numOfSelfChecks) {
        this.servers = servers;
        this.sharedQueue = sharedQueue;
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;

    }

    Shop(int numOfServers, int numOfSelfChecks, int qMax,
         Supplier<Double> restTimeSupplier) {
        this.servers = initialize(numOfServers, numOfSelfChecks, qMax, restTimeSupplier);
        this.sharedQueue = new QueueManager(new ImList<Customer>());

        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
    }

    // constructor for servers
    private ImList<Server> initialize(int numOfServers, int numOfSelfChecks,
                                      int qMax, Supplier<Double> restTimeSupplier) {
        ImList<Server> tempServers = new ImList<Server>();

        //create servers
        for (int i = 0; i < numOfServers; i++) {
            tempServers = tempServers.add(new HumanServer(i, 0.0, qMax,
                    new QueueManager(new ImList<Customer>()), restTimeSupplier));
        }

        //create selfcheckouts
        for (int j = numOfServers; j < (numOfServers + numOfSelfChecks); j++) {
            tempServers = tempServers.add(new SelfCheckOutServer(j, 0.0, qMax,
                    this.sharedQueue));
        }

        return tempServers;
    }

    /* function of the shop:
    shop is the one who will allocate each customer to a server

    variables:
    - list of servers

    methods:
    - handle the creation of the servers
    - finding for available servers
    - finding for available queues

    - allocating the customer to a server
    - allocating the customer to a queue

    */

    // ====================== shop methods =====================================

    /*
    methods that allow shop to add and remove customers from the queue
    */
    public Shop addCustomerToServerQueue(Customer customer, Server server) {
        if (isSelfCheckOut(server)) {
            // self-checkout server, update the shared queue
            QueueManager updatedQueue = this.sharedQueue.addQueue(customer);

            // update all self-checkout servers with shared queue
            ImList<Server> updatedServers = new ImList<>(
                    StreamSupport.stream(this.servers.spliterator(), false)
                            .map(s -> {
                                if (isSelfCheckOut(s)) {
                                    return new SelfCheckOutServer(
                                            s.getServerIndex(),
                                            s.getAvailableTime(),
                                            s.getQMax(),
                                            updatedQueue);
                                } else {
                                    return s;
                                }
                            })
                            .collect(Collectors.toList())
            );
            return new Shop(updatedServers, updatedQueue, this.numOfServers, this.numOfSelfChecks);

        } else {
            // human server, add the customer to that server's queue
            int serverIndex = server.getServerIndex();
            Server updatedServer = server.addQueue(customer);

            return updateShop(serverIndex, updatedServer);
        }
    }

    public Shop removeCustomerFromServerQueue(Server server) {
        if (isSelfCheckOut(server)) {
            QueueManager updatedQueue = this.sharedQueue.deQueue();

            ImList<Server> updatedServers = new ImList<>(
                    StreamSupport.stream(servers.spliterator(), false)
                            .map(s -> {
                                if (isSelfCheckOut(s)) {
                                    return new SelfCheckOutServer(
                                            s.getServerIndex(),
                                            s.getAvailableTime(),
                                            s.getQMax(),
                                            updatedQueue);
                                } else {
                                    return s;
                                }
                            })
                            .collect(Collectors.toList()) // Collect to a List first
            );
            return new Shop(updatedServers, updatedQueue, this.numOfServers, this.numOfSelfChecks);
        } else {
            int serverIndex = server.getServerIndex();
            Server updatedServer = server.deQueue();

            return updateShop(serverIndex, updatedServer);
        }
    }

    public Shop updateShop(int serverIndex, Server server) {
        return new Shop(this.servers.set(serverIndex, server), this.sharedQueue, this.numOfServers, this.numOfSelfChecks);
    }

//    public Shop serveCustomer(Customer customer) {
//        int serverIndex = findAvailableQueueIndex();
//        return (serverIndex >= 1) ?
//                this.servers.get(serverIndex).use(
//    }

    // ========================== HELPERS  ================================

    // find first available server, else return -1
    public int findAvailableServerIndex(Customer customer) {
        for (Server currServer : this.servers) {
            if (currServer.isServerAvail(customer)) {
                return currServer.getServerIndex();
            }
        }
        return -1;
    }

    // find first available queue, else return -1
    public int findAvailableQueueIndex() {
        for (Server currServer : this.servers) {
            if (currServer.isQueueAvail()) {
                return currServer.getServerIndex();
            }
        }
        return -1;
    }

    private boolean isSelfCheckOut(Server server) {
        return (server.getServerIndex() >= this.numOfServers);
    }

    // ========================== getters  ================================

    public Server getServer(int serverIndex) {
        return this.servers.get(serverIndex);
    }


    // ====================================================================

    public String toString() {
        return this.servers.toString();
    }
}
