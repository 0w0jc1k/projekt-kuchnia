package org.example;

/**
 * Interface do podstawowych akcji, ktore kazdy klient kuchni musi implementowac
 * Zapewnia stale zachowania posrod wszystkich typow klientow
 */
public interface ClientAction {
    /**
     * Sklada zamowienie w kuchni
     * @param dish jedno danie zamowione z kilku dostepnych
     * @param kitchen przetwarza zamowienie
     */
    void placeOrder(Dish dish, Kitchen kitchen);

    /**
     * Aktualizuje status klienta na podstawie czasu otrzymania zamowienia
     * od momentu zamowienia
     */
    void updateStatus();
}
