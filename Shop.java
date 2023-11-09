import java.util.function.Supplier;

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

    // constructor for servers
    public static Shop initialize(int numOfServers, int numOfSelfChecks, int qMax,
                                  Supplier<Double> restTimeSupplier, QueueManager sharedQueue) {

        ImList<Server> tempServers = new ImList<Server>();

        //create servers
        for (int i = 0; i < numOfServers; i++) {
            tempServers = tempServers.add(
                    new HumanServer(i, 0.0, qMax, new QueueManager(new ImList<Customer>()),
                            restTimeSupplier));
        }

        //create selfcheckouts
        for (int j = numOfServers; j < (numOfServers + numOfSelfChecks); j++) {
            tempServers = tempServers.add(
                    new SelfCheckOutServer(j, 0.0, qMax, sharedQueue));
        }

        return new Shop(tempServers, sharedQueue, numOfServers,numOfSelfChecks);
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

    // ====================== shop methods ================
    /*
    methods that allow shop to add and remove customers from the queue
    */
    public Shop addCustomerToServerQueue(Customer customer, Server serverToCheck) {
        int serverIndex = serverToCheck.getServerIndex();
        Server server = this.servers.get(serverIndex);

        if (isSelfCheckOut(server)) {
            // self-checkout server, update the shared queue
            QueueManager updatedQueue = this.sharedQueue.addQueue(customer);

            // update all self-checkout servers with the new shared queue
            ImList<Server> updatedServers = new ImList<>();
            for (Server s : this.servers) {
                updatedServers = updatedServers.add(isSelfCheckOut(s) ?
                        new SelfCheckOutServer(s.getServerIndex(), s.getAvailableTime(),
                                s.getQMax(), updatedQueue)
                        : s
                );
            }
            return new Shop(updatedServers, updatedQueue, this.numOfServers, this.numOfSelfChecks);
        } else {
            // human server, add the customer to that server's queue
            return updateShop(server.getServerIndex(), server.addQueue(customer));
        }
    }

    public Shop removeCustomerFromServerQueue(Server serverToCheck) {
        int serverIndex = serverToCheck.getServerIndex();
        Server server = this.servers.get(serverIndex);

        if (isSelfCheckOut(server)) {
            QueueManager updatedQueue = this.sharedQueue.deQueue();

            // update all self-checkout servers with the new shared queue
            ImList<Server> updatedServers = new ImList<>();
            for (Server s : this.servers) {
                updatedServers = updatedServers.add(isSelfCheckOut(s) ?
                        new SelfCheckOutServer(s.getServerIndex(), s.getAvailableTime(),
                                s.getQMax(), updatedQueue)
                        : s
                );
            }
            return new Shop(updatedServers, updatedQueue, this.numOfServers, this.numOfSelfChecks);

        } else {
            // human server, remove the customer from that server's queue
            return updateShop(server.getServerIndex(), server.deQueue());
        }
    }

    public Shop useServer(Server server, double updatedTime) {
        int serverIndex = server.getServerIndex();
        Server updatedServer = this.servers.get(serverIndex).use(updatedTime);
        return updateShop(serverIndex,updatedServer);
    }

    public Shop serverRest(Server server) {
        int serverIndex = server.getServerIndex();
        Server updatedServer = this.servers.get(serverIndex).rest();
        return updateShop(serverIndex,updatedServer);
    }


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

    // ==============================================================
    private Shop updateShop(int serverIndex, Server server) {
        return new Shop(this.servers.set(serverIndex, server),
                this.sharedQueue, this.numOfServers, this.numOfSelfChecks);
    }

    public Server getServer(int serverIndex) {
        return this.servers.get(serverIndex);
    }

    // ====================================================================

    public String toString() {
        return this.servers.toString();
    }
}
