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
        this.kitchen = new Kitchen(this);
        this.saver = new Saver();
        this.clients = new ArrayList<>();
        this.currentTime = 0;
    }

    private static final List<String> random_clients_names = Arrays.asList( //lista z ktorej w randomowy sposob losowane beda dane klientow
            "Pedro Pascal", "Chris Evans", "Johnny Depp", "Robert Downey Jr.", "Timothee Chalamet",
            "Cate Blanchett", "Jennifer Lawrence", "Dakota Johnson", "Bradley Cooper", "Ryan Gosling"
    );

    private final static List<Dish> menu = Arrays.asList(
            new Dish("Sushi", 15),
            new Dish("Karpatka", 25),
            new Dish("Pizza", 20),
            new Dish("Burger", 17),
            new Dish("Lasagne", 14),
            new Dish("Zupa pomidorowa", 8),
            new Dish("Pierogi", 10),
            new Dish("Bruschetta", 4),
            new Dish("Sałatka Cezar", 12),
            new Dish("Makaron Carbonara", 35)
    );

    public void initialize() {
        for (int i = 1; i <= config.getNumberOfCooks(); i++) {
            kitchen.addCook(new Cook(i, "Kucharz_" + i));
        }

        for (int i = 1; i <= config.getNumberOfClients(); i++) {
            Random rand = new Random();
            String randomClientName = random_clients_names.get(rand.nextInt(random_clients_names.size()));
            Client client = new Client(i, randomClientName);
            clients.add(client);

            Dish chosenDish = menu.get(new Random().nextInt(menu.size()));
            client.placeOrder(chosenDish, kitchen);
        }
        System.out.println("===================================");
    }

    public void run() {
        //podstawowa implementacja przebiegu symulacji
        System.out.println();
        System.out.println("Simulation started");

        List<Client> completedClients = new ArrayList<>();

        while (currentTime < config.getSimulationDuration() && (!clients.isEmpty() || !kitchen.getOrders().isEmpty())) {
            currentTime++;
            System.out.println("Czas symulacji: " + currentTime);

            //zwiekszamy czas przygotowania jeszcze niegotowych dan
            for (Order order : kitchen.getOrders()) {
                if (order.getStatus() != OrderStatus.READY && order.getStatus() != OrderStatus.CANCELLED) {
                    order.incrementActualOrderTime();
                }
            }

            kitchen.processOrders();

            List<Client> clientsToProcess = new ArrayList<>(clients);
            for (Client client : clientsToProcess) {
                client.updateStatus();
                if (client.getStatus() != client.getStatus()) {
                    System.out.println("Status klienta " + client.getName() + " zmienił się na: " + client.getStatus());
                }
                if (client.getStatus() == ClientStatus.LEFT || client.getStatus() == ClientStatus.SERVED) {
                    completedClients.add(client);
                }
            }
            // Dostarczanie gotowych zamówień
            kitchen.deliverOrders();
            clients.removeAll(completedClients);
            completedClients.clear();//czysci do kolejnej iteracji

            //sprawdzamy czy kazdy klient zostal obsluzony
            if (clients.isEmpty() && kitchen.getOrders().isEmpty()) {
                System.out.println("Wszyscy klienci zostali obsluzeni lub opuscili restauracje!");
                break;
            }
        }
        System.out.println("Simulation completed");
        if (!clients.isEmpty()) {
            System.out.println("\n--- Podsumowanie klientów pozostałych w restauracji ---");
            for (Client client : clients) {
                if (client.getStatus() != ClientStatus.SERVED && client.getStatus() != ClientStatus.LEFT) {
                    // For clients still waiting when simulation ends
                    printClientSummary(client);
                }
            }
        }
    }

    public void printClientSummary(Client client) {
        System.out.println("====Podsumowanie klienta====");
        System.out.println("Klient: " + client.getName());
        System.out.println("Status: " + client.getStatus());
        System.out.println("Ocena satysfakcji: "+ client.getSatisfactionRating());
        System.out.println("Czas oczekiwania: " + client.getActualWaitTime()+" jednostek czasu");
        System.out.println("==========================");
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void saveResults() {
        //zapisuje do pliku
    }
}