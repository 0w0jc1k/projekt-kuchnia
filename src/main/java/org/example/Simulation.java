package org.example;

import java.util.*;

public class Simulation {
    private Kitchen kitchen;
    private List<Client> clients;
    private Configuration config;
    private Saver saver;
    private int currentTime;

    public Simulation(Configuration config) {
        this.config = config;
        this.kitchen = new Kitchen();
        this.saver = new Saver();
        this.clients = new ArrayList<>();
    }

    public void initialize(){
        //inicjalizacja kucharzy
        for(int i=0; i<config.getNumberOfCooks();i++){
            kitchen.addCook(new Cook(i+1,"Cook"+i+1));
        }
        //inicjalizacja klientów
        for(int i=0; i<config.getNumberOfClients();i++){
            clients.add(new Client(i+1,"Client"+i+1));
        }
    }

    public void run(){
        //podstawowa implementacja przebiegu symulacji
        System.out.println("Simulation started");

        //tutaj bedzie kod z petla glowna symulacji

        System.out.println("Simulation completed");
    }

    public void saveResults(){
        saver.saveSimulationResults(clients, kitchen.getOrders());
    }
}
