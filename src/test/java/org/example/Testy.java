package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasa Testy weryfikujaca poprawny przebieg komponentow symulacji.
 * Zawiera testy jednostkowe dla podstawowej funkcjonalnosci.
 */
public class Testy {
    /**
     * Testuje, czy kucharz w odpowiedni sposob ignoruje usuniete zamowienia.
     * Weryfikuje, czy zamowienie ze statusem CANCELLED pozostaje niezmienione po
     * probie przetworzenia go przez kucharza.
     */
    @Test
    void ignoreCancelledOrder() {
        Cook cook = new Cook(1, "Simu Liu");
        Order order = new Order(new RegularClient(1, "Klient"), new Dish("Lasagne", 10), 1);
        order.setStatus(OrderStatus.CANCELLED);
        cook.prepareDish(order);
        assertNotEquals(OrderStatus.READY, order.getStatus());
    }

    /**
     * Testuje, czy klient po uplynieciu 30 minut od rozpoczecia trwania symulacji
     * opusci restauracje oraz, czy jego status zmieni sie na LEFT.
     */
    @Test
    void clientLeavesAfter30(){
        Client client = new RegularClient(1,"Timothee Chalamet");
        Order order = new Order(client, new Dish("Burger",10),1);
        client.setOrder(order);
        for(int i=0;i<30;i++){
            client.updateStatus();
        }
        assertEquals(ClientStatus.LEFT,client.getStatus());
    }

    /**
     * Testuje dostepnosc kucharza do przyjecia zamowienia, jesli obecnie nie jest
     * do zadnego przypisany.
     */
    @Test
    void findFreeCookActuallyWorks(){
        Kitchen kitchen = new Kitchen(null);
        Cook cook = new Cook(1, "Simu Liu");
        kitchen.addCook(cook);
        assertNotNull(kitchen.findFreeCook());
    }

    /**
     * Testuje, czy klient, ktory zostal obsluzony w odpowiednim czasie zmienia swoj status
     * na SERVED.
     */
    @Test
    void deliveredOrderMakesClientSERVED() {
        Kitchen kitchen = new Kitchen(null);
        Client client = new RegularClient(1, "Klient");
        Order order = new Order(client, new Dish("Sałatka", 5), 1);
        order.setStatus(OrderStatus.READY);
        kitchen.addOrder(order);
        kitchen.deliverOrders();
        assertEquals(ClientStatus.SERVED, client.getStatus());
    }

    /**
     * Testuje, czy po zlozeniu zamowien przez klientow regularnych i VIP-ow w
     * niezaleznej kolejnosci, jako pierwsze przetworzone zostanie zamowienie zlozone przez VIP-a.
     */
    @Test
    void VIPFirstInQueue(){
        Kitchen kitchen = new Kitchen(new Simulation(new Configuration(1,2,15)));
        Cook cook = new Cook(1, "Simu Liu");
        kitchen.addCook(cook);
        RegularClient regularClient = new RegularClient(1, "Klient");
        regularClient.placeOrder(new Dish("Pizza",10),kitchen);
        VIPClient vipClient = new VIPClient(2,"Bob Dylan");
        vipClient.placeOrder(new Dish("Burger",12),kitchen);
        kitchen.processOrders();
       Order processedOrder = kitchen.getCookAvailability().get(cook);
       assertEquals(vipClient.getOrder(), processedOrder);
    }
}
