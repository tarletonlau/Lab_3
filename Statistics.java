class Statistics {
    private final int numLeft;
    private final int numServed;
    private final double waitingTime;

    public Statistics(int numLeft, int numServed, double waitingTime) {
        this.numLeft = numLeft;
        this.numServed = numServed;
        this.waitingTime = waitingTime;
    }

    public Statistics incrementLeft() {
        return new Statistics(this.numLeft + 1,
                this.numServed, this.waitingTime);
    }

    public Statistics incrementServed() {
        return new Statistics(this.numLeft,
                this.numServed + 1, this.waitingTime);
    }

    public Statistics increaseWaitTime(double time) {
        return new Statistics(this.numLeft,
                this.numServed, this.waitingTime + time);
    }
    
    public int getNumLeft() {
        return this.numLeft;
    }

    public int getNumServed() {
        return this.numServed;
    }

    public double getAverageWaitTime() {
        double averageWaitTime = this.waitingTime / this.numServed;
        if (averageWaitTime > 0) {
            return averageWaitTime;
        } else {
            return 0.0;
        }
    }
}
