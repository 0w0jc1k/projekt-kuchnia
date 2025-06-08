package org.example;
import java.util.*;

/**
 * Glowne miejsce do zarzadzania symulacja i jej mozliwymi scenariuszami.
 * Koordynuje komponentami i kontroluje progres symulacji.
 */
public class Simulation {
    /**
     * Kuchnia, w ktorej odbywa sie symulacja
     */
    private Kitchen kitchen;
    /**
     * Lista przechowujaca aktywnych klientow symulacji
     */
    private List<Client> clients;
    /**
     * Lista przechowujaca obsluzonych klientow symulacji
     */
    private List<Client> servedClients;
    /**
     * Lista obslugujaca klientow, ktorzy opuscili kuchnie
     */
    private List<Client> leftClients;
    /**
     * Konfiguracje symulacji
     */
    private Configuration config;
    /**
     * Komponent zapisujacy rezultaty symulacji
     */
    private Saver saver;
    /**
     * Obecny czas trwania symulacji w minutach
     */
    private int currentTime;

    /**
     * Konstruktor do tworzenia nowej instancji new Simulation
     * @param config konfiguracje symulacji
     */
    public Simulation(Configuration config) {
        this.config = config;
        this.kitchen = new Kitchen(this);
        this.saver = new Saver();
        this.clients = new ArrayList<>();
        this.servedClients = new ArrayList<>();
        this.leftClients = new ArrayList<>();
        this.currentTime = 0;
    }

    /**
     * Ustalona wczesniej lista mozliwych imion klientow
     */
    private static final List<String> random_clients_names = Arrays.asList(
            "Pedro Pascal", "Chris Evans", "Johnny Depp", "Robert Downey Jr.", "Timothee Chalamet",
            "Cate Blanchett", "Jennifer Lawrence", "Dakota Johnson", "Bradley Cooper", "Ryan Gosling"
    );
    /**
     * Ustalona wczesniej lista nazw poszczegolnych dan i czasu ich przygotowania
     */
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

    /**
     * Inicjalizuje przebieg symulacji.
     *Tworzy kucharzy oraz klientow w skonfigurowany sposob.
     */
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

    /**
     * Uruchamia glowna petle symulacji.
     * Przetwarza zamowienia i aktualizuje stany do momentu zakonczenia symulacji.
     */
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
            }
        }

        System.out.println("Symulacja zakonczona");
        System.out.println("\n-----Podsumowanie-----");
        System.out.println("Czas symulacji: " + currentTime + " minut");
        System.out.println("Pozostali klienci: " + clients.size());
    }

    /**
     * Zwraca podsumowanie doswiadczenia klienta
     * @param client klient, ktorego podsumowanie chcemy otrzymac
     */
    public void printClientSummary(Client client) {
        System.out.println("\n====Podsumowanie klienta====");
        System.out.println("Klient: " + client.getName());
        System.out.println("Status: " + client.getStatus());
        System.out.println("Ocena satysfakcji: " + client.getSatisfactionRating());
        System.out.println("Czas oczekiwania: " + client.getActualWaitTime() + " minut");
        System.out.println("==========================\n");
    }

    /**
     * Zapisuje koncowe wyniki symulacji do odpowiedniego pliku
     */
    public void saveResults() {
        List<Client> allClients = new ArrayList<>();
        allClients.addAll(servedClients);
        allClients.addAll(leftClients);
        allClients.addAll(clients);
        saver.saveSimulationResults(allClients);
    }

}
