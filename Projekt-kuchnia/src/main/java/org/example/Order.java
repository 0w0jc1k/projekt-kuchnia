package org.example;

public class Order {
    private Client client;
    private Dish dish;

    public Order(Client client, Dish dish) {
        this.client = client;
        this.dish = dish;
    }

    public Client getClient() {
        return client;
    }

    public Dish getDish() {
        return dish;
    }
}
