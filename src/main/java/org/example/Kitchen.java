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

    public void processOrders(){
        for(Order order : orders){
            if (order.getStatus() == OrderStatus.PENDING) {
                Cook cook = cooks.get(0); // Bierzemy pierwszego dostępnego kucharza
                order.setStatus(OrderStatus.IN_PROGRESS);
                cook.prepareDish(order);
                return;
            }
        }
        }

        public void deliverOrders(){
            for (Order order : new ArrayList<>(orders)) {
                if (order.getStatus() == OrderStatus.READY) {
                    System.out.println("Kuchnia dostarcza zamówienie do " + order.getClient().getName());
                    orders.remove(order);
                }
            }
        }
    }

