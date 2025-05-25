package org.example;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private int waitTime;
    private List<Client> clients = new ArrayList<>();
    private int satisfactionRating;
    private ClientStatus status;
    private List<Order> orders = new ArrayList<>();

    public Client(int id, String name) {
        super(id, name);
        this.waitTime = 30;
        this.status = ClientStatus.WAITING; //poczatkowo oczekujacy
        this.satisfactionRating = 0; //poczatkowo nieocenione
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void placeOrder(Order order) {
        // klient składa zamówienie
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setSatisfactionRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.satisfactionRating = rating;
        }
    }

    public int getSatisfactionRating() {
        return satisfactionRating;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void receiveDish() {
        // klient otrzymuje danie
    }
//ostatnia zmiana
}
