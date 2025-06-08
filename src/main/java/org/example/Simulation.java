package org.example;

import java.util.*;

public class Simulation {
    private Kitchen kitchen;
    private List<Client> clients;
    private List<Client> servedClients;
    private List<Client> leftClients;
    private Configuration config;
    private Saver saver;
    private int currentTime;

    public Simulation(Configuration config) {
        this.config = config;
        this.kitchen = new Kitchen(this);
        this.saver = new Saver();
        this.clients = new ArrayList<>();
        this.servedClients = new ArrayList<>();
        this.leftClients = new ArrayList<>();
        this.currentTime = 0;
    }

    private static final List<String> random_clients_names = Arrays.asList(
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
        Set<String> vipNames = Set.of("Pedro Pascal", "Cate Blanchett", "Johnny Depp");

        for (int i = 1; i <= config.getNumberOfCooks(); i++) {
            kitchen.addCook(new Cook(i, "Kucharz_" + i));
        }

        for (int i = 1; i <= config.getNumberOfClients(); i++) {
            Random rand = new Random();
            String randomClientName = random_clients_names.get(rand.nextInt(random_clients_names.size()));
            Client client;

            if (vipNames.contains(randomClientName)) {
                client = new VIPClient(i, randomClientName);
            } else {
                client = new RegularClient(i, randomClientName);
            }

            clients.add(client);

            Dish chosenDish = menu.get(new Random().nextInt(menu.size()));
            client.placeOrder(chosenDish, kitchen);
        }
        System.out.println("===================================");
    }

    public void run() {
        System.out.println("\nSymulacja rozpoczeta, czas trwania: " + config.getSimulationDuration() + " minut");

        while (currentTime < config.getSimulationDuration() && !clients.isEmpty()) {
            currentTime++;
            kitchen.processOrders();
            kitchen.deliverOrders();
            System.out.println("Minuta: " + currentTime);

            Iterator<Client> iterator = clients.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                client.updateStatus();

                if (client.getStatus() == ClientStatus.SERVED && !servedClients.contains(client)) {
                    printClientSummary(client);
                    servedClients.add(client);
                    iterator.remove();
                } else if (client.getStatus() == ClientStatus.LEFT && !leftClients.contains(client)) {
                    printClientSummary(client);
                    leftClients.add(client);
                    iterator.remove();
                }
            }
        }

        // Ustawiamy oceny i czas oczekiwania dla klientów, którzy nadal czekają
        for (Client client : clients) {
            if (client.getStatus() == ClientStatus.WAITING || client.getStatus() == ClientStatus.IMPATIENT) {
                client.setActualWaitTime(currentTime);
                client.setSatisfactionRating(client.satisfactionCalculation()); // ocenianie wg czasu
            }
        }

        System.out.println("Symulacja zakonczona");
        System.out.println("\n-----Podsumowanie-----");
        System.out.println("Czas symulacji: " + currentTime + " minut");
        System.out.println("Pozostali klienci: " + clients.size());
    }


    public void printClientSummary(Client client) {
        System.out.println("\n====Podsumowanie klienta====");
        System.out.println("Klient: " + client.getName());
        System.out.println("Status: " + client.getStatus());
        System.out.println("Ocena satysfakcji: " + client.getSatisfactionRating());
        System.out.println("Czas oczekiwania: " + client.getActualWaitTime() + " minut");
        System.out.println("==========================\n");
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void saveResults() {
        List<Client> allClients = new ArrayList<>();
        allClients.addAll(servedClients);
        allClients.addAll(leftClients);
        allClients.addAll(clients);
        saver.saveSimulationResults(allClients);
    }

}
