package org.example;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private int waitTime;
    private int satisfactionRating;
    private ClientStatus status;
    private Order order;

    public Client(int id, String name) {
        super(id, name);
        this.waitTime = 30;
        this.satisfactionRating = 0; //poczatkowo nieocenione
        this.order = null;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void placeOrder(Order order) {
        this.order = order;
        this.status = ClientStatus.WAITING; //poczatkowo oczekujacy
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

    public Order getOrder() {
        return order;
    }

    public void receiveDish() {
        // klient otrzymuje danie
    }

}
