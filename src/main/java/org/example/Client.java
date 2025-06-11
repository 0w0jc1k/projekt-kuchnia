package org.example;

/**
 * Klasa klient reprezentuje klienta kuchni w symulacji
 * To klasa abstrakcyjna dla roznych typow klientow (regular i VIP)
 * Klienci moga skladac zamowienia, czekac na nie oraz wystawiac swoja ocene na podstawie
 * czasu oczekiwania na zamowione danie
 *
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

    /**
     * Tworzy nowa instancje new Client
     * @param id unikatowy identyfikator
     * @param name wyswietlane imie
     */
    public Client(int id, String name) {
        super(id, name);
        this.waitTime = 30;
        this.status = ClientStatus.WAITING;
    }

    /**\
     * Oblicza satysfakcje klienta na podstawie czasu oczekiwania
     * na zamowienie
     * @return ocena satysfakcji od 0 do 5
     */
    public int satisfactionCalculation() {
        if(actualWaitTime <= 10) return 5;
        if(actualWaitTime <= 15) return 4;
        if(actualWaitTime <= 20) return 3;
        if(actualWaitTime <= 25) return 2;
        if(actualWaitTime <= 30) return 1;
        return 0;
    }

    /**
     * Aktualizuje status klienta na podstawie czasu oczekiwania
     * na danie i momentu jego dostarczenia
     */
    @Override
    public void updateStatus() {
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

    /**
     * Klient sklada zamowienie, jesli jest VIP-em pojawia sie odpowiedni komunikat
     * @param dish zamowienie sklada sie z konkretnego dania
     * @param kitchen zamowienie skladane jest w kuchni
     */
    @Override
    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,id); //przykladowe zamowienie
        kitchen.addOrder(this.order);
    }

    /**
     * Zwraca prawdziwy czas oczekiwania na zamowienie
     * @return czas oczekiwania w minutach
     */
    public int getActualWaitTime(){
        return actualWaitTime;
    }

    /**
     * Ustawia czas prawdziwego oczekiwania na zamowienie
     * @param time czas oczekiwania w minutach
     */
    public void setActualWaitTime(int time) {
        this.actualWaitTime = time;
    }

    /**
     * Przypisuje konkretne zamowienie do klienta, ktory je zlozyl
     * @param order zamowienie skladajace sie z dania dla klienta
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Zwraca aktualny status klienta
     * @return aktualny status klienta
     */
    public ClientStatus getStatus() {
        return status;
    }

    /**
     * Zwraca ocene satysjakcji klienta
     * @return ocena satysfakcji klienta
     */
    public int getSatisfactionRating() {
        return satisfactionRating;
    }

    /**
     * Zwraca zamowienie, ktore zlozyl klient i ktore jest mu przypisane
     * @return zamowienie zlozone przez klienta
     */
    public Order getOrder() {
        return order;
    }

}
