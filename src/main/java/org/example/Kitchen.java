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

    public List<Order> getOrders() {
    return orders;
    }

    public void processOrders() {
        for (Order order : new ArrayList<>(orders)) {
            if (order.getStatus() == OrderStatus.PENDING) {
                if (!cooks.isEmpty()) {
                    Cook cook = cooks.get(0);
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    cook.prepareDish(order);
                }
            }

            // Anulowanie zamówienia jeśli klient wyszedł
            if (order.getClient().getStatus() == ClientStatus.LEFT) {
                order.setStatus(OrderStatus.CANCELLED);
                orders.remove(order);
            }
        }
    }


    public void deliverOrders(){
            for (Order order : new ArrayList<>(orders)) {
                if (order.getStatus() == OrderStatus.READY) {
                    System.out.println("Kuchnia dostarcza zamowienie do " + order.getClient().getName());
                    orders.remove(order);
                    order.getClient().updateStatus();
                }
            }
        }
    }

