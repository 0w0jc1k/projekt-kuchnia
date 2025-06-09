package org.example;

/**
 * Zawiera wszystkie mozliwe do konfikuracji parametry zawarte w symulacji.
 * Inicjalizuje zawartosc symualcji.
 */
public class Configuration {
    /**
     * Liczba kucharzy w symulacji
     */
    private int numberOfCooks;
    /**
     * Liczba klientow w symulacji
     */
    private int numberOfClients;
    /**
     * Czas trwania symulacji w minutach
     */
    private int simulationDuration;

    /**
     * Konstruktor do tworzenia nowej instancji new Configuration
     * @param numberOfCooks liczba kucharzy zawartych w symulacji
     * @param numberOfClients liczba klientow zawartych w symulacji
     * @param simulationDuration czas trwania symulacji w minutach
     */
    public Configuration(int numberOfCooks, int numberOfClients, int simulationDuration) {
        this.numberOfCooks = numberOfCooks;
        this.numberOfClients = numberOfClients;
        this.simulationDuration = simulationDuration;
    }

    /**
     * Zwraca liczbe kucharzy w symulacji
     * @return liczba kucharzy w symulacji
     */
    public int getNumberOfCooks() {
        return numberOfCooks;
    }

    /**
     * Zwraca liczbe klientow w symulacji
     * @return liczba klientow w symulacji
     */
    public int getNumberOfClients() {
        return numberOfClients;
    }

    /**
     * Zwraca czas trwania symulacji w minutach
     * @return czas trwania symulacji w minutach
     */
    public int getSimulationDuration() {
        return simulationDuration;
    }
}
