package org.example;

public interface ClientAction {
    void placeOrder(Dish dish, Kitchen kitchen);
    void updateStatus();
}
