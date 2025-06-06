package org.example;
import java.util.*;

public class Kitchen {
    private List<Cook> cooks = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private Map<Cook, Order> cookAvailability = new HashMap<>();//przechowuje czy kucharz jest wolny w danym momencie
    private Simulation simulation;

    public Kitchen(Simulation simulation) {
        this.simulation = simulation;
    }

    public void addCook(Cook cook) {
        cooks.add(cook);
        cookAvailability.put(cook, null); //inicjalizuje, ze poczatkowo kazdy kucharz jest wolny
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void processOrders() {
        //wolny kucharz otrzymuje nowe zamowienie
        for (Order order : new ArrayList<>(orders)) {
            if (order.getStatus() == OrderStatus.PENDING) {
                for (Cook cook : cooks) {
                    if (cookAvailability.get(cook) == null) { //jesli kucharz jest dostepny
                        order.setStatus(OrderStatus.IN_PROGRESS);
                        cookAvailability.put(cook, order);
                        break;
                    }
                }
            }
        }

        for (Cook cook : cooks) {
            Order assignedOrder = cookAvailability.get(cook);
            if (assignedOrder != null && assignedOrder.getStatus() == OrderStatus.IN_PROGRESS) {
                assignedOrder.incrementPreparationTime();//w zaleznosci od kolejnosci wziecia sie za zamowienie jego czas przygotowania sie zmienia
                if (assignedOrder.getPreparationProgress() >= assignedOrder.getDish().getPreparationTime()) {
                    cook.prepareDish(assignedOrder);
                    cookAvailability.put(cook, null);//zwalniamy kucharza po zrobieniu dania
                }
            }
        }
        //usuwamy zamowienia jesli klient wyszedl
        Iterator<Order> orderIterator = orders.iterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getClient() != null && order.getClient().getStatus() == ClientStatus.LEFT) {
                if (order.getStatus() != OrderStatus.CANCELLED) {
                    order.setStatus(OrderStatus.CANCELLED);
                    //uwalniamy kucharza jesli przygotowywal to zamowienie
                    for (Map.Entry<Cook, Order> entry : cookAvailability.entrySet()) {
                        if (entry.getValue() == order) {
                            cookAvailability.put(entry.getKey(), null);
                            break;
                        }
                    }
                }
                orderIterator.remove();//usuwa z listy usuniete zamowienie
            }
        }
    }

    public void deliverOrders() {
        Iterator<Order> orderIterator = orders.iterator();
        while(orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getStatus() == OrderStatus.READY) {
                Client client = order.getClient();
                System.out.println("Kuchnia dostarcza zamowienie do " + order.getClient().getName());
                client.updateStatus();
                simulation.printClientSummary(client);
                orderIterator.remove();
        }
    }
}

    public List<Order> getOrders() {
        return orders;
    }
}

