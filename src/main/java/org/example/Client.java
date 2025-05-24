package org.example;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private int waitTime;
    private List<Client> clients = new ArrayList<>();

    public Client(int id, String name) {
        super(id, name);
        this.waitTime = 30;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void placeOrder(Order order) {
        // klient składa zamówienie
    }

    public void receiveDish() {
        // klient otrzymuje danie
    }
}
