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

    public void initialize() {
        // Dodajemy kucharza
        kitchen.addCook(new Cook(1, "Maciej Musiał"));

        // Dodajemy klienta
        Client client = new Client(1, "Leonardo Di Caprio");
        clients.add(client);

        // Klient składa zamówienie wstepnie wybrane, finalnie bedzie losowal samodzielnie
        client.placeOrder(new Dish("Sushi", 15), kitchen);
    }

    public void run() {
        //podstawowa implementacja przebiegu symulacji
        System.out.println("Simulation started");

        for (currentTime = 0; currentTime < config.getSimulationDuration(); currentTime++) {
            // Kuchnia przetwarza zamówienia
            kitchen.processOrders();

            // Aktualizacja statusu klienta
            for (Client client : clients) {
                client.updateStatus();

                // Sprawdzenie czy symulacja powinna się zakończyć
                if (client.getStatus() == ClientStatus.SERVED || client.getStatus() == ClientStatus.LEFT) {
                    printSummary();
                    return;
                }
            }
            // Dostarczanie gotowych zamówień
            kitchen.deliverOrders();
        }
        printSummary();
    }

    private void printSummary() {
        System.out.println("\n=== Podsumowanie ===");
        Client client = clients.get(0);
        System.out.println("Status klienta: " + client.getStatus());
        System.out.println("Ocena satysfakcji: " + client.getSatisfactionRating());
        if(client.getActualWaitTime()<=0){
            System.out.println("Czas oczekiwania: wstyd sie przyznac... (>30)");
        }else {
            System.out.println("Czas oczekiwania: " + client.getActualWaitTime() + " jednostek czasu");
        }
        System.out.println("Simulation completed");
    }


    public void saveResults() {
        //zapisuje do pliku
    }

}