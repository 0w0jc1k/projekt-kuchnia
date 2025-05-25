package org.example;

public class Configuration {
    private int numberOfCooks;
    private int numberOfClients;
    private int simulationDuration;

    public Configuration(int numberOfCooks, int numberOfClients, int simulationDuration) {
        this.numberOfCooks = numberOfCooks;
        this.numberOfClients = numberOfClients;
        this.simulationDuration = simulationDuration;
    }

    public int getNumberOfCooks() {
        return numberOfCooks;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }
}
