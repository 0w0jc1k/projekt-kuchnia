package org.example;

/**
 * Reprezentuje zamowienie w symulacji.
 * Kontroluje status przygotowania, czas oraz przypisanie do klienta.
 */
public class Order {
    /**
     * Klient ktory zlozyl zamowienie
     */
    private Client client;
    /**
     * Danie ktore zamowil klient
     */
    private Dish dish;
    /**
     * Unikatowy identyfikator dania
     */
    private int id;
    /**
     * Status, w ktorym w danym momencie znajduje sie zamowienie
     */
    private OrderStatus status;
    /**
     * Czas mijajacy od momentu zlozenia zamowienia
     */
    private int actualOrderTime;
    /**
     * Postep w przygotowaniu dania
     */
    private int preparationProgress;

    /**
     * Konstruktor do tworzenia nowej instancji new Order
     * @param client klient, ktory sklada zamowienie
     * @param dish danie, ktore zamawia klient
     * @param id unikatowy identyfikator dania
     */
    public Order(Client client, Dish dish, int id) {
        this.client = client;
        this.dish = dish;
        this.id = id;
        this.status = OrderStatus.PENDING;
        this.client.setOrder(this);
        this.actualOrderTime = 1;
        this.preparationProgress = 0;
    }

    /**
     * Zwraca klienta, ktory zlozyl konkretne zamowienie
     * @return klient, ktory zlozyl zamowienie
     */
    public Client getClient() {
        return client;
    }

    /**
     * Zwraca danie, ktore zamowil klient
     * @return danie, ktore zamowil klient
     */
    public Dish getDish() {

        return dish;
    }

    /**
     * Zwraca status zamowienia, w zaleznosci od czasu symulacji
     * @return status zamowienia
     */
    public OrderStatus getStatus() {

        return status;
    }

    /**
     * Umozliwia zmiane statusu zamowienia
     * @param status nowy ststus do przypisania
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Zwraca faktyczny czas od momentu zlozenia zamowienia
     * @return czas od momentu zamowienia dania w minutach
     */
    public int getActualOrderTime() {
        return actualOrderTime;
    }

    /**
     * Zwieksza czas oczekiwania na danie
     */
    public void incrementActualOrderTime(){
        actualOrderTime++;
    }

    /**
     * Zwraca obecny stan przygotowania zamowienia
     * @return obecny stan zamowienia
     */
    public int getPreparationProgress() {
        return preparationProgress;
    }

    /**
     * Zwieksza czas potrzebny na przygotowanie dania
     */
    public void incrementPreparationTime(){
        preparationProgress++;
    }
}
