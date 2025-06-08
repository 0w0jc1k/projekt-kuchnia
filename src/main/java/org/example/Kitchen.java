package org.example;
import java.util.*;

public class Kitchen {
    private List<Cook> cooks = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Order> vipOrders = new ArrayList<>();
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

    public void addVipOrder(Order order) {
        vipOrders.add(order);
    }

    public void processOrders() {
        //wolny kucharz otrzymuje nowe zamowienie vip (priorytet)
        for (Order order : vipOrders) {
            if (order.getStatus() == OrderStatus.PENDING) {
                Cook freeCook = findFreeCook();
                if (freeCook != null) {
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    cookAvailability.put(freeCook, order);
                    System.out.println(freeCook.getName()+" rozpoczal przygotowywanie: "+order.getDish().getName()+" dla VIP-a "+order.getClient().getName());
                }
            }
        }

        //kucharz otrzymuje zamowienia regularnych klientow
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.PENDING) {
                Cook freeCook = findFreeCook();
                if (freeCook != null) {
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    cookAvailability.put(freeCook, order);
                    System.out.println(freeCook.getName()+" rozpoczal przygotowywanie: "+order.getDish().getName()+" dla "+order.getClient().getName());
                }
            }
        }

        for (Cook cook : cooks) {
            Order order = cookAvailability.get(cook);
            if(order != null && order.getStatus() == OrderStatus.IN_PROGRESS) {
                order.incrementPreparationTime();
                if(order.getPreparationProgress() >= order.getDish().getPreparationTime()) {
                    cook.prepareDish(order);
                    cookAvailability.put(cook, null);
                }
            }
        }
    }
    public Cook findFreeCook() {
        for (Cook cook : cooks) {
            if( cookAvailability.get(cook) == null ) {
                return cook;
            }
        }
        return null; //brak wolnych kucharzy
    }

    public void deliverOrders() {
        //dostarczamy zamowienia VIP
        Iterator<Order> vipIterator = vipOrders.iterator();
        while(vipIterator.hasNext()) {
            Order order = vipIterator.next();
            if(order.getStatus() == OrderStatus.READY) {
                Client client = order.getClient();
                System.out.println("Kuchnia dostarcza zamowienie priorytetowe do: " + order.getClient().getName());
                client.updateStatus();
                vipIterator.remove();
            }
        }
        //dostarczamy zamowienia regularne
        Iterator<Order> orderIterator = orders.iterator();
        while(orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getStatus() == OrderStatus.READY) {
                Client client = order.getClient();
                System.out.println("Kuchnia dostarcza zamowienie do " + order.getClient().getName());
                client.updateStatus();
                orderIterator.remove();
            }
        }
    }

    public List<Order> getOrders() {
        return orders;
    }
    public Map<Cook, Order> getCookAvailability() {
        return cookAvailability;
    }
}