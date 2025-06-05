package org.example;

public class Main {
    public static void main(String[] args) {

        Configuration config = new Configuration(1,8,1);

        Simulation simulation = new Simulation(config);
        simulation.initialize(); //inicjalizuje liczbe klientow i kucharzy
        simulation.run(); //uruchamia symulacje
        simulation.saveResults(); //zapisuje wyniki symulacji
    }
}