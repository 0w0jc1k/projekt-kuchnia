package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Testy {
    @Test
    void ignoreCancelledOrder() {
        Cook cook = new Cook(1, "Simu Liu");
        Order order = new Order(new RegularClient(1, "Klient"), new Dish("Lasagne", 10), 1);
        order.setStatus(OrderStatus.CANCELLED);
        cook.prepareDish(order);
        assertNotEquals(OrderStatus.READY, order.getStatus());
    }

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

    @Test
    void findFreeCookActuallyWorks(){
        Kitchen kitchen = new Kitchen(null);
        Cook cook = new Cook(1, "Simu Liu");
        kitchen.addCook(cook);
        assertNotNull(kitchen.findFreeCook());
    }

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
