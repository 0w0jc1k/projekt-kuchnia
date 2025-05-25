package org.example;

public class Order {
    private Client client;
    private Dish dish;
    private int id;
    private OrderStatus status;

    public Order(Client client, Dish dish, int id) {
        this.client = client;
        this.dish = dish;
        this.id = id;
        this.status = OrderStatus.PENDING;
        this.client.addOrder(this); //przypisuje zamowienie do klienta
    }

    public Client getClient() {
        return client;
    }

    public Dish getDish() {
        return dish;
    }

    public int getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
