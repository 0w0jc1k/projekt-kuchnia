package org.example;
import java.util.*;

/**
 * Wnetrze kuchni zarzadzajace przetwarzaniem zamowien i przyporzadkowywaniem
 * kucharzy.
 * Zarzadza zarowno regularnymi, jak i priorytetowymi zamowieniami.
 */
public class Kitchen {
    /**
     * Lista przechowujaca kucharzy
     */
    private List<Cook> cooks = new ArrayList<>();
    /**
     * Lista przechowujaca zamowienia regularne
     */
    private List<Order> orders = new ArrayList<>();
    /**
     * Lista przechowujaca zamowienia VIP-ow
     */
    private List<Order> vipOrders = new ArrayList<>();
    /**
     * HashMapa przechowujaca informacje o dostepnosci kucharza
     */
    private Map<Cook, Order> cookAvailability = new HashMap<>();
    /**
     * Referencja do symulacji
     */
    private Simulation simulation;

    /**
     * Konstruktor do tworzenia nowej instancji new Kitchen
     * @param simulation symulacja
     */
    public Kitchen(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Dodaje kucharza do listy
     * @param cook kucharz, ktorego chcemy dodac
     */
    public void addCook(Cook cook) {
        cooks.add(cook);
        cookAvailability.put(cook, null); //inicjalizuje, ze poczatkowo kazdy kucharz jest wolny
    }

    /**
     * Dodaje zamowienie do listy regularnej
     * @param order zamowienie, ktore chcemy dodac
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Dodaje zamowienie do listy VIP-owej
     * @param order zamowienie, ktore chcemy dodac
     */
    public void addVipOrder(Order order) {
        vipOrders.add(order);
    }

    /**
     * Przetwarza zlozone zamowienia, w kolejnosci najpierw priorytetowej
     * (zlozone przez VIP-a), a pozniej regularnej (zlozone przez klienta regularnego)
     */
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

    /**
     * Znajduje wolnego kucharza do przydzielenia mu zamowienia
     * @return wolny kucharz lub null jesli kazdy jest zajety
     */
    public Cook findFreeCook() {
        for (Cook cook : cooks) {
            if( cookAvailability.get(cook) == null ) {
                return cook;
            }
        }
        return null; //brak wolnych kucharzy
    }

    /**
     * Dostarcza zamowienia do klientow i aktualizuje ich statusy
     */
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

    /**
     * Zwraca przypisanie kucharza do dania
     * @return przypisanie kucharza do dania
     */
    public Map<Cook, Order> getCookAvailability() {
        return cookAvailability;
    }
}