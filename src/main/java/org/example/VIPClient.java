package org.example;

/**
 * Reprezentuje klienta VIP z pierwszenstwem obslugi.
 * Sklada zamowienie z priorytetem.
 */
public class VIPClient extends Client{
    /**
     * Konstruktor do tworzenia nowej instancji new VIPClient
     * @param id unikatowy identyfikator klienta
     * @param name imie klienta
     */
    public VIPClient(int id, String name) {
        super(id, name);
    }

    /**
     * Sklada zamowienie z priorytetem przetwarzania przed
     * klientem regularnym.
     * @param dish zamowienie sklada sie z konkretnego dania
     * @param kitchen zamowienie skladane jest w kuchni
     */
    @Override
    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addVipOrder(this.order);
        System.out.println(getId()+". [VIP] "+getName() + " zamówił/a: " + dish.getName());
    }

}
