package org.example;

public class Client extends Person {
    private int id;
    private String name;
    private int waitTime;

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
        this.waitTime = 0;
    }

    public void placeOrder(Order order) {
        // klient składa zamówienie
    }

    public void receiveDish() {
        // klient otrzymuje danie
    }
}
