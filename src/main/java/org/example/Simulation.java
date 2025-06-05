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
    }

    public void run() {
        //podstawowa implementacja przebiegu symulacji
        System.out.println();
        System.out.println("Simulation started");

        boolean allServedOrLeft = false; //zmienna przechowujaca informacje, czy kazdy klient zostal obsluzony lub czy wyszedl
        while (!allServedOrLeft) {
            kitchen.processOrders();


            for (Client client : new ArrayList<>(clients)) {
                ClientStatus prevStatus = client.getStatus();
                client.updateStatus(); //update jesli zmienil sie z pierwotnie przypisanego
                if (client.getStatus() != prevStatus) {
                    System.out.println("Status klienta " + client.getName() + " zmienil sie na: " + client.getStatus());
                }
                if (client.getStatus() == ClientStatus.LEFT) {
                    printClientSummary(client);
                    clients.remove(client);
                }
            }

            // Dostarczanie gotowych zamówień
            kitchen.deliverOrders();

            allServedOrLeft = clients.isEmpty();
        }
        System.out.println("Simulation completed");
    }

    public void printClientSummary(Client client) {
        System.out.println("====Podsumowanie klienta====");
        System.out.println("Klient: " + client.getName());
        System.out.println("Status: " + client.getStatus());
        System.out.println("Ocena satysfakcji: "+ client.getSatisfactionRating());
        if(client.getActualWaitTime()<=0){
            System.out.println("Czas oczekiwania: (>30) jednostek czasu");
        }else{
            System.out.println("Czas oczekiwania: " + client.getActualWaitTime()+" jednostek czasu");
        }
        System.out.println("==========================");
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void saveResults() {
        //zapisuje do pliku
    }
}