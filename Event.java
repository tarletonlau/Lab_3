interface Event {

    //needed for EventComp
    public double eventTime();

    public Pair<Shop,Event> process(Shop shop);

    //needed for EventComp
    public Customer getCustomer();
    
    public Statistics updateStatistics(Statistics stats);
}

