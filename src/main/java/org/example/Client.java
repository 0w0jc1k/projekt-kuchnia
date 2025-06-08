package org.example;

/**
 * Klasa klient reprezentuje klienta kuchni w symulacji
 * To klasa abstrakcyjna dla roznych typow klientow (regular i VIP)
 * Klienci moga skladac zamowienia, czekac na nie oraz wystawiac swoja ocene na podstawie
 * czasu oczekiwania na zamowione danie
 *
 * @author Aleksandra Wojcik, Katarzyna Druciak
 * @see RegularClient
 * @see VIPClient
 */

public abstract class Client extends Person implements ClientAction{
    /**
     * maksymalny czas oczekiwania klienta na zamowienie (w minutach)
     */
    protected int waitTime;
    /**
     * Ocena klienta od 0 do 5
     */
    protected int satisfactionRating;
    /**
     * Obecny status klienta
     */
    protected ClientStatus status;
    /**
     * Zamowienie zlozone przez klienta
     */
    protected Order order;
    /**
     * Faktyczny stan oczekiwania klienta
     */
    protected int actualWaitTime = 0;

    public Client(int id, String name) {
        super(id, name);
        this.waitTime = 30;
        this.status = ClientStatus.WAITING;
    }

    public int satisfactionCalculation() {// klient otrzymuje danie i daje ocenke
        if(actualWaitTime <= 10) return 5;
        if(actualWaitTime <= 15) return 4;
        if(actualWaitTime <= 20) return 3;
        if(actualWaitTime <= 25) return 2;
        if(actualWaitTime <= 30) return 1;
        return 0;
    }

    @Override
    public void updateStatus() { //update statusu klienta w zaleznosci od oczekiwania na zamowienie
        if (status == ClientStatus.SERVED || status == ClientStatus.LEFT) {
            return;
        }
        if(order != null && order.getStatus() == OrderStatus.READY) {
            status = ClientStatus.SERVED;
            actualWaitTime = order.getActualOrderTime();
            satisfactionRating = satisfactionCalculation();
            System.out.println("Klient: " + getName() + " zostal obsluzony/a w czasie: " + actualWaitTime + " minut");
        }else if(order != null) {
            order.incrementActualOrderTime();
            actualWaitTime++;

            //sprawdzamy czy klient sie niecierpliwi lub czy wyszedl
            if(actualWaitTime>=(waitTime - 15)&& status == ClientStatus.WAITING) {
                status = ClientStatus.IMPATIENT;
                System.out.println("Klient: "+getName()+" sie niecierpliwi!");
            }
            if(actualWaitTime>= waitTime) {
                status = ClientStatus.LEFT;
                satisfactionRating = 0;
                if(order != null){
                    order.setStatus(OrderStatus.CANCELLED);
                }
                System.out.println("Klient: "+getName()+" opuscil/a restauracje niezadowolony po "+actualWaitTime+" minutach");
            }
        }
    }
    @Override
    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addOrder(this.order);
        System.out.println(getId()+". [VIP] "+getName() + " zamówił/a: " + dish.getName());
    }

    public int getActualWaitTime(){
        return actualWaitTime;
    }
    public void setActualWaitTime(int time) {
        this.actualWaitTime = time;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public int getSatisfactionRating() {
        return satisfactionRating;
    }


    public void setSatisfactionRating(int rating) {
        this.satisfactionRating = rating;
    }

    public Order getOrder() {
        return order;
    }

}
