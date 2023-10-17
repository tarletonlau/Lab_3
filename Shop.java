import java.util.function.Supplier;

class Shop {
    private final ImList<Server> servers;

    public Shop(int numOfServers, int qMax, Supplier<Double> restTimeSupplier) {
        this.servers = initialize(numOfServers,qMax,restTimeSupplier);
    }
    public Shop(ImList<Server> servers) {
        this.servers = servers;
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

    // ========================== HELPERS  ================================

    private ImList<Server> initialize(int numOfServers, int qMax, Supplier<Double> restTimeSupplier) {
        ImList<Server> tempServers = new ImList<>();

        for (int i = 0; i < numOfServers; i++) {
            tempServers = tempServers.add(new HumanServer(i,0.0, qMax, new ImList<Customer>(),restTimeSupplier));
        }
        return tempServers;
    }

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

    // ==============================================================
    public Shop updateShop(int serverIndex, Server server) {
        return new Shop(this.servers.set(serverIndex, server));
    }

    public Server getServer(int serverIndex) {
        return this.servers.get(serverIndex);
    }

    // ====================================================================

    public String toString() {
        return this.servers.toString();
    }
}