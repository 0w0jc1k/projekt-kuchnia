package org.example;
import java.util.*;

public class Kitchen {
    private List<Cook> cooks = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public void addCook(Cook cook) {
        cooks.add(cook);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
