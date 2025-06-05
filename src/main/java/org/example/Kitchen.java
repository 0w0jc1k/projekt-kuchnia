package org.example;
import java.util.*;

public class Kitchen {
    private List<Cook> cooks = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private Map<Cook, Boolean> cookAvailability = new HashMap<>();//przechowuje czy kucharz jest wolny w danym momencie
    private Simulation simulation;

    public Kitchen(Simulation simulation) {
        this.simulation = simulation;
    }

    public void addCook(Cook cook) {
        cooks.add(cook);
        cookAvailability.put(cook, true);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void processOrders() {
        for (Order order : new ArrayList<>(orders)) {
            if (order.getStatus() == OrderStatus.PENDING) {
                for (Cook cook : cooks) {
                    if (cookAvailability.get(cook)) {
                        order.setStatus(OrderStatus.IN_PROGRESS);
                        cook.prepareDish(order);
                        cookAvailability.put(cook, false);
                        break;
                    }
                }
            }

            // Anulowanie zamówienia jeśli klient wyszedł
            if (order.getClient() != null && order.getClient().getStatus() == ClientStatus.LEFT) {
                order.setStatus(OrderStatus.CANCELLED);
                orders.remove(order);
            }
        }
    }

    public void deliverOrders() {
        for (Order order : new ArrayList<>(orders)) {
            if (order.getStatus() == OrderStatus.READY) {
                Client client = order.getClient();
                System.out.println("Kuchnia dostarcza zamowienie do " + order.getClient().getName());
                client.updateStatus();
                simulation.printClientSummary(client);
                freeCook(order);
                orders.remove(order);
            }
        }
    }

    private void freeCook(Order order) {
        for (Cook cook : cooks) {
            if (!cookAvailability.get(cook)) {
                cookAvailability.put(cook, true);
                break;
            }
        }
    }

    public List<Order> getOrders() {
        return orders;
    }
}

