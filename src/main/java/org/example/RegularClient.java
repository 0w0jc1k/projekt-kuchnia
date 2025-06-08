package org.example;

/**
 * Reprezentuje regularnego klienta w symulacji.
 * Sklada zamowienia bez priorytetow
 */
public class RegularClient extends Client{
    /**
     * Konstruktor do tworzenia nowej instancji new RegularClient
     * @param id unikatowy identyfikator klienta
     * @param name imie regularnego klienta
     */
    public RegularClient(int id, String name){
        super(id, name);
    }

    /**
     * Sklada zamowienie w kolejce bez priorytetu
     * @param dish zamowienie sklada sie z konkretnego dania
     * @param kitchen zamowienie skladane jest w kuchni
     */
    @Override
    public void placeOrder(Dish dish, Kitchen kitchen) { // klient składa zamówienie w kuchni
        this.order = new Order(this,dish,1); //przykladowe zamowienie
        kitchen.addOrder(this.order);
        System.out.println(getId()+". "+getName() + " zamówił/a: " + dish.getName());
    }

    }

